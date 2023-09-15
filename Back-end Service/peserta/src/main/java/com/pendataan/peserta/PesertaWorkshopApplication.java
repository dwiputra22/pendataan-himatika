package com.pendataan.peserta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.pendataan.workshop")
public class PesertaWorkshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(PesertaWorkshopApplication.class, args);
    }
}