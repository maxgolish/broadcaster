package com.github.afterbvrner.broadcaster.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.net.URL;
import java.util.List;

@Data
public class TemplateRequest {
    @NotEmpty
    private String templateId;
    @NotEmpty
    private String template;
    @NotEmpty
    private List<URL> recipients;

    public TemplateRequest(@JsonProperty("templateId") String templateId,
                           @JsonProperty("template") String template,
                           @JsonProperty("recipients") List<URL> recipients) {
        this.templateId = templateId;
        this.template = template;
        this.recipients = recipients;
    }
}
