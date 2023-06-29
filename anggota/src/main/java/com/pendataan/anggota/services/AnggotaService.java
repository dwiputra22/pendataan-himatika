package com.pendataan.anggota.services;

import com.pendataan.anggota.entity.Anggota;
import com.pendataan.anggota.registration.token.ConfirmationToken;
import com.pendataan.anggota.registration.token.ConfirmationTokenService;
import com.pendataan.anggota.repositories.AnggotaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnggotaService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with nim %s not found";

    private final AnggotaRepository anggotaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String nim) throws UsernameNotFoundException {
        return anggotaRepository.findByNim(nim)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, nim)));
    }

    public String signUpUser(Anggota anggota) {
        boolean userExistEmail = anggotaRepository
                .findByEmail(anggota.getEmail())
                .isPresent();
        if (userExistEmail) {
            throw new IllegalStateException("Email Anda Sudah di Daftarkan");
        }

        boolean userExistNim = anggotaRepository
                .findByNim(anggota.getNim())
                .isPresent();
        if (userExistNim) {
            throw new IllegalStateException("Nim Anda Sudah di Daftarkan");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(anggota.getPassword());
        anggota.setPassword(encodedPassword);
        anggota.setDateCreated(LocalDateTime.now());
        anggotaRepository.save(anggota);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                anggota
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }

    public int enableUsers(String email) {
        return anggotaRepository.enableUsers(email);
    }
    public List<Anggota> getAllUsers() {
        List<Anggota> users = new ArrayList<>();
        anggotaRepository.findAll()
                .forEach(users::add);
        return users;
    }

    public Optional<Anggota> getUsers(String nim) {
        Optional<Anggota> usersId = anggotaRepository.findByNim(nim);
        return usersId;
    }

    public Anggota registerUsers(Anggota anggota) {
        Anggota register = anggotaRepository.save(anggota);
        return register;
    }



}
