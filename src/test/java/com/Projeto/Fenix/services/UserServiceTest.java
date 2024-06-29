package com.Projeto.Fenix.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @DisplayName("Cria usuário com sucesso")
    @Test
    void createNewUserSuccess() {
    }

    @DisplayName("Tenta criar usuário por com email duplicado")
    @Test
    void createNewUserSuccessWithDuplicatedEmail() {
    }

    @DisplayName("Tenta criar usuário com username duplicado")
    @Test
    void createNewUserSuccessWithDuplicatedUsername() {
    }

    @DisplayName("Tenta criar usuário com campos nulos")
    @Test
    void createNewUserSuccessWithNullFields() {
    }

    @DisplayName("Usuário tem autorização para realizar ação")
    @Test
    void validateUserAuthorizationSuccess() {
    }

    @DisplayName("Usuário não tem autorização para realizar ação")
    @Test
    void validateUserAuthorizationFailed() {
    }

    @DisplayName("Procura usuário por email com sucesso")
    @Test
    void findUserByUserEmailSuccess() {
    }

    @DisplayName("Procura usuário por email sem resultados")
    @Test
    void findUserByUserEmailWithNoResults() {
    }

    @DisplayName("Procura usuário por email com campos nulos")
    @Test
    void findUserByUserEmailWithNullFields() {
    }

    @DisplayName("Procura usuário por usuários com sucesso")
    @Test
    void findUserByUserUsernameSuccess() {
    }

    @DisplayName("Procura usuário por usuário sem resultados")
    @Test
    void findUserByUserUsernameWithNoResults() {
    }

    @DisplayName("Procura usuário por username com campos nulos")
    @Test
    void findUserByUserUsernameWithNullFields() {
    }

    @DisplayName("Procura usuário por Id com sucesso")
    @Test
    void findUserByUserIdSuccess() {
    }

    @DisplayName("Procura usuário por Id sem resultados")
    @Test
    void findUserByUserIdWithNoResults() {
    }

    @DisplayName("Localiza usuário por token")
    @Test
    void findUserByToken() {
    }

    @DisplayName("Retorna informação pública do usuário")
    @Test
    void findUserByIdPublicInfo() {
    }
}