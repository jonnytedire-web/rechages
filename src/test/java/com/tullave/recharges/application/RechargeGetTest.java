package com.tullave.recharges.application;

import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.services.RechargeGetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechargeGetTest {

    @Mock
    private RechargeGetService rechargeGetService;

    @InjectMocks
    private RechargeGet rechargeGet;

    @Test
    void shouldReturnRechargesPageSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        Recharge recharge = new Recharge();
        Page<Recharge> expectedPage = new PageImpl<>(List.of(recharge));

        when(rechargeGetService.getAllRechrage(pageable)).thenReturn(expectedPage);

        Page<Recharge> result = rechargeGet.getRechageAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(rechargeGetService).getAllRechrage(pageable);
        verifyNoMoreInteractions(rechargeGetService);
    }
}