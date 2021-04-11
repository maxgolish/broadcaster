package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.exception.UnusedVariablesException;
import com.github.afterbvrner.broadcaster.exception.VariableNotFoundInTemplateException;
import com.github.afterbvrner.broadcaster.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageCreator {

    private final static String ESCAPED_DOLLAR = "###DOLLAR_SIGN###";

    public Message createMessage(String template, Map<String, String> variables) {
        String constructedMessage = template.replace("\\\\$", ESCAPED_DOLLAR);
        for (var variable : variables.entrySet())
            constructedMessage = replaceVariable(constructedMessage, variable.getKey(), variable.getValue());
        checkMessage(constructedMessage);
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

    // Проверка на то, что у нас не осталось неиспользованных переменных
    private void checkMessage(String message) {
        String regex = ".*\\$[a-zA-Z]*\\$.*";
        if (message.matches(regex)) {
            List<String> matches = Pattern.compile("\\$[a-zA-Z]*\\$")
                    .matcher(message)
                    .results()
                    .map(MatchResult::group)
                    .collect(Collectors.toList());
            throw new UnusedVariablesException(matches);
        }
    }
}
