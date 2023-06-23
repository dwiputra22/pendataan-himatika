package com.pendataan.workshop.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dokumentasi_workshop")
public class DokumentasiWorkshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "judul_workshop")
    private String judulWorkshop;
    @Column(name = "tgl_dokumentasi")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalDokumentasi;
    @Transient
    private MultipartFile fileDokumentasi; //tambahkan "multiple" pada forminput file dibagian input supaya bisa input banyak
    @Lob
    @Column(name = "file_byte")
    private byte[] dokumentasiByte;
    @Column(name = "type_file")
    private String type;
    @Column(name = "docName")
    private String docName;
    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;
}
