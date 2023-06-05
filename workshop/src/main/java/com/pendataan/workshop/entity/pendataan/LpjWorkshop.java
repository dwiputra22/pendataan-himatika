package com.pendataan.workshop.entity.pendataan;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "lpjworkshop")
public class LpjWorkshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Masukkan Nim Anda!")
    @Column(name = "nim")
    private String nim;
    @NotBlank(message = "Masukkan Nama Anda!")
    @Column(name = "nama")
    private String nama;
    @NotBlank(message = "Masukkan Nama Pembicara!")
    @Column(name = "pembicara")
    private String pembicara;
    @NotBlank(message = "Masukkan Judul Workshop!")
    @Column(name = "judulWorkshop")
    private String judulWorkshop;
    @NotBlank(message = "Masukkan Tanggal Workshop!")
    @Column(name = "tglWorkshop")
    private LocalDate tanggalWorkshop;
    @Lob
    @Column(name = "file")
    private byte[] suratLpj;
    @Column(name = "docName")
    private String docName;
}
