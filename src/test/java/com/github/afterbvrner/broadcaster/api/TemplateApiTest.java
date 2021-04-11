package com.github.afterbvrner.broadcaster.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TemplateApiTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveTemplate() throws Exception {
        TemplateRequest request = new TemplateRequest(
                "id1",
                "template $test$",
                List.of(new URL("http://localhost:8081"), new URL("http://localhost:8082"))
        );
        mockMvc.perform(post("/template")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void getTemplate() throws Exception {
        TemplateRequest request = new TemplateRequest(
                "id2",
                "template $test$",
                List.of(new URL("http://localhost:8081"), new URL("http://localhost:8082"))
        );
        mockMvc.perform(post("/template")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(get("/template/id2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(
                objectMapper.readValue(
                        result.getResponse().getContentAsString(),
                        TemplateRequest.class),
                request,
                "Request and response are not equal"
        );
    }
}
