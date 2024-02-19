package com.pendataan.workshop.repositories;

import com.pendataan.workshop.entity.CustomLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomLogRepository extends JpaRepository<CustomLog, Long> {
}
