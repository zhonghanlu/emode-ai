package emode.ai.config;

import emode.ai.advisor.RedisCacheChatMemory;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhl
 * @create 2025/1/21 17:05
 */
@Configuration
public class BeanConfig {

    @Bean
    public ChatMemory chatMemory() {
        return new RedisCacheChatMemory();
    }

}
