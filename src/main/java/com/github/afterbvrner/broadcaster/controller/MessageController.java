package com.github.afterbvrner.broadcaster.controller;

import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.model.MessageRequest;
import com.github.afterbvrner.broadcaster.service.BroadcastService;
import com.github.afterbvrner.broadcaster.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.send(messageRequest.getTemplateId(), messageRequest.getVariables());
    }
}
