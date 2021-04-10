package com.github.afterbvrner.broadcaster.service;

import com.github.afterbvrner.broadcaster.entity.ScheduledMessageEntity;
import com.github.afterbvrner.broadcaster.exception.ScheduledTaskNotFound;
import com.github.afterbvrner.broadcaster.model.Message;
import com.github.afterbvrner.broadcaster.model.scheduled.info.ScheduleMessageInfo;
import com.github.afterbvrner.broadcaster.repository.ScheduledMessageRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchedulerService {

    private final TaskScheduler taskScheduler;
    private final BroadcastService broadcastService;
    private final Map<UUID, ScheduledTaskInfo> currentTasks = new HashMap<>();
    private final ScheduledMessageRepository scheduledMessageRepository;

    public void schedule(ScheduleMessageInfo info) {
        ScheduledMessageEntity messageEntity = info.toEntity();
        ScheduledMessageEntity savedMessageEntity = scheduledMessageRepository.save(messageEntity);
        ScheduledFuture<?> scheduledFuture = runTask(
                new Message(savedMessageEntity.getMessage()),
                info.getRecipients(),
                savedMessageEntity.getTrigger()
        );
        currentTasks.put(
                savedMessageEntity.getId(),
                new ScheduledTaskInfo(scheduledFuture, info)
        );
    }

    public List<ScheduleMessageInfo> getCurrentTasks() {
        return currentTasks
                .values()
                .stream()
                .map(ScheduledTaskInfo::getInfo)
                .collect(Collectors.toList());
    }

    private ScheduledFuture<?> runTask(Message message, List<String> recipients, Trigger trigger) {
        return taskScheduler.schedule(
                () -> broadcastService.send(message, recipients),
                trigger
        );
    }

    public boolean stopTask(UUID id) {
        if (!currentTasks.containsKey(id))
            throw new ScheduledTaskNotFound(id);
        if (currentTasks.get(id).getScheduledFuture().cancel(true)) {
            currentTasks.remove(id);
            scheduledMessageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ScheduleMessageInfo getInfoById(UUID id) {
        ScheduledTaskInfo taskInfo = currentTasks.get(id);
        if (taskInfo ==null)
            throw new ScheduledTaskNotFound(id);
        return taskInfo.getInfo();
    }

//    // Необходим для переинициализации шедулера после перезапуска приложения
//    // Неактуально для H2, однако необходим при использовании не in-memory БД
//    @PostConstruct
//    public void init() {
//        scheduledMessageRepository
//                .findAll()
//                .forEach(message -> {
//                    runTask(message.getId(),
//                            new Message(message.getMessage()),
//                            message.getRecipients(),
//                            message.getTrigger()
//                    );
//                });
//    }

    @Value
    private static class ScheduledTaskInfo {
        ScheduledFuture<?> scheduledFuture;
        ScheduleMessageInfo info;
    }
}
