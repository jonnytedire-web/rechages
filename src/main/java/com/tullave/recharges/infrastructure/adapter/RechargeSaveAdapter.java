package com.tullave.recharges.infrastructure.adapter;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.services.RechargeSaveService;
import com.tullave.recharges.repository.RechargeRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class RechargeSaveAdapter implements RechargeSaveService {
    private final RechargeRepository rechargeRepository;

    @Override
    public Recharge saveRecharge(Recharge recharge) {
        return rechargeRepository.save(recharge);
    }
}
