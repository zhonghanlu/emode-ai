package emode.ai.config.serializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.model.Media;

import java.util.Collection;
import java.util.Map;

/**
 * @author zhl
 * @create 2025/1/22 16:22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AssistantMessageMixin {

    @JsonCreator
    public AssistantMessageMixin(
            @JsonProperty("content") String content,
            @JsonProperty("properties") Map<String, Object> properties,
            @JsonProperty("toolCalls") Collection<AssistantMessage.ToolCall> toolCalls,
            @JsonProperty("media") Collection<Media> media
    ) {
    }

//    {
//        "messageType": "ASSISTANT",
//            "metadata": {
//        "role": "ASSISTANT",
//                "messageType": "ASSISTANT",
//                "finishReason": "STOP",
//                "refusal": "",
//                "index": 0,
//                "id": "e5c766d8-2983-4027-a695-93465f3be670"
//    },
//        "toolCalls": [],
//        "media": [],
//        "content": "你好，仲寒露！很高兴见到你。如果你有任何关于少儿编程的问题，或者需要帮助解决某个编程题目，请告诉我题号，我会尽力提供思路和指导。记住，我不会直接给出答案，但会帮助你理解如何解决问题。有什么我可以帮你的吗？",
//            "text": "你好，仲寒露！很高兴见到你。如果你有任何关于少儿编程的问题，或者需要帮助解决某个编程题目，请告诉我题号，我会尽力提供思路和指导。记住，我不会直接给出答案，但会帮助你理解如何解决问题。有什么我可以帮你的吗？"
//    }

}
