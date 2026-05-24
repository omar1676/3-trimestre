package com.omar.entities;

import java.time.LocalDate;

public class Venta {

    private int id;
    private int clienteId;
    private int usuarioId;
    private LocalDate fecha;
    private String estado;
    private double total;

    public Venta() {
    }

    public Venta(int id, int clienteId, int usuarioId, LocalDate fecha, String estado, double total) {
        this.id = id;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clienteId;
    }

    public void setClientId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUserId() {
        return usuarioId;
    }

    public void setUserId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getDate() {
        return fecha;
    }

    public void setDate(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getStatus() {
        return estado;
    }

    public void setStatus(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPending() {
        return "PENDIENTE".equals(estado);
    }

    public boolean isConfirmed() {
        return "CONFIRMADA".equals(estado);
    }

    public boolean isPaid() {
        return "PAGADA".equals(estado);
    }

    public boolean isCancelled() {
        return "CANCELADA".equals(estado);
    }

    public boolean countsForRevenue() {
        return isPaid() || isConfirmed();
    }

    public int getEnrollmentYear() {
        return fecha != null ? fecha.getYear() : 0;
    }

    public String getAcademicYear() {
        if (fecha == null) {
            return "";
        }
        int ano = fecha.getYear();
        int mes = fecha.getMonthValue();
        if (mes >= 9) {
            return ano + "-" + (ano + 1);
        } else {
            return (ano - 1) + "-" + ano;
        }
    }

    public void confirm() {
        this.estado = "CONFIRMADA";
    }

    public void markAsPaid() {
        this.estado = "PAGADA";
    }

    public void cancel() {
        this.estado = "CANCELADA";
    }

    @Override
    public String toString() {
        return "[Venta] id=" + id + " | cliente=" + clienteId + " | usuario=" + usuarioId
                + " | " + fecha + " | " + estado + " | total=" + total + " EUR";
    }
}