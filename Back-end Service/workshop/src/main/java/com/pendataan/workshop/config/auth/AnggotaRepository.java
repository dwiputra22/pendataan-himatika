package com.pendataan.workshop.config.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AnggotaRepository extends JpaRepository<Anggota, Long> {
    Optional<Anggota> findByNim(String nim);

    Optional<Anggota> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Anggota a " +
            "SET a.enabled = TRUE WHERE a.nim = ?1")
    int enableUsers(String nim);
}
