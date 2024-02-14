package com.Projeto.Fenix.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidService {

    @Bean
    UUID generateUUID(){
        return UUID.randomUUID();
    }
}
