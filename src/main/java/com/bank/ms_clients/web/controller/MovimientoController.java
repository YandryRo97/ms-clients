package com.bank.ms_clients.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.ms_clients.application.dto.MovimientoDTO;
import com.bank.ms_clients.application.service.MovimientoService;
import com.bank.ms_clients.infrastructure.event.MovimientoProducer;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoProducer movimientoProducer;
    private final MovimientoService movimientoService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        movimientoService.validateMovimiento(movimientoDTO);
        movimientoProducer.enviarEventoMovimiento(movimientoDTO);
        return ResponseEntity.ok("Movimiento enviado para procesamiento");
    }

}
