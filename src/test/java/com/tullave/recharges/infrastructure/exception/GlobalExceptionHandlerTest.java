package com.tullave.recharges.infrastructure.exception;

import com.tullave.recharges.domain.exceptions.InvalidRechargeAmountException;
import com.tullave.recharges.domain.exceptions.RechargeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldReturnNotFoundWhenRechargeDoesNotExist() {
        RechargeNotFoundException ex = new RechargeNotFoundException(99L);

        ResponseEntity<ErrorResponse> respuesta = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(404, respuesta.getBody().getStatus());
        assertTrue(respuesta.getBody().getMessage().contains("99"));
    }

    @Test
    void shouldReturnBadRequestWhenAmountIsInvalid() {
        InvalidRechargeAmountException ex =
                new InvalidRechargeAmountException("El monto debe ser mayor a cero.");

        ResponseEntity<ErrorResponse> respuesta = handler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(400, respuesta.getBody().getStatus());
        assertEquals("El monto debe ser mayor a cero.", respuesta.getBody().getMessage());
    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedExceptionOccurs() {
        Exception ex = new RuntimeException("boom");

        ResponseEntity<ErrorResponse> respuesta = handler.handleGeneric(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(500, respuesta.getBody().getStatus());
        assertEquals("Ocurrió un error inesperado en el servidor.", respuesta.getBody().getMessage());
    }
}