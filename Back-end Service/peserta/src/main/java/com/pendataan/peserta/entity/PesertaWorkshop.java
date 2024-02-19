package com.pendataan.peserta.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "peserta_workshop")
public class PesertaWorkshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "email")
    private String email;
    @Column(name = "asalInstansi")
    private String asalInstansi;
    @Column(name = "pekerjaan")
    private String pekerjaan;
    @Column(name = "judul_workshop")
    private String judulWorkshop;
    @Column(name = "asalInfo")
    private String asalInfo;
    @Column(name = "kesan")
    private String kesan;
    @Column(name = "pesan")
    private String pesan;
    @Column(name = "createdDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @Column(name = "updatedDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;
    @Column(name = "kodeUnik")
    private String kodeUnik;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;
}
