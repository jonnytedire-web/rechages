package com.tullave.recharges.infrastructure.api.controller;

import com.tullave.recharges.application.RechargeDelete;
import com.tullave.recharges.application.RechargeGet;
import com.tullave.recharges.application.RechargeSave;
import com.tullave.recharges.domain.entities.Recharge;
import com.tullave.recharges.infrastructure.dto.RechargeDto;
import com.tullave.recharges.infrastructure.mapper.RechargeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Recargas", description = "Endpoints para la gestión de recargas de la tarjeta tuLlave")
@SecurityRequirement(name = "bearerAuth")

public class RechargeController {
    private final RechargeSave rechargeSave;
    private final RechargeGet rechargeGet;
    private final RechargeDelete rechargeDelete;
    private final RechargeMapper rechargeMapper;



    @Operation(summary = "Obtener todas las recargas registradas")
    @GetMapping("/getRecharges")
    public ResponseEntity<List<RechargeDto>>getRechagesById(){
        List<RechargeDto> rechager = rechargeGet.getRechageAll()
                .stream()
                .map(rechargeMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rechager);
    }

    @Operation(summary = "Crear una nueva recarga")
    @PostMapping("/recharges")
    public  ResponseEntity<RechargeDto> createRechage(@RequestBody RechargeDto rechargeDto){
        var recharge = rechargeMapper.toEntity(rechargeDto);
        var saved = rechargeSave.SaveRechage(recharge);

        return ResponseEntity.status(HttpStatus.CREATED).body(rechargeMapper.toDto(saved));
    }


    @Operation(summary = "Eliminar una recarga por ID")
    @DeleteMapping("/recharges/{id}")
    public ResponseEntity<Void> deleteRecharge(@PathVariable Long id){
        rechargeDelete.deleteRecharge(id);
        return ResponseEntity.noContent().build();
    }

}
