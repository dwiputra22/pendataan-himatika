package com.pendataan.workshop.repositories;

import com.pendataan.workshop.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
}
