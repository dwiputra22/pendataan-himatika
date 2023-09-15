package com.pendataan.workshop.repositories;


import com.pendataan.workshop.entity.ProposalWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PropWorkshopRepository extends JpaRepository<ProposalWorkshop, Long> {

    @Transactional
    @Modifying
    @Query("select p from ProposalWorkshop p where p. id like ?1 and p. docName like ?2")
    Optional<ProposalWorkshop> findByDocName(Long id, String docName);
//    @Transactional
//    @Modifying
//    @Query("delete from ProposalWorkshop p where p. id like ?1 and p. workshopId like ?2")
    void deleteByWorkshopId(Long workshopId);

//    @Transactional
//    @Modifying
//    @Query("select p from ProposalWorkshop p where p. id like ?1 and p. workshopId like ?2")
    ProposalWorkshop getByWorkshop(Long workshopId);
}
