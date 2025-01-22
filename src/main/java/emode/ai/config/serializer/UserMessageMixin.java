package emode.ai.config.serializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.model.Media;

import java.util.Collection;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class UserMessageMixin {

    @JsonCreator
    public UserMessageMixin(
            @JsonProperty("content") String textContent,
            @JsonProperty("media") Collection<Media> media,
            @JsonProperty("metadata") Map<String, Object> metadata
    ) {
    }
}
