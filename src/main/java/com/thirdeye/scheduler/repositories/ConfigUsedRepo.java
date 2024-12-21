package com.thirdeye.scheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thirdeye.scheduler.entity.ConfigUsed;

@Repository
public interface ConfigUsedRepo extends JpaRepository<ConfigUsed, Long> {
}
