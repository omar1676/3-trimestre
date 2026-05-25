package com.omar.controller;

import com.omar.entities.DetalleVenta;
import com.omar.entities.Venta;
import com.omar.exceptions.EntidadNoEncontradaException;
import com.omar.service.MatriculaService;
import com.omar.service.VentaService;

import java.sql.SQLException;
import java.util.List;

public class VentaController {

    private final VentaService ventaService;
    private final MatriculaService matriculaService;

    public VentaController() throws SQLException {
        this.ventaService = new VentaService();
        this.matriculaService = new MatriculaService();
    }

    public boolean crear(Venta venta) throws SQLException {
        return ventaService.add(venta);
    }

    public Venta buscar(int id) throws SQLException {
        return ventaService.find(id);
    }

    public List<Venta> listar() throws SQLException {
        return ventaService.list();
    }

    public boolean modificar(Venta venta) throws SQLException {
        return ventaService.modify(venta);
    }

    public boolean eliminar(int id) throws SQLException {
        return ventaService.remove(id);
    }

    public Venta matricular(int clienteId, int usuarioId, List<DetalleVenta> lineas)
            throws EntidadNoEncontradaException, SQLException {
        return matriculaService.enroll(clienteId, usuarioId, lineas);
    }

    public double facturacionTotal() throws SQLException {
        return ventaService.totalRevenue();
    }

    public List<Venta> listarPendientes() throws SQLException {
        return ventaService.listPendingPayment();
    }

    public List<Venta> listarPagadas() throws SQLException {
        return ventaService.listPaid();
    }

    public List<Venta> matriculasDe(int clienteId) throws SQLException {
        return ventaService.enrollmentsOf(clienteId);
    }

    public int contarGestionadasPor(int usuarioId) throws SQLException {
        return ventaService.countEnrollmentsManagedBy(usuarioId);
    }

    public boolean marcarComoPagada(int ventaId) throws SQLException {
        return ventaService.markAsPaid(ventaId);
    }
}
