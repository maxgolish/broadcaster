package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.exception.VariableNotFoundInTemplateException;
import com.github.afterbvrner.broadcaster.model.Message;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageCreator {

    private final static String ESCAPED_DOLLAR = "###DOLLAR_SIGN###";

    public Message createMessage(String template, Map<String, String> variables) {
        String constructedMessage = template.replace("\\\\$", ESCAPED_DOLLAR);
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
            throw new VariableNotFoundInTemplateException("Key " + key + " not found in template");
        return initialString.replace(constructVariable(key), value);
    }
}
