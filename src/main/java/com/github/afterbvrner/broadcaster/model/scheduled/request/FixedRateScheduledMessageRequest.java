package com.github.afterbvrner.broadcaster.model.scheduled.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.model.scheduled.info.FixedRateScheduledMessageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class FixedRateScheduledMessageRequest extends ScheduledMessageRequest {

    @NotNull
    private Long fixedRate;

    @Override
    public FixedRateScheduledMessageInfo convertToInfo(Message message, List<URL> recipients) {
        return new FixedRateScheduledMessageInfo(message, recipients, fixedRate);
    }

    @JsonCreator
    public FixedRateScheduledMessageRequest(@JsonProperty("templateId") String templateId,
                                            @JsonProperty("variables") Map<String, String> variables,
                                            @JsonProperty("fixedRate") Long fixedRate) {
        this.setTemplateId(templateId);
        this.setVariables(variables);
        this.fixedRate = fixedRate;
    }
}
