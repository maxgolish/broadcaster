package com.github.afterbvrner.broadcaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TemplateRequest {
    private String templateId;
    private String template;
    private List<String> recipients;
}
