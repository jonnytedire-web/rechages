package com.tullave.recharges.infrastructure.adapter;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.enums.PaymentMethod;
import com.tullave.recharges.domain.exceptions.InvalidRechargeAmountException;
import com.tullave.recharges.infrastructure.adapter.RechargeSaveAdapter;
import com.tullave.recharges.repository.RechargeRepository;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(MockitoExtension.class)
class RechargeSaveAdapterTest {

    @Mock
    private RechargeRepository rechargeRepository;

    @InjectMocks
    private RechargeSaveAdapter rechargeSaveAdapter;

    private Recharge recharge;

    @BeforeEach
    void setUp() {
        recharge = new Recharge();
        recharge.setCardNumber("1234567534567341");
        recharge.setPaymentMethod(PaymentMethod.PSE);
    }

    @Test
    void valid_amount() {
        recharge.setAmount(new BigDecimal("10000"));

        Recharge savedRecharge = new Recharge();
        savedRecharge.setId(1L);
        savedRecharge.setCardNumber(recharge.getCardNumber());
        savedRecharge.setAmount(recharge.getAmount());
        savedRecharge.setPaymentMethod(recharge.getPaymentMethod());
        savedRecharge.setCreatedAt(LocalDate.now());

        when(rechargeRepository.save(any(Recharge.class))).thenReturn(savedRecharge);

        Recharge result = rechargeSaveAdapter.saveRecharge(recharge);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(new BigDecimal("10000"), result.getAmount());
        assertEquals(PaymentMethod.PSE, result.getPaymentMethod());
        assertEquals("1234567534567341", result.getCardNumber());
        verify(rechargeRepository).save(any(Recharge.class));
    }

    @Test
    void zeroAmount_shouldThrowException() {
        recharge.setAmount(BigDecimal.ZERO);

        assertThrows(InvalidRechargeAmountException.class,
                () -> rechargeSaveAdapter.saveRecharge(recharge));

        verify(rechargeRepository, never()).save(any());
    }

    @Test
    void nullAmount_shouldThrowException() {
        recharge.setAmount(null);

        assertThrows(InvalidRechargeAmountException.class,
                () -> rechargeSaveAdapter.saveRecharge(recharge));

        verify(rechargeRepository, never()).save(any());
    }

    @Test
    void negativeAmount_shouldThrowException() {
        recharge.setAmount(new BigDecimal("-500"));

        assertThrows(InvalidRechargeAmountException.class,
                () -> rechargeSaveAdapter.saveRecharge(recharge));

        verify(rechargeRepository, never()).save(any());
    }
}