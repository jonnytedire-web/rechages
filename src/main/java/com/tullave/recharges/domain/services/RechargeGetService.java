package com.tullave.recharges.domain.services;

import com.tullave.recharges.domain.entities.Recharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RechargeGetService {
    Page<Recharge> getAllRechrage(Pageable pageable);

}
