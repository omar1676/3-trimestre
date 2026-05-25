package com.omar.controller;

import com.omar.entities.DetalleVenta;
import com.omar.service.DetalleVentaService;

import java.sql.SQLException;
import java.util.List;

public class DetalleController {

    private final DetalleVentaService detalleService;

    public DetalleController() throws SQLException {
        this.detalleService = new DetalleVentaService();
    }

    public boolean crear(DetalleVenta detalle) throws SQLException {
        return detalleService.add(detalle);
    }

    public DetalleVenta buscar(int id) throws SQLException {
        return detalleService.find(id);
    }

    public List<DetalleVenta> listar() throws SQLException {
        return detalleService.list();
    }

    public List<DetalleVenta> listarPorVenta(int ventaId) throws SQLException {
        return detalleService.listBySale(ventaId);
    }

    public boolean modificar(DetalleVenta detalle) throws SQLException {
        return detalleService.modify(detalle);
    }

    public boolean eliminar(int id) throws SQLException {
        return detalleService.remove(id);
    }
}
