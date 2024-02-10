package com.Projeto.Fenix.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidService {

    UUID generateUUID(){
        return UUID.randomUUID();
    }
}
