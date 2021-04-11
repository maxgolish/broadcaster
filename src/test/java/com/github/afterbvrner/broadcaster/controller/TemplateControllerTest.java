package com.github.afterbvrner.broadcaster.controller;

import com.github.afterbvrner.broadcaster.entity.TemplateEntity;
import com.github.afterbvrner.broadcaster.exception.TemplateAlreadyExistsException;
import com.github.afterbvrner.broadcaster.exception.TemplateNotFoundException;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TemplateControllerTest {

    @Autowired
    private TemplateController templateController;

    @Test
    public void saveTemplate_ReturnValidTemplate() throws MalformedURLException {
        String templateId = "templatetestid1";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://httpbin.org/post"));
        TemplateRequest request = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateController.saveTemplate(request);
        TemplateEntity entity = templateController.getTemplateById(templateId);
        assertEquals(templateId, entity.getTemplateId(), "Template id is not equal");
        assertEquals(template, entity.getTemplate(), "Templates are not equal");
        assertEquals(recipients, entity.getRecipients(), "Recipients are not equal");
    }

    @Test
    public void saveTemplate_ThenSaveTemplateWithSameId_ThrowException() throws MalformedURLException {
        String templateId = "templatetestid2";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://httpbin.org/post"));
        TemplateRequest request = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateController.saveTemplate(request);
        assertThrows(TemplateAlreadyExistsException.class, () -> templateController.saveTemplate(request));
    }
    @Test
    public void getNonExistentTemplate_ThrowException() {
        assertThrows(
                TemplateNotFoundException.class,
                () -> templateController.getTemplateById("non-existent id")
        );
    }
}
