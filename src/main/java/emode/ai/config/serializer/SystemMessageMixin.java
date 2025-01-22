package emode.ai.config.serializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhl
 * @create 2025/1/22 16:22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SystemMessageMixin {
    @JsonCreator
    public SystemMessageMixin(
            @JsonProperty("content") String textContent
    ) {
    }
}
