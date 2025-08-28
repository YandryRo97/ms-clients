package com.bank.ms_clients.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void testClienteFields() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Mock Client");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("Calle 123");
        cliente.setTelefono("0999999999");
        cliente.setClienteId("CLI123");
        cliente.setContrasena("pass123");
        cliente.setEstado(true);
        assertEquals("Mock Client", cliente.getNombre());
        assertEquals("Masculino", cliente.getGenero());
        assertEquals(30, cliente.getEdad());
        assertEquals("123456789", cliente.getIdentificacion());
        assertEquals("Calle 123", cliente.getDireccion());
        assertEquals("0999999999", cliente.getTelefono());
        assertEquals("CLI123", cliente.getClienteId());
        assertEquals("pass123", cliente.getContrasena());
        assertTrue(cliente.getEstado());
    }

}
