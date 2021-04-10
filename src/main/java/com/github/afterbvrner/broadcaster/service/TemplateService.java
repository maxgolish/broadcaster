package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.entity.TemplateEntity;
import com.github.afterbvrner.broadcaster.exception.TemplateAlreadyExistsException;
import com.github.afterbvrner.broadcaster.exception.TemplateNotFoundException;
import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import com.github.afterbvrner.broadcaster.repository.TemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    public void saveTemplate(TemplateRequest templateRequest) {
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
}
