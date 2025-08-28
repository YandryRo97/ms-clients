package com.bank.ms_clients.domain.repository;

import com.bank.ms_clients.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByClienteId(String clienteId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cliente c WHERE c.clienteId = :clienteId")
    void deleteByClienteId(String clienteId);

}
