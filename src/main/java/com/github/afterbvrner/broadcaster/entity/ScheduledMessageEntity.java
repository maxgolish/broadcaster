package com.github.afterbvrner.broadcaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.Trigger;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ScheduledMessageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String message;
    @ElementCollection
    private List<String> recipients;

    public abstract Trigger getTrigger();
}
