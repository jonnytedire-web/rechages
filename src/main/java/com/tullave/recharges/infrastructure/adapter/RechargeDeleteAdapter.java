package com.tullave.recharges.infrastructure.adapter;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.services.RechargeDeleteService;
import com.tullave.recharges.repository.RechargeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class RechargeDeleteAdapter implements RechargeDeleteService {
    private final RechargeRepository rechargeRepository;


    @Override
    public boolean deleteRecharge(Long id) {
        if (!rechargeRepository.existsById(id)) return false;
        rechargeRepository.deleteById(id);
        return true;
    }



}
