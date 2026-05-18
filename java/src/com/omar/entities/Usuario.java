package com.omar.entities;

public class Usuario extends Persona {

    private String rol;
    private String passwordHash;

    public Usuario() {
        super();
    }

    public Usuario(int id, String nombre, String email, String rol, String passwordHash) {
        super(id, nombre, email);
        this.rol = rol;
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return rol;
    }

    public void setRole(String rol) {
        this.rol = rol;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String roleDescription() {
        return "Personal Xtart (" + rol + ")";
    }

    public boolean isAdmin() {
        return "ADMIN".equals(rol);
    }

    public boolean isCounselor() {
        return "ORIENTADOR".equals(rol);
    }

    public boolean isSecretary() {
        return "SECRETARIA".equals(rol);
    }

    public boolean isSalesperson() {
        return "COMERCIAL".equals(rol);
    }

    public boolean canManageEnrollments() {
        return isSalesperson() || isAdmin();
    }

    @Override
    public String toString() {
        return "[Usuario] id=" + id + " | " + nombre + " | " + email + " | rol=" + rol;
    }
}