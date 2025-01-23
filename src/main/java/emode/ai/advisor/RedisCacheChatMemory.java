package emode.ai.advisor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
public class RedisCacheChatMemory implements ChatMemory {

    @Resource
    private RedisTemplate<String, Message> redisTemplate;

    private static final String CHAT_MEMORY_KEY = "chat_memory:";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void add(String conversationId, List<Message> messages) {
        String cacheKey = buildCacheKey(conversationId);
        messages.forEach(message -> redisTemplate.opsForList().leftPush(cacheKey, message));
        log.info("RedisCacheChatMemory add conversationId:{}, message:{}", conversationId, messages);
    }

    @SneakyThrows
    @Override
    public List<Message> get(String conversationId, int lastN) {
        String cacheKey = buildCacheKey(conversationId);
        List<Message> messageList = redisTemplate.opsForList().range(cacheKey, 0, lastN - 1);
        log.info("RedisCacheChatMemory get conversationId:{}, lastN:{},historyMessage:{}", conversationId, lastN, messageList);
        return messageList != null ? messageList.stream()
                .skip(Math.max(0, messageList.size() - lastN))
                .toList() : List.of();
    }

    @Override
    public void clear(String conversationId) {
        if (conversationId == null) {
            return;
        }

        String cacheKey = buildCacheKey(conversationId);
        redisTemplate.delete(cacheKey);

        log.info("RedisCacheChatMemory clear conversationId:{}", conversationId);
    }

    private String buildCacheKey(String conversationId) {
        return CHAT_MEMORY_KEY + conversationId;
    }
}
