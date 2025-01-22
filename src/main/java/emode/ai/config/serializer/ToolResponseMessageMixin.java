package emode.ai.config.serializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.chat.messages.ToolResponseMessage;

import java.util.Collection;
import java.util.Map;

/**
 * @author zhl
 * @create 2025/1/22 16:22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ToolResponseMessageMixin {

    @JsonCreator
    public ToolResponseMessageMixin(
            @JsonProperty("responses") Collection<ToolResponseMessage.ToolResponse> responses,
            @JsonProperty("metadata") Map<String, Object> metadata
    ) {
    }

}
