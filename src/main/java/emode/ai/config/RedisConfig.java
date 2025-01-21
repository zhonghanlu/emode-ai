package emode.ai.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author zhl
 * redis配置
 * @source_auth ruoyi-plus
 */
@Slf4j
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    @Resource
    private RedisProperties redisProperties;

    @Resource
    private RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() {
        String prefix = REDIS_PROTOCOL_PREFIX;
        if (redisProperties.getSsl().isEnabled()) {
            prefix = REDISS_PROTOCOL_PREFIX;
        }
        Config config = new Config();
        config.setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setCodec(JsonJacksonCodec.INSTANCE)
                .setTransportMode(redissonProperties.getTransportMode());

        RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
        if (Objects.nonNull(singleServerConfig)) {
            // 使用单机模式
            config.useSingleServer()
                    .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
                    .setDatabase(redisProperties.getDatabase())
                    .setPassword(StringUtils.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
                    .setTimeout(singleServerConfig.getTimeout())
                    .setRetryAttempts(singleServerConfig.getRetryAttempts())
                    .setRetryInterval(singleServerConfig.getRetryInterval())
                    .setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
                    .setClientName(singleServerConfig.getClientName())
                    .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                    .setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                    .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                    .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
                    .setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());
        }
        // 集群配置方式
        RedissonProperties.ClusterServersConfig clusterServersConfig = redissonProperties.getClusterServersConfig();
        if (Objects.nonNull(clusterServersConfig)) {
            // 使用集群模式
            config.useClusterServers()
                    .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
                    .setPassword(StringUtils.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
                    .setTimeout(clusterServersConfig.getTimeout())
                    .setRetryAttempts(clusterServersConfig.getRetryAttempts())
                    .setRetryInterval(clusterServersConfig.getRetryInterval())
                    .setSubscriptionsPerConnection(clusterServersConfig.getSubscriptionsPerConnection())
                    .setClientName(clusterServersConfig.getClientName())
                    .setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
                    .setPingConnectionInterval(clusterServersConfig.getPingConnectionInterval())
                    .setSubscriptionConnectionMinimumIdleSize(clusterServersConfig.getSubscriptionConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
                    .setDnsMonitoringInterval(clusterServersConfig.getDnsMonitoringInterval())
                    .setFailedSlaveReconnectionInterval(clusterServersConfig.getFailedSlaveReconnectionInterval())
                    .setScanInterval(clusterServersConfig.getScanInterval())
                    .setReadMode(clusterServersConfig.getReadMode())
                    .setSubscriptionMode(clusterServersConfig.getSubscriptionMode())
                    .setNodeAddresses(redisProperties.getCluster().getNodes());
        }
        RedissonClient redissonClient = Redisson.create(config);
        log.info("初始化 redis 配置");
        return redissonClient;
    }

}
