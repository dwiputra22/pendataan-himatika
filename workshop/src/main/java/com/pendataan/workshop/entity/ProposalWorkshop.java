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
@Table(name = "propworkshop")
public class ProposalWorkshop {
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
    @NotBlank(message = "Masukkan Nama Pembicara!")
    @Column(name = "pembicara")
    private String pembicara;
    @NotBlank(message = "Masukkan Judul Workshop!")
    @Column(name = "judul_workshop")
    private String judulWorkshop;
    @Column(name = "tgl_workshop")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalWorkshop;
    @Transient
    private MultipartFile suratProposal;
    @Lob
    private byte[] proposalByte;
    @Column(name = "type_file")
    private String type;
    @Column(name = "docName")
    private String docName;
    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @Override
    public String toString() {

        return "Proposal = [id=" + id + ", nim=" + nim + ", nama=" + nama  + ", Tahun Kepengurusan=" + tahunKepengurusan
                + ", pembicara=" + pembicara + ", judulWorkshop=" + judulWorkshop + ", tanggalWorkshop=" + tanggalWorkshop
                + ", suratProposal=" + suratProposal + ", type=" + type + ", docName=" + docName + "]";
    }
}
