package com.tullave.recharges.application;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.services.RechargeGetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class RechargeGet {
    private final RechargeGetService rechargeGetService;

   public List<Recharge> getRechageAll(){
        return rechargeGetService.getAllRechrage();
    }

}
