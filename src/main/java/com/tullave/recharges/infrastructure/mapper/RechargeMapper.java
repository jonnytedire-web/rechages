package com.tullave.recharges.infrastructure.mapper;


import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.infrastructure.dto.RechargeDto;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "sprint")
public interface RechargeMapper {

    RechargeDto toDto(Recharge recharge);
    Recharge toEntity(RechargeDto rechargeDto);



}
