package com.omar.entities;

public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;

    public Producto() {
    }

    public Producto(int id, String nombre, String descripcion, double precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return descripcion;
    }

    public void setDescription(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrice() {
        return precio;
    }

    public void setPrice(double precio) {
        this.precio = precio;
    }

    public String getCategory() {
        return categoria;
    }

    public void setCategory(String categoria) {
        this.categoria = categoria;
    }

    public boolean isMediumCycle() {
        return "GRADO_MEDIO".equals(categoria);
    }

    public boolean isHigherCycle() {
        return "GRADO_SUPERIOR".equals(categoria);
    }

    public boolean isSpecialization() {
        return "ESPECIALIZACION".equals(categoria);
    }

    public boolean isShortCourse() {
        return "CURSO_CORTO".equals(categoria);
    }

    public boolean isLanguage() {
        return "IDIOMAS".equals(categoria);
    }

    public boolean isFormativeCycle() {
        return isMediumCycle() || isHigherCycle();
    }

    public double calculatePriceWithDiscount(double porcentaje) {
        if (porcentaje < 0) {
            porcentaje = 0;
        }
        if (porcentaje > 100) {
            porcentaje = 100;
        }
        return precio - (precio * porcentaje / 100.0);
    }

    public double calculateAlumniPrice() {
        return calculatePriceWithDiscount(15);
    }

    @Override
    public String toString() {
        return "[Producto] id=" + id + " | " + nombre + " | " + categoria
                + " | " + precio + " EUR";
    }
}