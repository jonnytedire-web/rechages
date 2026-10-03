package com.tullave.recharges.infrastructure.adapter;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.services.RechargeGetService;
import com.tullave.recharges.repository.RechargeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component

public class RechargeGetAdapter implements RechargeGetService {
  private final RechargeRepository rechargeRepository;


    @Override
    public Page<Recharge> getAllRechrage(Pageable pageable) {
        return rechargeRepository.findAll(pageable);
    }
}
