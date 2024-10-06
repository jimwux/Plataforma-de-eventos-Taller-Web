package com.tallerwebi.dominio.excepcion;

public class EventoNoEncontradoException extends RuntimeException {
    public EventoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}