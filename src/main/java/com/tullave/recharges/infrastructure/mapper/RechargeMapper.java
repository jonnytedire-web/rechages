package com.tullave.recharges.infrastructure.mapper;


import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.infrastructure.dto.RechargeDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RechargeMapper {

    RechargeDto toDto(Recharge recharge);
    Recharge toEntity(RechargeDto rechargeDto);



}
