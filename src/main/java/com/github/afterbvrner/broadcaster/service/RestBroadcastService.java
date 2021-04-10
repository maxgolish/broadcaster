package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.exception.EndpointNotAvailableException;
import com.github.afterbvrner.broadcaster.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class RestBroadcastService implements BroadcastService {

    private final RestTemplate restTemplate;

    @Override
    public void send(Message message, List<String> receivers) {
        for (var receiver : receivers) {
            try {
                restTemplate.postForEntity(receiver, message, Message.class);
            } catch (HttpClientErrorException e) {
                throw new EndpointNotAvailableException(receiver, e);
            }
        }
    }
}
