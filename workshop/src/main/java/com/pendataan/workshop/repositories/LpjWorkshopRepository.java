package com.pendataan.workshop.repositories;

import com.pendataan.workshop.entity.pendataan.LpjWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface LpjWorkshopRepository extends JpaRepository<LpjWorkshop, Long> {

    Optional<LpjWorkshop> findByDocName(String docName);
}
