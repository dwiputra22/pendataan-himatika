package com.pendataan.anggota.controllers;


import com.pendataan.anggota.entity.Anggota;
import com.pendataan.anggota.services.AnggotaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/himatika")
public class AnggotaController {

    @Autowired
    private AnggotaService anggotaService;

    @GetMapping(path = "/anggota")
    public ModelAndView getAllUsers() {
        return anggotaService.getAllUsers();
    }

    @GetMapping(path = "/anggota/{nim}")
    public ResponseEntity<Optional<Anggota>> getUsers(
            @RequestParam(value = "nim", defaultValue = "") String nim) {
        return ResponseEntity.ok(anggotaService.getUsers(nim));
    }


}
