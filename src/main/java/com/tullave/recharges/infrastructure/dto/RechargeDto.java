package com.tullave.recharges.infrastructure.dto;

import com.tullave.recharges.domain.enums.PymentMethod;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;



@Data
public class RechargeDto {

    private String id;
    private String cardNumber;
    private BigDecimal amount;
    private PymentMethod pymentMethod;
    private LocalDate createAt;
}
