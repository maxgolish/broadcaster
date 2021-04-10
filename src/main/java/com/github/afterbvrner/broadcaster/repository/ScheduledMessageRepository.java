package com.github.afterbvrner.broadcaster.repository;

import com.github.afterbvrner.broadcaster.entity.ScheduledMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessageEntity, UUID> {
}
