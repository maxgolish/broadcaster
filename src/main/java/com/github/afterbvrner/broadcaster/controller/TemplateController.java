package com.github.afterbvrner.broadcaster.controller;

import com.github.afterbvrner.broadcaster.entity.TemplateEntity;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import com.github.afterbvrner.broadcaster.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
@AllArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping("/{templateId}")
    public TemplateEntity getTemplateById(@PathVariable String templateId) {
        return templateService.getTemplate(templateId);
    }

    @PostMapping
    public void saveTemplate(@RequestBody TemplateRequest template) {
        templateService.saveTemplate(template);
    }
}
