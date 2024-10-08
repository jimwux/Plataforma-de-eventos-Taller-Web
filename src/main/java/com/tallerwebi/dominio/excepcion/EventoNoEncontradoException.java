package com.tallerwebi.dominio.excepcion;

public class EventoNoEncontradoException extends RuntimeException {
    private String mensaje;
    public EventoNoEncontradoException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}