package com.omar.repository;

import com.omar.entities.Usuario;
import com.omar.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements IRepository<Usuario, Integer> {

    private final ConnectionPool pool;

    public UsuarioRepository() throws SQLException {
        this.pool = ConnectionPool.getInstance();
    }

    @Override
    public void create(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES (?, ?, ?, ?)";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getName());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getRole());
            ps.setString(4, usuario.getPasswordHash());
            ps.executeUpdate();
            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    usuario.setId(generadas.getInt(1));
                }
            }
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public Usuario findById(Integer id) throws SQLException {
        String sql = "SELECT id, nombre, email, rol, password_hash FROM usuarios WHERE id = ?";
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
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email, rol, password_hash FROM usuarios ORDER BY id";
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
    public boolean update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, rol = ?, password_hash = ? WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getName());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getRole());
            ps.setString(4, usuario.getPasswordHash());
            ps.setInt(5, usuario.getId());
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } finally {
            pool.releaseConnection(conexion);
        }
    }

    public List<Usuario> findByRole(String rol) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email, rol, password_hash FROM usuarios WHERE rol = ? ORDER BY nombre";
        Connection conexion = pool.getConnection();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, rol);
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

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setName(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setRole(rs.getString("rol"));
        u.setPasswordHash(rs.getString("password_hash"));
        return u;
    }
}