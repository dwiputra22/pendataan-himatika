package com.pendataan.workshop.repositories;


import com.pendataan.workshop.entity.ProposalWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PropWorkshopRepository extends JpaRepository<ProposalWorkshop, Long> {

    Optional<ProposalWorkshop> findByDocName(String docName);
    @Transactional
    @Modifying
    @Query("delete from ProposalWorkshop p where p. id like ?1 and p. judulWorkshop like ?2")
    void deleteByJudulWorkshop(Long id, String judulWorkshop);

    @Transactional
    List<ProposalWorkshop> getByJudulWorkshop(String judulWorkshop);
}
