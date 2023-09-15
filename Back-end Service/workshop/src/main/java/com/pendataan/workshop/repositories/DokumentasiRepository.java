package com.pendataan.workshop.repositories;

import com.pendataan.workshop.entity.DokumentasiWorkshop;
import com.pendataan.workshop.entity.LpjWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DokumentasiRepository extends JpaRepository<DokumentasiWorkshop, Long> {

    @Transactional
    @Modifying
    @Query("select d from DokumentasiWorkshop d where d. id like ?1 and d. docName like ?2")
    DokumentasiWorkshop findByDocName(Long id, String docName);

    void deleteByWorkshopId(Long workshopId);

    LpjWorkshop getByWorkshopId(Long workshopId);

}
