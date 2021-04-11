package com.github.afterbvrner.broadcaster.model.scheduled.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.model.MessageRequest;
import com.github.afterbvrner.broadcaster.model.scheduled.info.ScheduledMessageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;
import java.util.Map;

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
@NoArgsConstructor
public abstract class ScheduledMessageRequest extends MessageRequest {
    public abstract ScheduledMessageInfo convertToInfo(Message message, List<URL> recipients);
    public ScheduledMessageRequest(String templateId, Map<String, String> variables) {
        super(templateId, variables);
    }
}
