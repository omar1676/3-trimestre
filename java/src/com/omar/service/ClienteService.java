package com.omar.service;

import com.omar.entities.Cliente;
import com.omar.repository.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService() throws SQLException {
        this.repository = new ClienteRepository();
    }

    public boolean add(Cliente cliente) throws SQLException {
        if (cliente.getName() == null || cliente.getName().trim().isEmpty()) {
            return false;
        }
        if (cliente.getEmail() == null || !cliente.getEmail().contains("@")) {
            return false;
        }
        repository.create(cliente);
        return true;
    }

    public Cliente find(int id) throws SQLException {
        return repository.findById(id);
    }

    public List<Cliente> list() throws SQLException {
        return repository.findAll();
    }

    public boolean modify(Cliente cliente) throws SQLException {
        if (repository.findById(cliente.getId()) == null) {
            return false;
        }
        return repository.update(cliente);
    }

    public boolean remove(int id) throws SQLException {
        return repository.delete(id);
    }
}