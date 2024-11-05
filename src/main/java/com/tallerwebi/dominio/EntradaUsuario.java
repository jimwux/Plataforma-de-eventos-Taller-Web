package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class EntradaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Entrada entrada;

    private String qrCode; // Código QR para esta entrada específica

    private String compraId; // Identificador único de la compra a la que pertenece

    public EntradaUsuario(Usuario usuario, Entrada entrada, String compraId) {
        this.usuario = usuario;
        this.entrada = entrada;
        this.compraId = compraId;
    }

    public EntradaUsuario() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getCompraId() {
        return compraId;
    }

    public void setCompraId(String compraId) {
        this.compraId = compraId;
    }
}
