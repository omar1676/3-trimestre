package com.omar.repository;

import com.omar.entities.Producto;
import com.omar.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository implements IRepository<Producto, Integer> {

    private final ConnectionPool pool;

    public ProductoRepository() throws SQLException {
        this.pool = ConnectionPool.getInstance();
    }

    @Override
    public void create(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES (?, ?, ?, ?)";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, producto.getName());
            ps.setString(2, producto.getDescription());
            ps.setDouble(3, producto.getPrice());
            ps.setString(4, producto.getCategory());
            ps.executeUpdate();
            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    producto.setId(generadas.getInt(1));
                }
            }
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public Producto findById(Integer id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, categoria FROM productos WHERE id = ?";
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
    public List<Producto> findAll() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, precio, categoria FROM productos ORDER BY id";
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
    public boolean update(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, categoria = ? WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, producto.getName());
            ps.setString(2, producto.getDescription());
            ps.setDouble(3, producto.getPrice());
            ps.setString(4, producto.getCategory());
            ps.setInt(5, producto.getId());
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    public List<Producto> findByCategory(String categoria) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, precio, categoria FROM productos WHERE categoria = ? ORDER BY nombre";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, categoria);
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

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("nombre"));
        p.setDescription(rs.getString("descripcion"));
        p.setPrice(rs.getDouble("precio"));
        p.setCategory(rs.getString("categoria"));
        return p;
    }
}