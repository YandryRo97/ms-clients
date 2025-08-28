package com.bank.ms_clients.application.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MovimientoDTO {

    private String numeroCuenta;

    private String tipoCuenta;

    private String tipoMovimiento;

    private Double valor;

}