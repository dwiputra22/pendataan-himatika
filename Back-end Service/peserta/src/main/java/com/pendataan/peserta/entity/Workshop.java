package com.pendataan.peserta.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "workshop")
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "judul_workshop")
    private String judulWorkshop;
    @Column(name = "pembicara")
    private String pembicara;
    @Column(name = "tgl_workshop")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalWorkshop;
    @Column(name = "description")
    private String description;
    @Column(name = "linkwhatsapp")
    private String linkWhatsapp;
    @Transient
    private MultipartFile poster;
    @Lob
    private byte[] posterByte;
    @Column(name = "type_file")
    private String type;
    @Column(name = "docName")
    private String docName;
    @Column(name = "status")
    private String status;
    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;

    public Workshop(Workshop workshop) {
        this.judulWorkshop = workshop.getJudulWorkshop();
    }
}
