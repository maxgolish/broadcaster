package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.exception.EndpointNotAvailableException;
import com.github.afterbvrner.broadcaster.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BroadcastServiceTest {

    @Autowired
    private RestBroadcastService broadcastService;

    @Test
    public void sendToValidUrl() throws MalformedURLException {
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://httpbin.org/post"));
        broadcastService.send(new Message("testmessage"), recipients);
    }

    @Test
    public void sendToInvalidUrl_ThrowException() throws MalformedURLException {
        List<URL> recipients = new ArrayList<>();
        recipients.add(new URL("https://~~~.com"));
        assertThrows(
                EndpointNotAvailableException.class,
                () -> broadcastService.send(new Message("testmessage"), recipients)
        );
    }
}
