package com.tullave.recharges.application;

import com.tullave.recharges.domain.services.RechargeDeleteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechargeDeleteTest {

    @Mock
    private RechargeDeleteService rechargeDeleteService;

    @InjectMocks
    private RechargeDelete rechargeDelete;

    @Test
    void shouldDeleteRechargeSuccessfully() {
        Long id = 1L;
        when(rechargeDeleteService.deleteRecharge(id)).thenReturn(true);
        boolean result = rechargeDelete.deleteRecharge(id);
        assertTrue(result);
        verify(rechargeDeleteService).deleteRecharge(id);
        verifyNoMoreInteractions(rechargeDeleteService);
    }
}