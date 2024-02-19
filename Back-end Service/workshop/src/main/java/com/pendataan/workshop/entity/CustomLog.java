package com.pendataan.workshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "log_info")
public class CustomLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nim")
    private Integer nim;
    @Column(name = "nama")
    private String nama;
    @Column(name = "aktivitas")
    private String aktivitas;
    @Column(name = "judulWorkshop")
    private String judulWorkshop;
    @Column(name = "tanggal")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggal;
}
