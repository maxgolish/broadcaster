package com.github.afterbvrner.broadcaster.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.afterbvrner.broadcaster.model.MessageRequest;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.request.CronScheduledMessageRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.request.FixedRateScheduledMessageRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.request.ScheduledMessageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageApiTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void sendMessage() throws Exception {
        TemplateRequest request = new TemplateRequest(
                "id1",
                "template $test$",
                List.of("https://httpbin.org/post")
        );
        mockMvc.perform(post("/template")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        MessageRequest messageRequest = new MessageRequest("id1", Map.of("test", "message"));
        mockMvc.perform(post("/messages/default")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void sendCronScheduledMessage() throws Exception {
        TemplateRequest request = new TemplateRequest(
                "id2",
                "template $test$",
                List.of("https://httpbin.org/post")
        );
        mockMvc.perform(post("/template")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        ScheduledMessageRequest messageRequest = new CronScheduledMessageRequest(
                "id2",
                Map.of("test", "message"),
                "* * * * * *");
        mockMvc.perform(post("/messages/scheduled")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void sendFixedRateScheduledMessage() throws Exception {
        TemplateRequest request = new TemplateRequest(
                "id3",
                "template $test$",
                List.of("https://httpbin.org/post")
        );
        mockMvc.perform(post("/template")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        ScheduledMessageRequest messageRequest = new FixedRateScheduledMessageRequest(
                "id3",
                Map.of("test", "message"),
                10000L);
        mockMvc.perform(post("/messages/scheduled")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isOk());
    }
}
