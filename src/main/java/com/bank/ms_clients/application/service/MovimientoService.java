package com.bank.ms_clients.application.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.ms_clients.application.dto.MovimientoDTO;
import com.bank.ms_clients.application.exception.DataNotFound;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimientoService {
    private final WebClient webClient;

    public Mono<Void> validateMovimiento(MovimientoDTO dto) {
    return webClient.post()
        .uri("/movimientos/validar")
        .bodyValue(dto)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError,
            res -> Mono.error(new DataNotFound("Cuenta no encontrada o saldo insuficiente")))
        .onStatus(HttpStatusCode::is5xxServerError,
            res -> Mono.error(new RuntimeException("Error en servidor")))
        .toBodilessEntity()
        .then();
  }

    public Mono<MovimientoDTO> createMovimiento(MovimientoDTO dto) {
        return webClient.post()
            .uri("/movimientos")
            .bodyValue(dto)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError,
                res -> Mono.error(new DataNotFound("No se pudo crear el movimiento")))
            .onStatus(HttpStatusCode::is5xxServerError,
                res -> Mono.error(new RuntimeException("Error en servidor")))
            .bodyToMono(MovimientoDTO.class);
    }

}
