package com.bank.ms_clients.application.dto;

import java.time.LocalDateTime;

public record ErrorDTO(
    LocalDateTime timestamp,
    String message,
    String details
    ) {}
