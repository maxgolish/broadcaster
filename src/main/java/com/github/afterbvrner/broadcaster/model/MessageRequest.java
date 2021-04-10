package com.github.afterbvrner.broadcaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String templateId;
    private Map<String, String> variables;
}
