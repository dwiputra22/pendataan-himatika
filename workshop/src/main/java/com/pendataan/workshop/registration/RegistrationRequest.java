package com.pendataan.workshop.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String nama;
    private final String nim;
    private final String email;
    private final String password;
}
