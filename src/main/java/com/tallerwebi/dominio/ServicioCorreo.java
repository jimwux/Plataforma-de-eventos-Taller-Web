package com.tallerwebi.dominio;

public interface ServicioCorreo {

    void enviarCorreo(String destinatario, String asunto, String mensaje);

}
