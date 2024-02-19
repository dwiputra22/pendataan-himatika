package com.pendataan.workshop.config.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnggotaService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with nim %s not found";

    @Autowired
    private final AnggotaRepository anggotaRepository;

    @Override
    public UserDetails loadUserByUsername(String nim) throws UsernameNotFoundException {
        return anggotaRepository.findByNim(nim)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, nim)));
    }
}
