package com.pendataan.anggota.controllers;


import com.pendataan.anggota.entity.Anggota;
import com.pendataan.anggota.services.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/users")
public class AnggotaController {

    @Autowired
    private AnggotaService anggotaService;

    @GetMapping
    public ResponseEntity<List<Anggota>> getAllUsers() {
        return ResponseEntity.ok(anggotaService.getAllUsers());
    }

    @GetMapping(path = "/{nim}")
    public ResponseEntity<Optional<Anggota>> getUsers(
            @RequestParam(value = "nim", defaultValue = "") String nim) {
        return ResponseEntity.ok(anggotaService.getUsers(nim));
    }


}
