package com.omar.entities;

public class Cliente extends Persona {

    private String telefono;
    private String direccion;

    public Cliente() {
        super();
    }

    public Cliente(int id, String nombre, String email, String telefono, String direccion) {
        super(id, nombre, email);
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getPhone() {
        return telefono;
    }

    public void setPhone(String telefono) {
        this.telefono = telefono;
    }

    public String getAddress() {
        return direccion;
    }

    public void setAddress(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String roleDescription() {
        return "Alumno";
    }

    public String getEmailDomain() {
        if (email == null || !email.contains("@")) {
            return "";
        }
        return email.substring(email.indexOf('@') + 1);
    }

    public boolean hasFullContact() {
        return telefono != null && !telefono.isEmpty()
                && direccion != null && !direccion.isEmpty();
    }

    public boolean isFromBarcelona() {
        return direccion != null && direccion.toLowerCase().contains("barcelona");
    }

    @Override
    public String toString() {
        return "[Cliente] id=" + id + " | " + nombre + " | " + email
                + " | tel=" + telefono + " | " + direccion;
    }
}