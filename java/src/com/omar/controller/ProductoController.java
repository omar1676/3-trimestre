package com.omar.controller;

import com.omar.entities.Producto;
import com.omar.service.ProductoService;

import java.sql.SQLException;
import java.util.List;

public class ProductoController {

    private final ProductoService productoService;

    public ProductoController() throws SQLException {
        this.productoService = new ProductoService();
    }

    public boolean crear(Producto producto) throws SQLException {
        return productoService.add(producto);
    }

    public Producto buscar(int id) throws SQLException {
        return productoService.find(id);
    }

    public List<Producto> listar() throws SQLException {
        return productoService.list();
    }

    public boolean modificar(Producto producto) throws SQLException {
        return productoService.modify(producto);
    }

    public boolean eliminar(int id) throws SQLException {
        return productoService.remove(id);
    }

    public List<Producto> listarCiclos() throws SQLException {
        return productoService.listCycles();
    }
}
