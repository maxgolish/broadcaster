package com.github.afterbvrner.broadcaster.model.scheduled.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.model.MessageRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.info.ScheduleMessageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CronScheduledMessageRequest.class, name = "cron"),
        @JsonSubTypes.Type(value = FixedRateScheduledMessageRequest.class, name = "fixed")
})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
public abstract class ScheduledMessageRequest extends MessageRequest {
    public abstract ScheduleMessageInfo convertToInfo(Message message, List<String> recipients);
}
