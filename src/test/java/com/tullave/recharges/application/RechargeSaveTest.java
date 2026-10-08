package com.tullave.recharges.application;

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
class RechargeSaveTest {

}