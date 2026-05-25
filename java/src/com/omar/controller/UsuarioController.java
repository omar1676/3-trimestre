package com.omar.controller;

import com.omar.entities.Usuario;
import com.omar.service.UsuarioService;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController() throws SQLException {
        this.usuarioService = new UsuarioService();
    }

    public boolean crear(Usuario usuario) throws SQLException {
        return usuarioService.add(usuario);
    }

    public Usuario buscar(int id) throws SQLException {
        return usuarioService.find(id);
    }

    public List<Usuario> listar() throws SQLException {
        return usuarioService.list();
    }

    public boolean modificar(Usuario usuario) throws SQLException {
        return usuarioService.modify(usuario);
    }

    public boolean eliminar(int id) throws SQLException {
        return usuarioService.remove(id);
    }

    public List<Usuario> listarComerciales() throws SQLException {
        return usuarioService.listSalespeople();
    }
}
