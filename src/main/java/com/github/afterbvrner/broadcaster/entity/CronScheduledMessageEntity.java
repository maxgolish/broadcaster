package com.github.afterbvrner.broadcaster.entity;

import lombok.*;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

import javax.persistence.Entity;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CronScheduledMessageEntity extends ScheduledMessageEntity {
    private String cron;

    @Override
    public Trigger getTrigger() {
        return new CronTrigger(cron);
    }

    @Builder
    public CronScheduledMessageEntity(UUID id, String message, List<URL> recipients, String cron) {
        super(id, message, recipients);
        this.cron = cron;
    }
}
