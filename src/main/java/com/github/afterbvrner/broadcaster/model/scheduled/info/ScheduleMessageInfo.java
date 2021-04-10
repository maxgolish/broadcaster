package com.github.afterbvrner.broadcaster.model.scheduled.info;

import com.github.afterbvrner.broadcaster.entity.ScheduledMessageEntity;
import com.github.afterbvrner.broadcaster.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ScheduleMessageInfo {
    private Message message;
    private List<String> recipients;

    public abstract ScheduledMessageEntity toEntity();
}
