package com.pendataan.anggota.registration;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String nim;
    private final String nama;
    private final String thnKepengurusan;
    private final String divisi;
    private final String jabatan;
    private final String noTelepon;
    private final String email;
    private final String password;
}
