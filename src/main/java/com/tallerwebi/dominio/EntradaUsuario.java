package com.tallerwebi.dominio;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
public class EntradaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    private Entrada entrada;

    @Column(columnDefinition = "TEXT")
    private String qrCode; // Código QR para esta entrada específica

    private String compraId; // Identificador único de la compra a la que pertenece

    private LocalDateTime fecha;

    public EntradaUsuario(Usuario usuario, Entrada entrada, String compraId) {
        this.usuario = usuario;
        this.entrada = entrada;
        this.compraId = compraId;
        this.fecha = LocalDateTime.now();
    }

    public EntradaUsuario() {

    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntradaUsuario that = (EntradaUsuario) o;
        return Objects.equals(id, that.id) && Objects.equals(usuario, that.usuario) && Objects.equals(entrada, that.entrada) && Objects.equals(qrCode, that.qrCode) && Objects.equals(compraId, that.compraId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, entrada, qrCode, compraId);
    }

}
