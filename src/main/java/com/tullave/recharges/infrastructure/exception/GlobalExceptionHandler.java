// infrastructure/api/exception/GlobalExceptionHandler.java
package com.tullave.recharges.infrastructure.exception;

import com.tullave.recharges.domain.exceptions.InvalidRechargeAmountException;
import com.tullave.recharges.domain.exceptions.RechargeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja recurso no encontrado (HTTP 404)
    @ExceptionHandler(RechargeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(RechargeNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Maneja errores de reglas de negocio / validaciones (HTTP 400)
    @ExceptionHandler(InvalidRechargeAmountException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(InvalidRechargeAmountException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Maneja cualquier otra excepción no controlada (HTTP 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Error",
                "Ocurrió un error inesperado en el servidor.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}