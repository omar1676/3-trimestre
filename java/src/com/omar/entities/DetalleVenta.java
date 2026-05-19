package com.omar.entities;

public class DetalleVenta {

    private int id;
    private int ventaId;
    private int productoId;
    private int cantidad;
    private double precioUnitario;

    public DetalleVenta() {
    }

    public DetalleVenta(int id, int ventaId, int productoId, int cantidad, double precioUnitario) {
        this.id = id;
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaleId() {
        return ventaId;
    }

    public void setSaleId(int ventaId) {
        this.ventaId = ventaId;
    }

    public int getProductId() {
        return productoId;
    }

    public void setProductId(int productoId) {
        this.productoId = productoId;
    }

    public int getQuantity() {
        return cantidad;
    }

    public void setQuantity(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getUnitPrice() {
        return precioUnitario;
    }

    public void setUnitPrice(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return "[Detalle] id=" + id + " | venta=" + ventaId + " | producto=" + productoId
                + " | cant=" + cantidad + " | precio=" + precioUnitario
                + " | subtotal=" + getSubtotal();
    }
}