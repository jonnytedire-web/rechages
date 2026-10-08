package com.tullave.recharges.infrastructure.adapter;

import com.tullave.recharges.domain.exceptions.RechargeNotFoundException;
import com.tullave.recharges.infrastructure.adapter.RechargeDeleteAdapter;
import com.tullave.recharges.repository.RechargeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RechargeDeleteAdapterTest {

    @Mock
    private RechargeRepository rechargeRepository;

    @InjectMocks
    private RechargeDeleteAdapter rechargeDeleteAdapter;



    @Test
    void deleteRecharge() {
        Long id = 1L;
        when(rechargeRepository.existsById(id)).thenReturn(true);
        boolean resultado = rechargeDeleteAdapter.deleteRecharge(id);
        assertTrue(resultado);
        verify(rechargeRepository, times(1)).deleteById(id);
    }

    @Test
    void RechargeNotFoundException() {
        Long id = 99L;
        when(rechargeRepository.existsById(id)).thenReturn(false);
        assertThrows(RechargeNotFoundException.class, () -> rechargeDeleteAdapter.deleteRecharge(id));
        verify(rechargeRepository, never()).deleteById(anyLong());
    }
}