package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.entity.TemplateEntity;
import com.github.afterbvrner.broadcaster.exception.TemplateNotFoundException;
import com.github.afterbvrner.broadcaster.exception.VariableNotFoundInTemplateException;
import com.github.afterbvrner.broadcaster.exception.WrongVariableDefinitionException;
import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.repository.TemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class MessageService {
    private final static String ESCAPED_DOLLAR = "###DOLLAR_SIGN###";

    private final TemplateRepository templateRepository;
    private final BroadcastService broadcastService;

    public void send(String templateId, Map<String, String> variables) {
        TemplateEntity template = templateRepository
                .findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundException(templateId));
        Message message = createMessage(template.getTemplate(), variables);
        broadcastService.send(message, template.getRecipients());
    }

    private Message createMessage(String template, Map<String, String> variables) {
        String constructedMessage = template.replaceAll("\\\\$", ESCAPED_DOLLAR);

        for (var variable : variables.entrySet())
            constructedMessage = replaceVariable(constructedMessage, variable.getKey(), variable.getValue());
        constructedMessage = constructedMessage.replaceAll(ESCAPED_DOLLAR, "$");
        return new Message(constructedMessage);
    }

    private String constructVariable(String key) {
        return "$" + key + "$";
    }

    private String replaceVariable(String initialString, String key, String value) {
        if (!initialString.contains(constructVariable(key)))
            throw new WrongVariableDefinitionException("Key " + key + " not found in template");
        return initialString.replaceAll(constructVariable(key), value);
    }
}
