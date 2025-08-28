package com.bank.ms_clients.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.ms_clients.application.dto.ClienteCuentaDTO;
import com.bank.ms_clients.application.dto.ClienteDTO;
import com.bank.ms_clients.application.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.save(clienteDTO));
    }

    @PostMapping("/cuenta")
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteCuentaDTO clienteCuentaDTO) {
        return ResponseEntity.ok(clienteService.saveClienteWithCuenta(clienteCuentaDTO));
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> getClienteByClienteId(@PathVariable String clienteId) {
        return ResponseEntity.ok(clienteService.readByClienteId(clienteId));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.readAll());
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable String clienteId,
            @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.updateByClienteId(clienteId, clienteDTO));
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String clienteId) {
        clienteService.deleteByClienteId(clienteId);
        return ResponseEntity.noContent().build();
    }

}
