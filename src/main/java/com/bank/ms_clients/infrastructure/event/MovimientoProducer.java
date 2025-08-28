package com.bank.ms_clients.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.bank.ms_clients.application.dto.MovimientoDTO;

@Component
@RequiredArgsConstructor
public class MovimientoProducer {

    private final Logger log = LoggerFactory.getLogger(MovimientoProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "movimientos.exchange";
    private static final String ROUTING_KEY = "movimientos.submitted";

    public void enviarEventoMovimiento(MovimientoDTO movimientoDTO) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, movimientoDTO, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        log.info("📩 Movimiento encolado: {}", movimientoDTO);
    }

}