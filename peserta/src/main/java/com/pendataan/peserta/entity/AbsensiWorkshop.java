package com.pendataan.peserta.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "absensi_peserta")
public class AbsensiWorkshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nim")
    private String nim;
    @Column(name = "nama")
    private String nama;
    @Column(name = "email")
    private String email;
    @Column(name = "programStudi")
    private String programStudi;
    @Column(name = "thnAngkatan")
    private Integer thnAngkatan;
    @Column(name = "tglWorkshop")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalWorkshop;
    @Column(name = "judulWorkshop")
    private String judulWorkshop;
    @Column(name = "kesan")
    private String kesan;
    @Column(name = "saran")
    private String saran;
    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;
}
