package com.tallerwebi.dominio;

public interface ServicioEmail {

    void enviarCodigoDescuento(String email, String codigoDescuento);

    void enviarContraseniaAUsuarios(String email, String contrasenia);

    void enviarMensajeDeContacto(String nombre, String apellido, String email, String asunto, String mensajeUsuario);
}
