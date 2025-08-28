package com.bank.ms_clients.application.dto;

public record CuentaDTO(
        String clienteId,
        String numeroCuenta,
        String tipoCuenta,
        Double saldoInicial,
        Boolean estado
) {}
