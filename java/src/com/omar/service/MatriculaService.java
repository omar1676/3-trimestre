package com.omar.service;

import com.omar.entities.Cliente;
import com.omar.entities.DetalleVenta;
import com.omar.entities.Producto;
import com.omar.entities.Usuario;
import com.omar.entities.Venta;
import com.omar.exceptions.EntidadNoEncontradaException;
import com.omar.repository.ClienteRepository;
import com.omar.repository.DetalleVentaRepository;
import com.omar.repository.ProductoRepository;
import com.omar.repository.UsuarioRepository;
import com.omar.repository.VentaRepository;
import com.omar.util.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MatriculaService {

    private final ConnectionPool pool;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public MatriculaService() throws SQLException {
        this.pool = ConnectionPool.getInstance();
        this.ventaRepository = new VentaRepository();
        this.detalleRepository = new DetalleVentaRepository();
        this.clienteRepository = new ClienteRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.productoRepository = new ProductoRepository();
    }

    public Venta enroll(int clienteId, int usuarioId, List<DetalleVenta> lineas)
            throws EntidadNoEncontradaException, SQLException {

        Cliente cliente = clienteRepository.findById(clienteId);
        if (cliente == null) {
            throw new EntidadNoEncontradaException("Alumno", clienteId);
        }
        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null) {
            throw new EntidadNoEncontradaException("Comercial/Usuario", usuarioId);
        }

        double total = 0;
        for (DetalleVenta linea : lineas) {
            Producto producto = productoRepository.findById(linea.getProductId());
            if (producto == null) {
                throw new EntidadNoEncontradaException("Curso/Producto", linea.getProductId());
            }
            linea.setUnitPrice(producto.getPrice());
            total += linea.getSubtotal();
        }

        Venta venta = new Venta();
        venta.setClientId(clienteId);
        venta.setUserId(usuarioId);
        venta.setDate(LocalDate.now());
        venta.setStatus("PENDIENTE");
        venta.setTotal(total);

        Connection conexion = pool.getConnection();
        try {
            conexion.setAutoCommit(false);

            ventaRepository.create(venta, conexion);
            for (DetalleVenta linea : lineas) {
                linea.setSaleId(venta.getId());
                detalleRepository.create(linea, conexion);
            }

            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(true);
            pool.releaseConnection(conexion);
        }

        return venta;
    }
}