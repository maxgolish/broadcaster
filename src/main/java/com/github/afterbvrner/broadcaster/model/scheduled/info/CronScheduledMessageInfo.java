package com.github.afterbvrner.broadcaster.model.scheduled.info;

import com.github.afterbvrner.broadcaster.entity.CronScheduledMessageEntity;
import com.github.afterbvrner.broadcaster.model.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URL;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CronScheduledMessageInfo extends ScheduledMessageInfo {

    private String expression;

    public CronScheduledMessageInfo(Message message, List<URL> recipients, String expression) {
        super(message, recipients);
        this.expression = expression;
    }

    @Override
    public CronScheduledMessageEntity toEntity() {
        return CronScheduledMessageEntity
                .builder()
                .message(getMessage().getMessage())
                .recipients(getRecipients())
                .cron(expression)
                .build();
    }
}
