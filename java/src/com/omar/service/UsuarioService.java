package com.omar.service;

import com.omar.entities.Usuario;
import com.omar.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    private final UsuarioRepository repository;

    private static final String[] ROLES_VALIDOS = {
            "ADMIN", "ORIENTADOR", "SECRETARIA", "COMERCIAL"
    };

    public UsuarioService() throws SQLException {
        this.repository = new UsuarioRepository();
    }

    public boolean add(Usuario usuario) throws SQLException {
        if (usuario.getName() == null || usuario.getName().trim().isEmpty()) {
            return false;
        }
        if (!isValidRole(usuario.getRole())) {
            return false;
        }
        repository.create(usuario);
        return true;
    }

    public Usuario find(int id) throws SQLException {
        return repository.findById(id);
    }

    public List<Usuario> list() throws SQLException {
        return repository.findAll();
    }

    public boolean modify(Usuario usuario) throws SQLException {
        if (repository.findById(usuario.getId()) == null) {
            return false;
        }
        if (!isValidRole(usuario.getRole())) {
            return false;
        }
        return repository.update(usuario);
    }

    public boolean remove(int id) throws SQLException {
        return repository.delete(id);
    }

    public boolean isValidRole(String rol) {
        if (rol == null) {
            return false;
        }
        for (String r : ROLES_VALIDOS) {
            if (r.equals(rol)) {
                return true;
            }
        }
        return false;
    }

    public List<Usuario> listSalespeople() throws SQLException {
        return repository.findByRole("COMERCIAL");
    }

    public List<Usuario> listByRole(String rol) throws SQLException {
        return repository.findByRole(rol);
    }
}