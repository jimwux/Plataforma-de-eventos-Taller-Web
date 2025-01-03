package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class EntradaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idEntrada; // ID de la entrada
    private Integer cantidad; // Cantidad de esa entrada en particular

    @ManyToOne
    @JoinColumn(name = "datos_compra_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DatosCompra datosCompra;

    // Constructor, getters y setters
    public EntradaCompra() {}

    public EntradaCompra(Long idEntrada, Integer cantidad) {
        this.idEntrada = idEntrada;
        this.cantidad = cantidad;
    }

    public Long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DatosCompra getDatosCompra() {
        return datosCompra;
    }

    public void setDatosCompra(DatosCompra datosCompra) {
        this.datosCompra = datosCompra;
    }
}
