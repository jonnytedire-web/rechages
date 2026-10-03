package com.tullave.recharges.domain.exceptions;

public class RechargeNotFoundException extends RuntimeException {
    public RechargeNotFoundException(Long id) {
        super("La recarga con ID " + id + " no fue encontrada.");
    }
}