package com.tullave.recharges.application;

import com.tullave.recharges.domain.services.RechargeDeleteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RechargeDelete {

    private final RechargeDeleteService rechargeDeleteService;

    public boolean  deleteRecharge(Long id){
        return rechargeDeleteService.deleteRecharge(id);
    }
}
