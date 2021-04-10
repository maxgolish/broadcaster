package com.github.afterbvrner.broadcaster.model.scheduled.info;

import com.github.afterbvrner.broadcaster.entity.FixedScheduledMessageEntity;
import com.github.afterbvrner.broadcaster.model.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FixedRateSchedulingMessageInfo extends ScheduleMessageInfo {

    private long fixedRate;

    public FixedRateSchedulingMessageInfo(Message message, List<String> recipients, long fixedRate) {
        super(message, recipients);
        this.fixedRate = fixedRate;
    }

    @Override
    public FixedScheduledMessageEntity toEntity() {
        return FixedScheduledMessageEntity
                .builder()
                .message(getMessage().getMessage())
                .recipients(getRecipients())
                .fixedRate(fixedRate)
                .build();
    }
}
