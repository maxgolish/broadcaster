package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.exception.EndpointNotAvailableException;
import com.github.afterbvrner.broadcaster.exception.ScheduledTaskNotFound;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.request.FixedRateScheduledMessageRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.request.ScheduledMessageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    private TemplateService templateService;

    @Test
    public void sendMessageToCorrectUrl() throws MalformedURLException {
        String templateId = "test1";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://httpbin.org/post"));
        TemplateRequest request = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateService.saveTemplate(request);
        Map<String, String> variables = new HashMap<>();
        variables.put("test", "JetBrains");
        messageService.createAndSend(templateId, variables);
    }

    @Test
    public void sendMessageToIncorrectUrl_ThrowException() throws MalformedURLException {
        String templateId = "test2";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://~~~.com"));
        TemplateRequest request = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateService.saveTemplate(request);
        Map<String, String> variables = new HashMap<>();
        variables.put("test", "JetBrains");
        assertThrows(
                EndpointNotAvailableException.class,
                () -> messageService.createAndSend(templateId, variables)
        );
    }

    @Test
    public void startScheduledMessaging() throws MalformedURLException {
        String templateId = "test3";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://httpbin.org/post"));
        TemplateRequest templateRequest = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateService.saveTemplate(templateRequest);
        Map<String, String> variables = new HashMap<>();
        variables.put("test", "JetBrains");
        ScheduledMessageRequest request = new FixedRateScheduledMessageRequest(templateId, variables, 10000L);
        UUID taskId = messageService.runScheduledTask(request);
        messageService.getInfoById(taskId);
    }

    @Test
    public void getIncorrectTaskById_ThrowException() {
        assertThrows(
                ScheduledTaskNotFound.class,
                () -> messageService.getInfoById(UUID.randomUUID())
        );
    }
}
