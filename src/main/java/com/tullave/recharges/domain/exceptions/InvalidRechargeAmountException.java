package com.tullave.recharges.domain.exceptions;

public class InvalidRechargeAmountException extends RuntimeException {
    public InvalidRechargeAmountException(String message) {
        super(message);
    }
}