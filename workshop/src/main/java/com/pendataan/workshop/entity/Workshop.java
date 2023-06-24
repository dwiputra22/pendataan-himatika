package com.pendataan.workshop.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "Masukkan Judul Workshop!")
    @Column(name = "judul_workshop")
    private String judulWorkshop;
    @NotBlank(message = "Masukkan Nama Pembicara!")
    @Column(name = "pembicara")
    private String pembicara;
    @Column(name = "tgl_workshop")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalWorkshop;
    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;
}
