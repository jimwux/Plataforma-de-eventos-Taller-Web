package com.tallerwebi.presentacion.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class FormularioDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre debe contener solo letras")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido debe contener solo letras")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @NotBlank(message = "El correo repetido es obligatorio")
    @Email(message = "El correo repetido debe tener un formato válido")
    private String emailRep;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^11\\d{8}$", message = "El teléfono debe tener 10 dígitos y comenzar con 11")
    private String telefono;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener 8 dígitos")
    private String dni;

    public @NotBlank(message = "El nombre es obligatorio") @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre debe contener solo letras") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio") @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre debe contener solo letras") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El apellido es obligatorio") @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido debe contener solo letras") String getApellido() {
        return apellido;
    }

    public void setApellido(@NotBlank(message = "El apellido es obligatorio") @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido debe contener solo letras") String apellido) {
        this.apellido = apellido;
    }

    public @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo debe tener un formato válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "El correo es obligatorio") @Email(message = "El correo debe tener un formato válido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "El correo repetido es obligatorio") @Email(message = "El correo repetido debe tener un formato válido") String getEmailRep() {
        return emailRep;
    }

    public void setEmailRep(@NotBlank(message = "El correo repetido es obligatorio") @Email(message = "El correo repetido debe tener un formato válido") String emailRep) {
        this.emailRep = emailRep;
    }

    public @NotBlank(message = "El teléfono es obligatorio") @Pattern(regexp = "^11\\d{8}$", message = "El teléfono debe tener 10 dígitos y comenzar con 11") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotBlank(message = "El teléfono es obligatorio") @Pattern(regexp = "^11\\d{8}$", message = "El teléfono debe tener 10 dígitos y comenzar con 11") String telefono) {
        this.telefono = telefono;
    }

    public @NotBlank(message = "El DNI es obligatorio") @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener 8 dígitos") String getDni() {
        return dni;
    }

    public void setDni(@NotBlank(message = "El DNI es obligatorio") @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener 8 dígitos") String dni) {
        this.dni = dni;
    }

    public boolean correosCoinciden() {
        return email.equals(emailRep);
    }
}