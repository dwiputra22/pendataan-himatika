package com.pendataan.workshop.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
    @NotBlank(message = "Masukkan Tahun Kepengurusan Anda!")
    @Column(name = "thnKepengurusan")
    private String tahunKepengurusan;
    @Transient
    private MultipartFile suratLpj;
    @Lob
    @Column(name = "lpjByte")
    private byte[] lpjByte;
    @Column(name = "type_file")
    private String type;
    @Column(name = "docName")
    private String docName;
    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @Column(name = "updatedDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;
    @OneToOne
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

    @Override
    public String toString() {

        return "Lpj = [id=" + id + ", nim=" + nim + ", nama=" + nama + ", Tahun Kepengurusan=" + tahunKepengurusan
                + ", suratProposal=" + suratLpj + ", type=" + type + ", docName=" + docName + "]";
    }
}
