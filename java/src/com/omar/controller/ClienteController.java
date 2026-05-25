package com.omar.controller;

import com.omar.entities.Cliente;
import com.omar.service.ClienteService;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() throws SQLException {
        this.clienteService = new ClienteService();
    }

    public boolean crear(Cliente cliente) throws SQLException {
        return clienteService.add(cliente);
    }

    public Cliente buscar(int id) throws SQLException {
        return clienteService.find(id);
    }

    public List<Cliente> listar() throws SQLException {
        return clienteService.list();
    }

    public boolean modificar(Cliente cliente) throws SQLException {
        return clienteService.modify(cliente);
    }

    public boolean eliminar(int id) throws SQLException {
        return clienteService.remove(id);
    }
}
