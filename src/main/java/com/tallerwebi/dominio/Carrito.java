package com.tallerwebi.dominio;

public class Carrito {

    private Long idEntrada;  // El id de la entrada (general, vip, etc.)
    private Integer cantidad;  // Cantidad de entradas seleccionadas
    private Double totalCarrito;  // Precio total por este tipo de entrada

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

    public Long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Long idEntrada) {
        this.idEntrada = idEntrada;
    }
}
