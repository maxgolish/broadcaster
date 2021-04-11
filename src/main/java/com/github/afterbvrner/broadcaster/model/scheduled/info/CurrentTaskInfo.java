package com.github.afterbvrner.broadcaster.model.scheduled.info;

import lombok.Value;

import java.util.UUID;

@Value
public class CurrentTaskInfo {
    UUID id;
    ScheduledMessageInfo info;
}
