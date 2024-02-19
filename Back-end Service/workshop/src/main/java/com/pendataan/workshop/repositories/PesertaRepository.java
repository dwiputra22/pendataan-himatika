package com.pendataan.workshop.repositories;

import com.pendataan.workshop.entity.PesertaWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PesertaRepository extends JpaRepository<PesertaWorkshop, Long> {
    List<PesertaWorkshop> getByJudulWorkshop(String judulWorkshop);

    PesertaWorkshop findByKodeUnik(String kodeUnik);

    @Transactional
    @Modifying
    @Query("select a from PesertaWorkshop a where a. id = ?1 and a. judulWorkshop = ?2")
    PesertaWorkshop getByJudulWorkshop(Long id, String judulWorkshop);

    @Transactional
    @Modifying
    @Query("delete from PesertaWorkshop p where p. id like ?1 and p. judulWorkshop like ?2")
    void deleteByJudulWorkshop(Long id, String judulWorkshop);

    PesertaWorkshop getByWorkshopId(Long workshopId);

    PesertaWorkshop getByKodeUnik(String kodeUnik);
}
