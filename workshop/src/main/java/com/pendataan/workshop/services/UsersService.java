package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.users.Users;
import com.pendataan.workshop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

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
