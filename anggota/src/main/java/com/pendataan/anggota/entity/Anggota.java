package com.pendataan.anggota.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "anggota")
public class Anggota implements UserDetails {
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
    private String thnKepengurusan;
    @NotBlank(message = "Masukkan Tahun Divisi Anda!")
    @Column(name = "divisi")
    private String divisi;
    @NotBlank(message = "Masukkan Tahun Jabatan Anda!")
    @Column(name = "jabatan")
    private String jabatan;
    @NotBlank(message = "Masukkan Nomor Telepon Anda!")
    @Column(name = "noTelepon")
    private String noTelepon;
    @Email(regexp = "[a-z0-9._%+-]+@perbanas+\\.id",
    flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email Harus Menggunakan Akun Perbanas")
    @NotBlank(message = "Masukkan Email Anda!")
    @Column(name = "email")
    private String email;
    @NotBlank(message = "Masukkan Password Anda!")
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Role status;
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    private Boolean locked = false;
    private Boolean enabled = false;

    public Anggota(String nim, String nama, String thnKepengurusan, String divisi, String jabatan, String noTelepon, String email, String password, Role status) {
        this.nim = nim;
        this.nama = nama;
        this.thnKepengurusan = thnKepengurusan;
        this.divisi = divisi;
        this.jabatan = jabatan;
        this.noTelepon = noTelepon;
        this.email = email;
        this.password = password;
        this.status = status;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(status.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nim;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
