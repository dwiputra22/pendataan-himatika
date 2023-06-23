package com.pendataan.peserta.repositories;

import com.pendataan.peserta.entity.PendaftaranWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PendaftaranRepository extends JpaRepository<PendaftaranWorkshop, Long> {
    List<PendaftaranWorkshop> getByJudulWorkshop(String judulWorkshop);

    @Transactional
    @Modifying
    @Query("select a from PendaftaranWaorkshop a where a. id = ?1 and a. judulWorkshop = ?2")
    PendaftaranWorkshop getByJudulWorkshop(Long id, String judulWorkshop);

    @Transactional
    @Modifying
    @Query("delete from PendaftaranWorkshop p where p. id like ?1 and p. judulWorkshop like ?2")
    void deleteByJudulWorkshop(Long id, String judulWorkshop);
}
