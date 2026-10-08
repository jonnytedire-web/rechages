package com.tullave.recharges.application;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.enums.PaymentMethod;
import com.tullave.recharges.domain.services.RechargeSaveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechargeSaveTest {

    @Mock
    private RechargeSaveService rechargeSaveService;

    @InjectMocks
    private RechargeSave rechargeSave;

    @Test
    void shouldSaveRechargeSuccessfully() {
        Recharge recharge = new Recharge();
        recharge.setAmount(new BigDecimal("5000"));
        recharge.setPaymentMethod(PaymentMethod.DAVIPLATA);

        when(rechargeSaveService.saveRecharge(recharge)).thenReturn(recharge);

        Recharge resultado = rechargeSave.SaveRechage(recharge);


        assertSame(recharge, resultado);
        verify(rechargeSaveService).saveRecharge(recharge);
        verifyNoMoreInteractions(rechargeSaveService);
    }
}