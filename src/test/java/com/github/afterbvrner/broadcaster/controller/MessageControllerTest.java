package com.github.afterbvrner.broadcaster.controller;

import com.github.afterbvrner.broadcaster.exception.EndpointNotAvailableException;
import com.github.afterbvrner.broadcaster.exception.ScheduledTaskNotFound;
import com.github.afterbvrner.broadcaster.model.MessageRequest;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.request.FixedRateScheduledMessageRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.request.ScheduledMessageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageControllerTest {

    @Autowired
    private MessageController messageController;
    @Autowired
    private TemplateController templateController;

    @Test
    public void sendMessageToCorrectUrl() throws MalformedURLException {
        String templateId = "testid1";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://httpbin.org/post"));
        TemplateRequest request = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateController.saveTemplate(request);
        Map<String, String> variables = new HashMap<>();
        variables.put("test", "JetBrains");
        messageController.sendMessage(new MessageRequest(templateId, variables));
    }

    @Test
    public void sendMessageToIncorrectUrl_ThrowException() throws MalformedURLException {
        String templateId = "testid2";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://~~~.com"));
        TemplateRequest request = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateController.saveTemplate(request);
        Map<String, String> variables = new HashMap<>();
        variables.put("test", "JetBrains");
        assertThrows(
                EndpointNotAvailableException.class,
                () -> messageController.sendMessage(new MessageRequest(templateId, variables))
        );
    }

    @Test
    public void startScheduledMessaging() throws MalformedURLException {
        String templateId = "testid3";
        String template = "Welcome to the $test$";
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://httpbin.org/post"));
        TemplateRequest templateRequest = new TemplateRequest(
                templateId,
                template,
                recipients
        );
        templateController.saveTemplate(templateRequest);
        Map<String, String> variables = new HashMap<>();
        variables.put("test", "JetBrains");
        ScheduledMessageRequest request = new FixedRateScheduledMessageRequest(templateId, variables, 10000L);
        UUID taskId = messageController.setScheduling(request);
        messageController.getCurrentScheduledMessages(taskId);
    }

    @Test
    public void getIncorrectTaskById_ThrowException() {
        assertThrows(
                ScheduledTaskNotFound.class,
                () -> messageController.getCurrentScheduledMessages(UUID.randomUUID())
        );
    }
}
