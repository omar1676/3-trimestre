package com.omar.repository;

import com.omar.entities.Cliente;
import com.omar.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository implements IRepository<Cliente, Integer> {

    private final ConnectionPool pool;

    public ClienteRepository() throws SQLException {
        this.pool = ConnectionPool.getInstance();
    }

    @Override
    public void create(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, email, telefono, direccion) "
                + "VALUES (?, ?, ?, ?)";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getName());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getPhone());
            ps.setString(4, cliente.getAddress());
            ps.executeUpdate();
            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    cliente.setId(generadas.getInt(1));
                }
            }
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public Cliente findById(Integer id) throws SQLException {
        String sql = "SELECT id, nombre, email, telefono, direccion FROM clientes WHERE id = ?";
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
    public List<Cliente> findAll() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email, telefono, direccion FROM clientes ORDER BY id";
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
    public boolean update(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, direccion = ? WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getName());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getPhone());
            ps.setString(4, cliente.getAddress());
            ps.setInt(5, cliente.getId());
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("nombre"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("telefono"));
        c.setAddress(rs.getString("direccion"));
        return c;
    }
}