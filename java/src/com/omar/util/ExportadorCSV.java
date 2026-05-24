package com.omar.util;

import com.omar.entities.Cliente;
import com.omar.entities.Venta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV {

    private static final String SEPARADOR = ";";

    public static void exportarClientes(List<Cliente> clientes, String ruta) throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta));
        try {
            escritor.write("id" + SEPARADOR + "nombre" + SEPARADOR + "email"
                    + SEPARADOR + "telefono" + SEPARADOR + "direccion");
            escritor.newLine();
            for (Cliente c : clientes) {
                escritor.write(c.getId() + SEPARADOR
                        + sanitizar(c.getName()) + SEPARADOR
                        + sanitizar(c.getEmail()) + SEPARADOR
                        + sanitizar(c.getPhone()) + SEPARADOR
                        + sanitizar(c.getAddress()));
                escritor.newLine();
            }
        } finally {
            escritor.close();
        }
    }

    public static void exportarVentas(List<Venta> ventas, String ruta) throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta));
        try {
            escritor.write("id" + SEPARADOR + "cliente_id" + SEPARADOR + "usuario_id"
                    + SEPARADOR + "fecha" + SEPARADOR + "estado" + SEPARADOR + "total");
            escritor.newLine();
            for (Venta v : ventas) {
                escritor.write(v.getId() + SEPARADOR
                        + v.getClientId() + SEPARADOR
                        + v.getUserId() + SEPARADOR
                        + v.getDate() + SEPARADOR
                        + v.getStatus() + SEPARADOR
                        + v.getTotal());
                escritor.newLine();
            }
        } finally {
            escritor.close();
        }
    }

    private static String sanitizar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace(SEPARADOR, ",").replace("\n", " ").replace("\r", " ");
    }
}