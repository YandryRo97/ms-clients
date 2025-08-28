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

    public Optional<MovimientoDTO> validateMovimiento(MovimientoDTO movimientoDTO) {
        return Optional.ofNullable(webClient.post()
                .uri("/movimientos/validar")
                .body(Mono.just(movimientoDTO), MovimientoDTO.class)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        res -> Mono.error(new DataNotFound("Cuenta no encontrada o saldo insuficiente")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        res -> Mono.error(new RuntimeException("Error en servidor")))
                .bodyToMono(MovimientoDTO.class)
                .block());
    }

}
