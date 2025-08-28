package com.bank.ms_clients.infrastructure.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovimientoRabbitConfig {

    @Bean
    public DirectExchange movimientosExchange() {
        return ExchangeBuilder.directExchange("movimientos.exchange").build();
    }

}
