package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.exception.VariableNotFoundInTemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageCreatorTest {
    @Autowired
    private MessageCreator messageCreator;

    @Test
    public void createMessage() {
        String template = "Hello, $username$! Welcome to the $application$ application";
        String username = "afterbvrner";
        String application = "broadcaster";
        String correctString = "Hello, afterbvrner! Welcome to the broadcaster application";
        Map<String, String> vars = new HashMap<>();
        vars.put("username", username);
        vars.put("application", application);
        String createdMessage = messageCreator.createMessage(template, vars).getMessage();
        assertEquals(correctString, createdMessage, "Messages are not equal");
    }

    @Test
    public void createMessageUsingWrongArgs_ThrowException() {
        String template = "Hello, $username$! Welcome to the $application$ application";
        Map<String, String> vars = new HashMap<>();
        vars.put("wrongarg", "replacement");
        assertThrows(
                VariableNotFoundInTemplateException.class,
                () -> messageCreator.createMessage(template, vars)
        );
    }
}
