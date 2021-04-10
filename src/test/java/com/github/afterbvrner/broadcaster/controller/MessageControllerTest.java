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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageControllerTest {

    @Autowired
    private MessageController messageController;
    @Autowired
    private TemplateController templateController;

    @Test
    public void sendMessageToCorrectUrl() {
        String templateId = "testid1";
        String template = "Welcome to the $test$";
        List<String> recipients = new ArrayList<>();
        recipients.add("https://httpbin.org/post");
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
    public void sendMessageToIncorrectUrl_ThrowException() {
        String templateId = "testid2";
        String template = "Welcome to the $test$";
        List<String> recipients = new ArrayList<>();
        recipients.add("https://~~~.com");
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
    public void startScheduledMessaging() {
        String templateId = "testid3";
        String template = "Welcome to the $test$";
        List<String> recipients = new ArrayList<>();
        recipients.add("https://httpbin.org/post");
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
