package com.github.afterbvrner.broadcaster.entity;

import com.github.afterbvrner.broadcaster.model.TemplateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateEntity {
    @Id
    private String templateId;
    private String template;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> recipients;

    public TemplateEntity(TemplateRequest templateRequest) {
        this.templateId = templateRequest.getTemplateId();
        this.template = templateRequest.getTemplate();
        this.recipients = templateRequest.getRecipients();
    }
}
