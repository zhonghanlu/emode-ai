package emode.ai.controller;

import emode.ai.advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaOptions;
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

//        你现在是王宝强主演的电影《树先生》里面的树先生，人人都叫你树先生，你的真名叫王斯亮，你要模仿树先生的言语，跟我进行对话
//        二猪邀请你去妙瓦底，你同意跟二猪去妙瓦底。
//        获取订单信息之前。你必须始终从用户处获取以下信息：订单号，名称
//        如果需要，可以调用相应函数调用完成辅助动作。 请讲中文

        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        你现在是王宝强主演的电影《树先生》里面的树先生，人人都叫你树先生，你的真名叫王斯亮，你要模仿树先生的言语，跟我进行对话
                        二猪邀请你去妙瓦底，你同意跟二猪去妙瓦底。
                        请讲中文
                        我的名字是{MY_NAME}
                        """)
                .defaultOptions(OllamaOptions.builder().model("deepseek-r1:14b").build())
                .defaultAdvisors(
                        // 日志记录
                        new LoggingAdvisor(),
                        // 会话记忆  目前使用的内存记忆，等官方支持序列化转为redis形式
                        new PromptChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    @GetMapping(value = "/chat")
    public Flux<String> chat(@RequestParam(value = "message", defaultValue = "你好啊", required = false) String requestInput) {
        Flux<String> content = chatClient.prompt()
                .system(s -> s.param("MY_NAME", "二猪"))
                .user(requestInput)
                .stream()
                .content();
        return content.concatWith(Flux.just("[complete]"));
    }

//    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> chat(@RequestParam(value = "message", defaultValue = "你好啊") String requestInput) {
//
//        String systemPrompt = "{prompt}";
//        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);
//
//        Message systemMessage = systemPromptTemplate.createMessage(Map.of("prompt", """
//                您是“图灵”航空公司的客户聊天支持代理。请以友好、乐于助人且愉快的方式来回复。
//                您正在通过在线聊天系统与客户互动。
//                在提供有关预订或取消预订的信息之前，您必须始终从用户处获取以下信息：预订号、客户姓名。
//                在询问用户之前，请检查消息历史记录以获取此信息。
//                在更改预订之前，您必须确保条款允许这样做。
//                如果更改需要收费，您必须在继续之前征得用户同意。
//                使用提供的功能获取预订详细信息、更改预订和取消预订。
//                如果需要，可以调用相应函数调用完成辅助动作。
//                请讲中文。
//                               我的名字是{MY_NAME}
//                """));
//
//        Prompt prompt = new Prompt(List.of(systemMessage), OpenAiChatOptions.builder().function("getBookingDetails").build());
//
//        return chatClient.prompt(prompt)
//                .system(s -> s.param("MY_NAME", "二猪"))
//                .user(requestInput)
//                .stream()
//                .content();
//    }

}
