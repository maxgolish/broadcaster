package com.github.afterbvrner.broadcaster.repository;

import com.github.afterbvrner.broadcaster.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<TemplateEntity, String> {
}
