package com.pendataan.peserta.repositories;

import com.pendataan.peserta.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    Workshop getByJudulWorkshop(String judulWorkshop);

    List<Workshop> getByStatus(String status);

    Workshop findByJudulWorkshop(String judulWorkshop);

    Workshop findByDocName(String docName);
}
