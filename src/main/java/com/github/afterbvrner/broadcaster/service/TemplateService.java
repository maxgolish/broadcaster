package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.entity.TemplateEntity;
import com.github.afterbvrner.broadcaster.exception.TemplateAlreadyExistsException;
import com.github.afterbvrner.broadcaster.exception.TemplateNotFoundException;
import com.github.afterbvrner.broadcaster.exception.WrongVariableDefinitionException;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import com.github.afterbvrner.broadcaster.repository.TemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class TemplateService {

    private final static String DOLLAR_ESCAPE = "###DOLLAR_SIGN###";

    private final TemplateRepository templateRepository;

    public void saveTemplate(TemplateRequest templateRequest) {
        checkTemplate(templateRequest.getTemplate());
        TemplateEntity templateEntity = new TemplateEntity(templateRequest);
        if (templateRepository.findById(templateRequest.getTemplateId()).isPresent())
            throw new TemplateAlreadyExistsException(templateRequest.getTemplateId());
        templateRepository.save(templateEntity);
    }

    public TemplateEntity getTemplate(String id) {
        return templateRepository
                .findById(id)
                .orElseThrow(() -> new TemplateNotFoundException(id));
    }

    private void checkTemplate(String template) {
        boolean result = Pattern.compile("\\$.*\\$")
                .matcher(template.replace("\\$", DOLLAR_ESCAPE))
                .results()
                .map(MatchResult::group)
                .allMatch(this::isValidVariable);
        if (!result)
            throw new WrongVariableDefinitionException();
    }

    // Темплейт поддерживает только текстовые имена переменных с нижними подчеркиваниями
    private boolean isValidVariable(String variable) {
        return variable.matches("\\$[a-zA-Z_]*\\$");
    }
}
