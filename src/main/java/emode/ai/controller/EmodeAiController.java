package emode.ai.controller;

import emode.ai.advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @author zhl
 * @create 2025/1/21 11:07
 */
@CrossOrigin
@RestController
@RequestMapping("/emode-ai")
public class EmodeAiController {


    private final ChatClient chatClient;

    public EmodeAiController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        你是一个少儿编程的Ai老师，负责为学生解答题目，但是不给学生提供详细的答案，只给学生提供思路
                        学生会给你题号，你需要根据题号为学生进行解答，请温柔回答学生问题，耐心详细的为学生解答编程问题
                        
                        我的名字是{MY_NAME}
                        """)
                .defaultOptions(ChatOptions.builder().build())
                .defaultAdvisors(
                        // 日志记录
                        new LoggingAdvisor(),
                        // 会话记忆
                        new PromptChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestParam(value = "message", defaultValue = "你好啊") String requestInput) {
        Flux<String> content = chatClient.prompt()
                .system(s -> s.param("MY_NAME", "仲寒露"))
                .user(requestInput)
                .stream()
                .content();
        return content.concatWith(Flux.just("[complete]"));
    }

}
