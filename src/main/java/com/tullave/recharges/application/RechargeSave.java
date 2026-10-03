package com.tullave.recharges.application;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.services.RechargeSaveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RechargeSave {
    private final RechargeSaveService rechargeSaveService;

    public Recharge SaveRechage(Recharge recharge){
        return rechargeSaveService.saveRecharge(recharge);

    }


}
