package com.tullave.recharges.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider();
        ReflectionTestUtils.setField(jwtProvider, "secret",
                "ClaveSecretaSuperSeguraConLongitudSuficienteParaHMAC256");
    }

    @Test
    void validateToken_TokenInvalido() {
        assertFalse(jwtProvider.validateToken("token.invalido.aqui"));
    }

    @Test
    void validateToken_TokenNull() {
        assertFalse(jwtProvider.validateToken(null));
    }

    @Test
    void validateToken_TokenVacio() {
        assertFalse(jwtProvider.validateToken(""));
    }
}