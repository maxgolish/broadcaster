package com.github.afterbvrner.broadcaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class MessageRequest {
    private String templateId;
    private Map<String, String> variables;
}
