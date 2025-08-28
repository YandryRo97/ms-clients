package com.bank.ms_clients.web.controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bank.ms_clients.application.dto.ClienteDTO;
import com.bank.ms_clients.application.service.ClienteService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = ClienteDTO.builder()
                .nombre("Mock Client")
                .genero("Masculino")
                .edad(30)
                .identificacion("123456789")
                .direccion("Calle 123")
                .telefono("0999999999")
                .clienteId("CLI123")
                .contrasena("pass123")
                .estado(true)
                .build();
        clienteService.deleteByClienteId(clienteDTO.getClienteId());
        clienteService.save(clienteDTO);
    }

    @AfterEach
    void clean() {
        clienteService.deleteByClienteId(clienteDTO.getClienteId());
    }

    @Test
    void testIntegrationCuentaDeleteCreateReadRepositoryController() {
        ResponseEntity<ClienteDTO> response = restTemplate
                .getForEntity("/clientes/{clienteId}", ClienteDTO.class, clienteDTO.getClienteId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(clienteDTO.getNombre(), response.getBody().getNombre());
    }

}
