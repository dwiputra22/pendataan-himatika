package com.pendataan.workshop.entity.pendataan;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "Masukkan Nama Pembicara!")
    @Column(name = "pembicara")
    private String pembicara;
    @NotBlank(message = "Masukkan Judul Workshop!")
    @Column(name = "judul_workshop")
    private String judulWorkshop;
    @Column(name = "tgl_workshop")
    private String tanggalWorkshop;
    @Transient
    private MultipartFile suratProposal;
    @Lob
    private byte[] proposalByte;
    @Column(name = "type_file")
    private String type;
    @Column(name = "docName")
    private String docName;

    @Override
    public String toString() {

        return "Proposal [id=" + id + ", nim=" + nim + ", nama=" + nama + ", pembicara=" + pembicara + ", judulWorkshop="
                + judulWorkshop + ", tanggalWorkshop=" + tanggalWorkshop + ", suratProposal=" + suratProposal +
                ", type=" + type + ", docName=" + docName + "]";
    }
}
