package emode.ai.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import emode.ai.config.serializer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author zhl
 * redis配置
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Message> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Message> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.addMixIn(Message.class, MessageMixin.class);
        objectMapper.addMixIn(UserMessage.class, UserMessageMixin.class);
        objectMapper.addMixIn(AssistantMessage.class, AssistantMessageMixin.class);
        objectMapper.addMixIn(SystemMessage.class, SystemMessageMixin.class);
        objectMapper.addMixIn(ToolResponseMessage.class, ToolResponseMessageMixin.class);

        Jackson2JsonRedisSerializer<Message> jsonSerializer = new Jackson2JsonRedisSerializer<>(objectMapper,Message.class);
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }

}
