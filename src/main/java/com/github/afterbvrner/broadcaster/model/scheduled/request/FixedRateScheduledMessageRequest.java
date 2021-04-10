package com.github.afterbvrner.broadcaster.model.scheduled.request;

import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.model.scheduled.info.FixedRateScheduledMessageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FixedRateScheduledMessageRequest extends ScheduledMessageRequest {

    private long fixedRate;

    @Override
    public FixedRateScheduledMessageInfo convertToInfo(Message message, List<String> recipients) {
        return new FixedRateScheduledMessageInfo(message, recipients, fixedRate);
    }
}
