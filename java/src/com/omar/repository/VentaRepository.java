package com.omar.repository;

import com.omar.entities.Venta;
import com.omar.util.ConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentaRepository implements IRepository<Venta, Integer> {

    private final ConnectionPool pool;

    public VentaRepository() throws SQLException {
        this.pool = ConnectionPool.getInstance();
    }

    @Override
    public void create(Venta venta) throws SQLException {
        Connection conexion = pool.getConnection();
        try {
            create(venta, conexion);
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    public void create(Venta venta, Connection conexion) throws SQLException {
        String sql = "INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, venta.getClientId());
            ps.setInt(2, venta.getUserId());
            ps.setDate(3, Date.valueOf(venta.getDate()));
            ps.setString(4, venta.getStatus());
            ps.setDouble(5, venta.getTotal());
            ps.executeUpdate();
            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    venta.setId(generadas.getInt(1));
                }
            }
        }
    }

    @Override
    public Venta findById(Integer id) throws SQLException {
        String sql = "SELECT id, cliente_id, usuario_id, fecha, estado, total FROM ventas WHERE id = ?";
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
    public List<Venta> findAll() throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT id, cliente_id, usuario_id, fecha, estado, total FROM ventas ORDER BY id";
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
    public boolean update(Venta venta) throws SQLException {
        String sql = "UPDATE ventas SET cliente_id = ?, usuario_id = ?, fecha = ?, estado = ?, total = ? WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, venta.getClientId());
            ps.setInt(2, venta.getUserId());
            ps.setDate(3, Date.valueOf(venta.getDate()));
            ps.setString(4, venta.getStatus());
            ps.setDouble(5, venta.getTotal());
            ps.setInt(6, venta.getId());
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM ventas WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    public List<Venta> findByStatus(String estado) throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT id, cliente_id, usuario_id, fecha, estado, total FROM ventas WHERE estado = ? ORDER BY fecha DESC";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estado);
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

    public List<Venta> findByClientId(int clienteId) throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT id, cliente_id, usuario_id, fecha, estado, total FROM ventas WHERE cliente_id = ? ORDER BY fecha DESC";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
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

    public int countByUserId(int usuarioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ventas WHERE usuario_id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } finally {
            pool.releaseConnection(conexion);
        }
        return 0;
    }

    public double calculateTotalRevenue() throws SQLException {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM ventas WHERE estado IN ('PAGADA','CONFIRMADA')";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } finally {
            pool.releaseConnection(conexion);
        }
        return 0;
    }

    private Venta mapear(ResultSet rs) throws SQLException {
        Venta v = new Venta();
        v.setId(rs.getInt("id"));
        v.setClientId(rs.getInt("cliente_id"));
        v.setUserId(rs.getInt("usuario_id"));
        v.setDate(rs.getDate("fecha").toLocalDate());
        v.setStatus(rs.getString("estado"));
        v.setTotal(rs.getDouble("total"));
        return v;
    }
}