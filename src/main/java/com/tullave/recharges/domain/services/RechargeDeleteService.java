package com.tullave.recharges.domain.services;

import com.tullave.recharges.domain.entities.Recharge;

import java.util.Optional;

public interface RechargeDeleteService {
    boolean deleteRecharge(Long id);
}
