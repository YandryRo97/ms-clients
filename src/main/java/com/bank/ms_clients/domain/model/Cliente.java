package com.bank.ms_clients.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Cliente extends Persona {

    @Column(name = "cliente_id", unique = true, nullable = false, updatable = false)
    private String clienteId;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    /**
     * Genera un clienteId automáticamente si no se asigna.
     */
    @PrePersist
    public void generateClienteId() {
        if (this.clienteId == null || this.clienteId.isBlank()) {
            this.clienteId = UUID.randomUUID().toString(); // O usa tu formato "CLI-0001"
        }
    }
}
