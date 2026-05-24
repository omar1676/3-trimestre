package com.omar.service;

import com.omar.entities.DetalleVenta;
import com.omar.repository.DetalleVentaRepository;

import java.sql.SQLException;
import java.util.List;

public class DetalleVentaService {

    private final DetalleVentaRepository repository;

    public DetalleVentaService() throws SQLException {
        this.repository = new DetalleVentaRepository();
    }

    public boolean add(DetalleVenta detalle) throws SQLException {
        if (detalle.getQuantity() <= 0) {
            return false;
        }
        if (detalle.getUnitPrice() < 0) {
            return false;
        }
        repository.create(detalle);
        return true;
    }

    public DetalleVenta find(int id) throws SQLException {
        return repository.findById(id);
    }

    public List<DetalleVenta> list() throws SQLException {
        return repository.findAll();
    }

    public List<DetalleVenta> listBySale(int ventaId) throws SQLException {
        return repository.findBySaleId(ventaId);
    }

    public boolean modify(DetalleVenta detalle) throws SQLException {
        if (repository.findById(detalle.getId()) == null) {
            return false;
        }
        return repository.update(detalle);
    }

    public boolean remove(int id) throws SQLException {
        return repository.delete(id);
    }
}