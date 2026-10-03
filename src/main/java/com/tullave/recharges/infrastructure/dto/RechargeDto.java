package com.tullave.recharges.infrastructure.dto;

import com.tullave.recharges.domain.enums.PaymentMethod;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;



@Data
public class RechargeDto {

    private String id;
    private String cardNumber;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private LocalDate createAt;
}
