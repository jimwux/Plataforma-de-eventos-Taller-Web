package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEmail {

    void enviarEntradasConQR(String emailUsuario, List<EntradaUsuario> entradas);

    void enviarCodigoDescuento(String email, String codigoDescuento);

    void enviarContraseniaAUsuarios(String email, String contrasenia);

    void enviarMensajeDeContacto(String nombre, String apellido, String email, String asunto, String mensajeUsuario);
}
