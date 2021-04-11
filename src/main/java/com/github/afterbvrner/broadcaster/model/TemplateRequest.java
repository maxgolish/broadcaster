package com.github.afterbvrner.broadcaster.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class TemplateRequest {
    private String templateId;
    private String template;
    private List<String> recipients;

    public TemplateRequest(@JsonProperty("templateId") String templateId,
                           @JsonProperty("template") String template,
                           @JsonProperty("recipients") List<String> recipients) {
        this.templateId = templateId;
        this.template = template;
        this.recipients = recipients;
    }
}
