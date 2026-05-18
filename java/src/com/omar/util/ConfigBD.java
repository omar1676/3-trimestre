package com.omar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigBD {

    private static final String FICHERO_CONFIG = "config.properties";
    private static final Properties propiedades = new Properties();

    static {
        cargar();
    }

    private static void cargar() {
        Path ruta = Paths.get(FICHERO_CONFIG);
        if (Files.exists(ruta)) {
            try (InputStream entrada = new FileInputStream(FICHERO_CONFIG)) {
                propiedades.load(entrada);
                System.out.println("Configuracion cargada del fichero config.properties");
            } catch (IOException e) {
                System.err.println("No se pudo leer config.properties: " + e.getMessage());
            }
        } else {
            System.out.println("No encuentro config.properties, uso valores por defecto");
        }
    }

    public static String getUrl() {
        String host = propiedades.getProperty("db.host", "localhost");
        String puerto = propiedades.getProperty("db.puerto", "3307");
        String nombre = propiedades.getProperty("db.nombre", "crm_xtart");
        return "jdbc:mysql://" + host + ":" + puerto + "/" + nombre
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    }

    public static String getUser() {
        return propiedades.getProperty("db.usuario", "root");
    }

    public static String getPassword() {
        return propiedades.getProperty("db.password", "");
    }

    public static int getPoolSize() {
        try {
            return Integer.parseInt(propiedades.getProperty("db.pool.tamano", "5"));
        } catch (NumberFormatException e) {
            return 5;
        }
    }
}