package com.pendataan.workshop.repositories;

import com.pendataan.workshop.entity.LpjWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface LpjWorkshopRepository extends JpaRepository<LpjWorkshop, Long> {

    Optional<LpjWorkshop> findByDocName(String docName);
    @Transactional
    @Modifying
    @Query("delete from LpjWorkshop p where p. id like ?1 and p. judulWorkshop like ?2")
    void deleteByJudulWorkshop(Long id, String judulWorkshop);
}
