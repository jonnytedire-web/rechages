package com.tullave.recharges.infrastructure.api.controller;

import com.tullave.recharges.application.RechargeDelete;
import com.tullave.recharges.application.RechargeGet;
import com.tullave.recharges.application.RechargeSave;
import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.domain.enums.PaymentMethod;
import com.tullave.recharges.infrastructure.dto.RechargeDto;
import com.tullave.recharges.infrastructure.mapper.RechargeMapper;
import com.tullave.recharges.infrastructure.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RechargeController.class)
class RechargeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RechargeSave rechargeSave;
    @MockitoBean
    private RechargeGet rechargeGet;
    @MockitoBean
    private RechargeDelete rechargeDelete;
    @MockitoBean
    private RechargeMapper rechargeMapper;
    @MockitoBean
    private JwtProvider jwtProvider;

    @Test
    void shouldCreateRechargeAndReturnCreatedStatus() throws Exception {
        RechargeDto dtoSalida = new RechargeDto();
        dtoSalida.setId("1");
        dtoSalida.setCardNumber("1234567890123456");
        dtoSalida.setAmount(new BigDecimal("5000"));
        dtoSalida.setPaymentMethod(PaymentMethod.PSE);

        Recharge entity = new Recharge();
        entity.setCardNumber("1234567890123456");
        entity.setAmount(new BigDecimal("5000"));
        entity.setPaymentMethod(PaymentMethod.PSE);

        when(rechargeMapper.toEntity(any(RechargeDto.class))).thenReturn(entity);
        when(rechargeSave.SaveRechage(entity)).thenReturn(entity);
        when(rechargeMapper.toDto(entity)).thenReturn(dtoSalida);

        mockMvc.perform(post("/api/v1/recharges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardNumber\":\"1234567890123456\",\"amount\":5000,\"paymentMethod\":\"PSE\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.amount").value(5000))
                .andExpect(jsonPath("$.paymentMethod").value("PSE"));

        verify(rechargeSave, times(1)).SaveRechage(entity);
    }

    @Test
    void shouldDeleteRechargeAndReturnNoContentStatus() throws Exception {
        when(rechargeDelete.deleteRecharge(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/recharges/1"))
                .andExpect(status().isNoContent());

        verify(rechargeDelete, times(1)).deleteRecharge(1L);
    }

    @Test
    void shouldReturnPagedRechargesWhenRequested() throws Exception {
        Recharge entity = new Recharge();
        entity.setAmount(new BigDecimal("5000"));
        entity.setPaymentMethod(PaymentMethod.NEQUI);

        RechargeDto dto = new RechargeDto();
        dto.setId("1");
        dto.setAmount(new BigDecimal("5000"));
        dto.setPaymentMethod(PaymentMethod.NEQUI);

        Page<Recharge> pageEntity = new PageImpl<>(List.of(entity));
        when(rechargeGet.getRechageAll(any(Pageable.class))).thenReturn(pageEntity);
        when(rechargeMapper.toDto(entity)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/getRecharges")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].paymentMethod").value("NEQUI"));
    }
}
