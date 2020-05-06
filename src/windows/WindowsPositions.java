package windows;

import com.fasterxml.jackson.annotation.*;
import windows.Window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "windows"
})

public class WindowsPositions {
    @JsonProperty("windows")
    private List<Window> windows = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("windows")
    public List<Window> getWindows() {
        return windows;
    }

    @JsonProperty("windows")
    public void setWindows(List<Window> windows) {
        this.windows = windows;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}