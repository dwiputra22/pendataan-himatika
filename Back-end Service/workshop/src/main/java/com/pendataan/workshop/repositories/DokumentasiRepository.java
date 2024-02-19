package com.pendataan.workshop.repositories;

import com.pendataan.workshop.entity.DokumentasiWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DokumentasiRepository extends JpaRepository<DokumentasiWorkshop, Long> {

    @Transactional
    DokumentasiWorkshop findByDocName(String docName);

//    void deleteByWorkshopId(Long workshopId);

    List<DokumentasiWorkshop> getByWorkshopId(Long workshopId);

}
