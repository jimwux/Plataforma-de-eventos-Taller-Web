package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.Usuario;

import javax.persistence.Column;

public class UsuarioVistaDTO {

    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private String email;

    public UsuarioVistaDTO(Usuario usuario) {
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.telefono = usuario.getTelefono();
        this.dni = usuario.getDni();
        this.email = usuario.getEmail();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }
}
