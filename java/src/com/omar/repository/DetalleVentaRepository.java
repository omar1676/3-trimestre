package com.omar.repository;

import com.omar.entities.DetalleVenta;
import com.omar.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaRepository implements IRepository<DetalleVenta, Integer> {

    private final ConnectionPool pool;

    public DetalleVentaRepository() throws SQLException {
        this.pool = ConnectionPool.getInstance();
    }

    @Override
    public void create(DetalleVenta detalle) throws SQLException {
        Connection conexion = pool.getConnection();
        try {
            create(detalle, conexion);
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    public void create(DetalleVenta detalle, Connection conexion) throws SQLException {
        String sql = "INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, detalle.getSaleId());
            ps.setInt(2, detalle.getProductId());
            ps.setInt(3, detalle.getQuantity());
            ps.setDouble(4, detalle.getUnitPrice());
            ps.executeUpdate();
            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    detalle.setId(generadas.getInt(1));
                }
            }
        }
    }

    @Override
    public DetalleVenta findById(Integer id) throws SQLException {
        String sql = "SELECT id, venta_id, producto_id, cantidad, precio_unitario FROM detalle_venta WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } finally {
            pool.releaseConnection(conexion);
        }
        return null;
    }

    @Override
    public List<DetalleVenta> findAll() throws SQLException {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT id, venta_id, producto_id, cantidad, precio_unitario FROM detalle_venta ORDER BY id";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } finally {
            pool.releaseConnection(conexion);
        }
        return lista;
    }

    @Override
    public boolean update(DetalleVenta detalle) throws SQLException {
        String sql = "UPDATE detalle_venta SET venta_id = ?, producto_id = ?, cantidad = ?, precio_unitario = ? WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, detalle.getSaleId());
            ps.setInt(2, detalle.getProductId());
            ps.setInt(3, detalle.getQuantity());
            ps.setDouble(4, detalle.getUnitPrice());
            ps.setInt(5, detalle.getId());
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM detalle_venta WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    public List<DetalleVenta> findBySaleId(int ventaId) throws SQLException {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT id, venta_id, producto_id, cantidad, precio_unitario FROM detalle_venta WHERE venta_id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, ventaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } finally {
            pool.releaseConnection(conexion);
        }
        return lista;
    }

    private DetalleVenta mapear(ResultSet rs) throws SQLException {
        DetalleVenta d = new DetalleVenta();
        d.setId(rs.getInt("id"));
        d.setSaleId(rs.getInt("venta_id"));
        d.setProductId(rs.getInt("producto_id"));
        d.setQuantity(rs.getInt("cantidad"));
        d.setUnitPrice(rs.getDouble("precio_unitario"));
        return d;
    }
}