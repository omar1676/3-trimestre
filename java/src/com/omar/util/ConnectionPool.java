package com.omar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static ConnectionPool instancia;
    private final List<Connection> libres;
    private final int tamano;

    private ConnectionPool() throws SQLException {
        tamano = ConfigBD.getPoolSize();
        libres = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encuentra el driver de MySQL. Anade el jar al proyecto.", e);
        }

        for (int i = 0; i < tamano; i++) {
            libres.add(crearConexion());
        }
        System.out.println("Pool de conexiones listo (" + tamano + " conexiones)");
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ConnectionPool();
        }
        return instancia;
    }

    private Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(
                ConfigBD.getUrl(),
                ConfigBD.getUser(),
                ConfigBD.getPassword());
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection conexion;
        if (libres.isEmpty()) {
            conexion = crearConexion();
        } else {
            conexion = libres.remove(libres.size() - 1);
        }
        return conexion;
    }

    public synchronized void releaseConnection(Connection conexion) {
        if (conexion != null) {
            libres.add(conexion);
        }
    }

    public synchronized void closeAll() {
        for (Connection c : libres) {
            try {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
            } catch (SQLException e) {
                System.err.println("Error cerrando conexion: " + e.getMessage());
            }
        }
        libres.clear();
        instancia = null;
        System.out.println("Conexiones cerradas");
    }

    public int getPoolSize() {
        return tamano;
    }

    public int getFreeConnections() {
        return libres.size();
    }
}