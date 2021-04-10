package com.github.afterbvrner.broadcaster.model.scheduled;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunningScheduledMessage {
    private UUID id;
    private String message;
    private SchedulingType type;
    private String cron;
    private Long fixedRate;
}
