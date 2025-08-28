package com.bank.ms_clients.application.service;

import com.bank.ms_clients.application.dto.ClienteCuentaDTO;
import com.bank.ms_clients.application.dto.ClienteDTO;
import com.bank.ms_clients.application.dto.CuentaDTO;
import com.bank.ms_clients.application.exception.DataNotFound;
import com.bank.ms_clients.application.mapper.ClienteMapper;
import com.bank.ms_clients.domain.model.Cliente;
import com.bank.ms_clients.domain.repository.ClienteRepository;
import com.bank.ms_clients.infrastructure.event.ClienteProducer;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService extends AbstractService<Cliente, Long, ClienteDTO, ClienteRepository> {

    private final ClienteProducer clienteProducer;

    public ClienteService(ClienteRepository repository, ClienteProducer clienteProducer) {
        super(repository, LoggerFactory.getLogger(ClienteService.class), "Cliente");
        this.clienteProducer = clienteProducer;
    }

    @Override
    public ClienteDTO mapToDTO(Cliente cliente) {
        return ClienteMapper.INSTANCE.clientToClientDTO(cliente);
    }

    @Override
    public Cliente mapToEntity(ClienteDTO clienteDTO) {
        return ClienteMapper.INSTANCE.clientDTOToClient(clienteDTO);
    }

    @Transactional
    public ClienteDTO saveClienteWithCuenta(ClienteCuentaDTO clienteCuentaDTO) {
        log.info("Save client with cuenta: {}", clienteCuentaDTO);
        ClienteDTO newCliente = save(
                clienteCuentaDTO.getCliente()
        );
        clienteProducer.enviarEventoClienteCreado(
                new CuentaDTO(
                        newCliente.getClienteId(),
                        clienteCuentaDTO.getNumeroCuenta(),
                        clienteCuentaDTO.getTipoCuenta(),
                        clienteCuentaDTO.getSaldoInicial(),
                        clienteCuentaDTO.getEstado()
                )
        );
        return newCliente;
    }

    public ClienteDTO readByClienteId(String clienteId) {
        return mapToDTO(
                repository.findByClienteId(clienteId)
                        .orElseThrow(() ->
                                new DataNotFound("Cliente con \"clienteId\": " + clienteId + ", no encontrado"))
        );
    }

    public void deleteByClienteId(String clienteId) {
        repository.deleteByClienteId(clienteId);
    }

    @Transactional
    public ClienteDTO updateByClienteId(String clienteId, ClienteDTO cliente) {
        ClienteDTO clienteToUpdate = readByClienteId(clienteId);
        clienteToUpdate.setNombre(cliente.getNombre().trim());
        clienteToUpdate.setGenero(cliente.getGenero().trim());
        clienteToUpdate.setEdad(cliente.getEdad());
        clienteToUpdate.setIdentificacion(cliente.getIdentificacion().trim());
        clienteToUpdate.setDireccion(cliente.getDireccion().trim());
        clienteToUpdate.setTelefono(cliente.getTelefono().trim());
        clienteToUpdate.setContrasena(cliente.getContrasena().trim());

        return update(
                clienteToUpdate.getId(),
                clienteToUpdate
        );
    }

}