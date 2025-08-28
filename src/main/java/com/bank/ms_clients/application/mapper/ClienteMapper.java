package com.bank.ms_clients.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bank.ms_clients.application.dto.ClienteDTO;
import com.bank.ms_clients.domain.model.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    ClienteDTO clientToClientDTO(Cliente client);
    Cliente clientDTOToClient(ClienteDTO clientDTO);
}
