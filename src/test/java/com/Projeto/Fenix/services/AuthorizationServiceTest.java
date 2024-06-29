package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class AuthorizationServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthorizationService authorizationService;

    User theUser = new User();
    @BeforeEach
    public void setup(){
        theUser.setUserUsername("aUsername");
    }
    @DisplayName("Retorna usuário baseado no token recebido")
    @Test
    void loadUserByUsername() {
        given(userRepository.findUserByUserUsername(theUser.getUserUsername())).willReturn(theUser);

        authorizationService.loadUserByUsername(theUser.getUserUsername());

        verify(userRepository,times(1)).findUserByUserUsername(any());
    }
}