package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.Usuario;

import javax.persistence.Column;
import java.util.Objects;

public class UsuarioVistaDTO {

    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioVistaDTO that = (UsuarioVistaDTO) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

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
