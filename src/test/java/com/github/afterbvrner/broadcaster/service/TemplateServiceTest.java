package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.entity.TemplateEntity;
import com.github.afterbvrner.broadcaster.exception.TemplateNotFoundException;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TemplateServiceTest {
    @Autowired
    private TemplateService templateService;

    @Test
    public void saveTemplate_ReturnValidTemplate() {
        String templateId = "templatetestid";
        String template = "Welcome to the $test$";
        List<String> recipients = new ArrayList<>();
        recipients.add("https://httpbin.org/post");
        TemplateRequest request = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateService.saveTemplate(request);
        TemplateEntity entity = templateService.getTemplate(templateId);
        assertEquals(templateId, entity.getTemplateId(), "Template id is not equal");
        assertEquals(template, entity.getTemplate(), "Templates are not equal");
        assertEquals(recipients, entity.getRecipients(), "Recipients are not equal");
    }

    @Test
    public void getNonExistentTemplate_ThrowException() {
        assertThrows(
                TemplateNotFoundException.class,
                () -> templateService.getTemplate("non-existent id")
        );
    }
}
