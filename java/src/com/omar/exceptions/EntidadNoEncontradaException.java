package com.omar.exceptions;

public class EntidadNoEncontradaException extends Exception {

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public EntidadNoEncontradaException(String tipoEntidad, int id) {
        super("No existe " + tipoEntidad + " con id " + id);
    }
}