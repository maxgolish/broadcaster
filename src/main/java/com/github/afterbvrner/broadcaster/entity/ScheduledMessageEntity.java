package com.github.afterbvrner.broadcaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.Trigger;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ScheduledMessageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String message;
    @ElementCollection
    private List<URL> recipients;

    public abstract Trigger getTrigger();
}
