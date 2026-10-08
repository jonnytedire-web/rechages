package com.tullave.recharges.infrastructure.adapter;


import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.enums.PaymentMethod;
import com.tullave.recharges.infrastructure.adapter.RechargeGetAdapter;
import com.tullave.recharges.repository.RechargeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class RechargeGetAdapterTest {

    @Mock
    private RechargeRepository rechargeRepository;

    @InjectMocks
    private RechargeGetAdapter rechargeGetAdapter;

    @Test
    void getAllRechrage() {

        Pageable pageable = PageRequest.of(0, 10);

        Recharge recharge = new Recharge();
        recharge.setId(1L);
        recharge.setCardNumber("1234567890123456");
        recharge.setAmount(new BigDecimal("5000"));
        recharge.setPaymentMethod(PaymentMethod.NEQUI);

        Page<Recharge> paginaEsperada = new PageImpl<>(List.of(recharge));
        when(rechargeRepository.findAll(pageable)).thenReturn(paginaEsperada);
        Page<Recharge> resultado = rechargeGetAdapter.getAllRechrage(pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(1L, resultado.getContent().get(0).getId());
        verify(rechargeRepository, times(1)).findAll(pageable);
    }
    @Test
    void getAllRechrage_null() {
        Pageable pageable = PageRequest.of(0, 10);
        when(rechargeRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<Recharge> resultado = rechargeGetAdapter.getAllRechrage(pageable);

        assertTrue(resultado.isEmpty());
        verify(rechargeRepository, times(1)).findAll(pageable);
    }


}