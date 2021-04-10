package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.exception.ScheduledTaskNotFound;
import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.model.scheduled.info.CronScheduledMessageInfo;
import com.github.afterbvrner.broadcaster.model.scheduled.info.FixedRateScheduledMessageInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SchedulerServiceTest {

    @Autowired
    private SchedulerService schedulerService;

    @AfterEach
    public void tearDown() {
        schedulerService.shutdown();
    }

    @Test
    public void createCronScheduling_ReturnValidInfo() {
        List<String> recipients = new ArrayList<>();
        recipients.add("https://httpbin.org/post");
        CronScheduledMessageInfo initialInfo = new CronScheduledMessageInfo(
                new Message("testmessage"),
                recipients,
                "* * * * * *"
        );
        UUID taskId = schedulerService.schedule(initialInfo);
        CronScheduledMessageInfo info = (CronScheduledMessageInfo) schedulerService.getInfoById(taskId);
        assertEquals(initialInfo.getMessage(), info.getMessage(), "Messages are not equal");
        assertEquals(initialInfo.getExpression(), info.getExpression(), "Expressions are not equal");
    }

    @Test
    public void createFixedScheduling_ReturnValidInfo() {
        List<String> recipients = new ArrayList<>();
        recipients.add("https://httpbin.org/post");
        FixedRateScheduledMessageInfo initialInfo = new FixedRateScheduledMessageInfo(
                new Message("testmessage"),
                recipients,
                10000
        );
        UUID taskId = schedulerService.schedule(initialInfo);
        FixedRateScheduledMessageInfo info = (FixedRateScheduledMessageInfo) schedulerService.getInfoById(taskId);
        assertEquals(initialInfo.getMessage(), info.getMessage(), "Messages are not equal");
        assertEquals(initialInfo.getFixedRate(), info.getFixedRate(), "Rates are not equal");
    }

    @Test
    public void getNonExistentTask_ThrowException() {
        UUID taskId = UUID.randomUUID();
        assertThrows(ScheduledTaskNotFound.class, () -> schedulerService.getInfoById(taskId));
    }
}
