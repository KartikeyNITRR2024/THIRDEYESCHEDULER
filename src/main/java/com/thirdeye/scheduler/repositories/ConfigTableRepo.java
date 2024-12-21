package com.thirdeye.scheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thirdeye.scheduler.entity.ConfigTable;


@Repository
public interface ConfigTableRepo extends JpaRepository<ConfigTable, Long> {
}
