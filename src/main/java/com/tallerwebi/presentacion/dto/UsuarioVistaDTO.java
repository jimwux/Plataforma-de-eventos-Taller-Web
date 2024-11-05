package com.tallerwebi.presentacion.dto;

public class UsuarioVistaDTO {

    private Long id;
    private String email;
    private String rol;

    public UsuarioVistaDTO(Long id, String email, String rol) {
        this.id = id;
        this.email = email;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getRol() {
        return rol;
    }
}
