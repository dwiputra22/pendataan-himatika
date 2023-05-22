package com.pendataan.workshop.auth;

import com.pendataan.workshop.entity.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String nim;
    private String nama;
    private String password;
    private Role status;
}
