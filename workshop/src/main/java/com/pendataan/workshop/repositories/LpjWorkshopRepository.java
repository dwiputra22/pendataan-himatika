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

    @Transactional
    @Modifying
    @Query("select l from LpjWorkshop l where l. id like ?1 and l. docName like ?2")
    Optional<LpjWorkshop> findByDocName(Long id, String docName);
//    @Transactional
//    @Modifying
//    @Query("delete from LpjWorkshop l where l. id like ?1 and l. workshopId like ?2")
    void deleteByWorkshopId(Long workshopId);

//    @Transactional
//    @Modifying
//    @Query("select l from LpjWorkshop l where l. id like ?1 and l. workshopId like ?2")
    LpjWorkshop getByWorkshopId(Long workshopId);
}
