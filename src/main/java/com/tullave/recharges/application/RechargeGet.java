package com.tullave.recharges.application;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.services.RechargeGetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class RechargeGet {
    private final RechargeGetService rechargeGetService;

   public Page<Recharge> getRechageAll(Pageable pageable){
        return rechargeGetService.getAllRechrage(pageable);
    }

}
