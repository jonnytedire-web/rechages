package com.tullave.recharges.application;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.enums.PaymentMethod;
import com.tullave.recharges.domain.exceptions.InvalidRechargeAmountException;
import com.tullave.recharges.infrastructure.adapter.RechargeSaveAdapter;
import com.tullave.recharges.repository.RechargeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
class RechargeSaveTest {

    @Mock
    private RechargeRepository rechargeRepository;

    @InjectMocks
    private RechargeSaveAdapter rechargeSaveAdapter;


    @Test
    void valid_amount() {

        Recharge rechargeToSave = new Recharge();
        rechargeToSave.setCardNumber("1234567534567341");
        rechargeToSave.setAmount(new BigDecimal("10000"));
        rechargeToSave.setPaymentMethod(PaymentMethod.PSE);

        Recharge savedRecharge = new Recharge();
        savedRecharge.setId(1L);
        savedRecharge.setCardNumber(rechargeToSave.getCardNumber());
        savedRecharge.setAmount(rechargeToSave.getAmount());
        savedRecharge.setPaymentMethod(rechargeToSave.getPaymentMethod());
        savedRecharge.setCreatedAt(LocalDate.now());

        when(rechargeRepository.save(any(Recharge.class))).thenReturn(savedRecharge);


        Recharge result = rechargeSaveAdapter.saveRecharge(rechargeToSave);


        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(new BigDecimal("10000"), result.getAmount());
        assertEquals(PaymentMethod.PSE, result.getPaymentMethod());
        assertEquals("1234567534567341", result.getCardNumber());
        verify(rechargeRepository).save(any(Recharge.class));
    }

 @Test
    void duffExcepcion(){

     Recharge recharge = new Recharge();
     recharge.setAmount(BigDecimal.ZERO);

     assertThrows(InvalidRechargeAmountException.class,
             () -> rechargeSaveAdapter.saveRecharge(recharge));

     verify(rechargeRepository, never()).save(any());

 }



 @Test
  void noNullExcepcion(){
     Recharge recharge = new Recharge();
     recharge.setAmount(BigDecimal.ZERO);

     assertThrows(InvalidRechargeAmountException.class,
             () -> rechargeSaveAdapter.saveRecharge(recharge));

     verify(rechargeRepository, never()).save(any());
 }


    @Test
    void NegativeExcepcion() {
        Recharge recharge = new Recharge();
        recharge.setAmount(new BigDecimal("-500"));

        assertThrows(InvalidRechargeAmountException.class,
                () -> rechargeSaveAdapter.saveRecharge(recharge));

        verify(rechargeRepository, never()).save(any());
    }





}