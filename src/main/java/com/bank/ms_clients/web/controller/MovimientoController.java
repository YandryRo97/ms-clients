package com.bank.ms_clients.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
public Mono<ResponseEntity<String>> crearMovimiento(@RequestBody MovimientoDTO dto) {
  return movimientoService.validateMovimiento(dto)
      .then( // solo si validó OK
        Mono.fromRunnable(() -> movimientoProducer.enviarEventoMovimiento(dto))
            .subscribeOn(Schedulers.boundedElastic())
      )
      .thenReturn(ResponseEntity.status(HttpStatus.ACCEPTED)
          .body("Movimiento enviado para procesamiento"));
}

}
