package com.omar.service;

import com.omar.entities.Venta;
import com.omar.repository.VentaRepository;

import java.sql.SQLException;
import java.util.List;

public class VentaService {

    private final VentaRepository repository;

    private static final String[] ESTADOS_VALIDOS = {
            "PENDIENTE", "CONFIRMADA", "PAGADA", "CANCELADA"
    };

    public VentaService() throws SQLException {
        this.repository = new VentaRepository();
    }

    public boolean add(Venta venta) throws SQLException {
        if (!isValidStatus(venta.getStatus())) {
            return false;
        }
        if (venta.getTotal() < 0) {
            return false;
        }
        repository.create(venta);
        return true;
    }

    public Venta find(int id) throws SQLException {
        return repository.findById(id);
    }

    public List<Venta> list() throws SQLException {
        return repository.findAll();
    }

    public boolean modify(Venta venta) throws SQLException {
        if (repository.findById(venta.getId()) == null) {
            return false;
        }
        if (!isValidStatus(venta.getStatus())) {
            return false;
        }
        return repository.update(venta);
    }

    public boolean remove(int id) throws SQLException {
        return repository.delete(id);
    }

    public boolean isValidStatus(String estado) {
        if (estado == null) {
            return false;
        }
        for (String e : ESTADOS_VALIDOS) {
            if (e.equals(estado)) {
                return true;
            }
        }
        return false;
    }

    public List<Venta> listPendingPayment() throws SQLException {
        return repository.findByStatus("PENDIENTE");
    }

    public List<Venta> listPaid() throws SQLException {
        return repository.findByStatus("PAGADA");
    }

    public List<Venta> listCancelled() throws SQLException {
        return repository.findByStatus("CANCELADA");
    }

    public List<Venta> enrollmentsOf(int clienteId) throws SQLException {
        return repository.findByClientId(clienteId);
    }

    public int countEnrollmentsManagedBy(int usuarioId) throws SQLException {
        return repository.countByUserId(usuarioId);
    }

    public double totalRevenue() throws SQLException {
        return repository.calculateTotalRevenue();
    }

    public boolean markAsPaid(int ventaId) throws SQLException {
        Venta v = repository.findById(ventaId);
        if (v == null) {
            return false;
        }
        v.markAsPaid();
        return repository.update(v);
    }

    public boolean cancelEnrollment(int ventaId) throws SQLException {
        Venta v = repository.findById(ventaId);
        if (v == null) {
            return false;
        }
        v.cancel();
        return repository.update(v);
    }
}