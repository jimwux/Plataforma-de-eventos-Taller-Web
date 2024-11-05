package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DatosCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoTransaccion; // ID de la preferencia de pago de MercadoPago
    private String estado; // Estado de la compra ("pendiente", "completada", "rechazada")
    private String emailUsuario;
    private LocalDateTime fechaCompra;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "datos_compra_id") // Une la relación con DatosCompra
    private List<EntradaCompra> entradasCompradas;

    public DatosCompra(String codigoTransaccion, String emailUsuario) {
        this.codigoTransaccion = codigoTransaccion;
        this.emailUsuario = emailUsuario;
        this.estado = "pendiente";
        this.fechaCompra = LocalDateTime.now();
        this.entradasCompradas = new ArrayList<EntradaCompra>();
    }

    public DatosCompra() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(String preferenceId) {
        this.codigoTransaccion = preferenceId;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public List<EntradaCompra> getEntradasCompradas() {
        return entradasCompradas;
    }

    public void setEntradasCompradas(List<EntradaCompra> entradasCompradas) {
        this.entradasCompradas = entradasCompradas;
    }

    public EntradaCompra agregarEntrada(Long idEntrada, Integer cantidad) {
        EntradaCompra entradaCompra = new EntradaCompra(idEntrada, cantidad);
        this.entradasCompradas.add(entradaCompra);
        return entradaCompra;
    }

}
