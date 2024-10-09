package com.tallerwebi.dominio;

public class Carrito {

    private Entrada entrada;  // El id de la entrada (general, vip, etc.)
    private Integer cantidad;  // Cantidad de entradas seleccionadas
    private Double totalCarrito;  // Precio total por este tipo de entrada


    public Carrito(Entrada entrada, Integer cantidad, Double totalCarrito ) {
        this.cantidad = cantidad;
        this.totalCarrito = totalCarrito;
        this.entrada = entrada;
    }

    public Carrito() {

    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotalCarrito() {
        return totalCarrito;
    }

    public void setTotalCarrito(Double totalCarrito) {
        this.totalCarrito = totalCarrito;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }
}
