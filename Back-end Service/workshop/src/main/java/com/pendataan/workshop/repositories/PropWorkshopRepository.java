package com.pendataan.workshop.repositories;


import com.pendataan.workshop.entity.ProposalWorkshop;
import com.pendataan.workshop.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PropWorkshopRepository extends JpaRepository<ProposalWorkshop, Long> {

    @Transactional
    Optional<ProposalWorkshop> findByDocName(String docName);
//    @Transactional
//    @Modifying
//    @Query("delete from ProposalWorkshop p where p. id like ?1 and p. workshopId like ?2")
    void deleteByWorkshopId(Long workshopId);

//    @Transactional
//    @Modifying
//    @Query("select p from ProposalWorkshop p where p. id like ?1 and p. workshopId like ?2")
//    ProposalWorkshop getByWorkshop(Long workshopId);

    ProposalWorkshop getByWorkshopId(Long workshopId);

    Workshop findByWorkshopId(Long workshopId);
}
