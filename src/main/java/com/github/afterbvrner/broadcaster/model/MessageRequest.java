package com.github.afterbvrner.broadcaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    @NotEmpty
    private String templateId;
    @NotNull
    private Map<String, String> variables;
}
