package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.users.Users;
import com.pendataan.workshop.registration.token.ConfirmationToken;
import com.pendataan.workshop.registration.token.ConfirmationTokenService;
import com.pendataan.workshop.repositories.UsersRepository;
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
public class UsersService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with nim %s not found";

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String nim) throws UsernameNotFoundException {
        return usersRepository.findByNim(nim)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, nim)));
    }

    public String signUpUser(Users users) {
        boolean userExistEmail = usersRepository
                .findByEmail(users.getEmail())
                .isPresent();
        if (userExistEmail) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("Email Anda Sudah di Daftarkan");
        }

        boolean userExistNim = usersRepository
                .findByNim(users.getNim())
                .isPresent();
        if (userExistNim) {
            throw new IllegalStateException("Nim Anda Sudah di Daftarkan");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(users.getPassword());
        users.setPassword(encodedPassword);
        usersRepository.save(users);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                users
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }

    public int enableUsers(String email) {
        return usersRepository.enableUsers(email);
    }
    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();
        usersRepository.findAll()
                .forEach(users::add);
        return users;
    }

    public Optional<Users> getUsers(String nim) {
        Optional<Users> usersId = usersRepository.findByNim(nim);
        return usersId;
    }

    public Users registerUsers(Users users) {
        Users register = usersRepository.save(users);
        return register;
    }



}
