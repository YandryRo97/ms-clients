package com.bank.ms_clients.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.bank.ms_clients.application.dto.CuentaDTO;

@Component
@RequiredArgsConstructor
public class ClienteProducer {

    private final Logger log = LoggerFactory.getLogger(ClienteProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "clientes.exchange";
    private static final String ROUTING_KEY = "clientes.created";

    public void enviarEventoClienteCreado(CuentaDTO cuentaDTO) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, cuentaDTO);
        log.info("\uD83D\uDCE4 Evento enviado: Cliente creado, proceder a crear cuenta {}", cuentaDTO);
    }

}
