package com.tullave.recharges.infrastructure.adapter;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.exceptions.InvalidRechargeAmountException;
import com.tullave.recharges.domain.services.RechargeSaveService;
import com.tullave.recharges.repository.RechargeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class RechargeSaveAdapter implements RechargeSaveService {
    private final RechargeRepository rechargeRepository;

    @Override
    public Recharge saveRecharge(Recharge recharge) {
        if (recharge.getAmount() == null || recharge.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRechargeAmountException("El monto de la recarga debe ser mayor a cero.");
        }
        return rechargeRepository.save(recharge);
    }
}
