package com.github.afterbvrner.broadcaster.entity;

import lombok.*;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import javax.persistence.Entity;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class FixedScheduledMessageEntity extends ScheduledMessageEntity {
    private long fixedRate;

    @Override
    public Trigger getTrigger() {
        return new PeriodicTrigger(fixedRate);
    }

    @Builder
    public FixedScheduledMessageEntity(UUID id, String message, List<URL> recipients, long fixedRate) {
        super(id, message, recipients);
        this.fixedRate = fixedRate;
    }
}
