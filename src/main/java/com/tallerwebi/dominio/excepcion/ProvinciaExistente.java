package com.tallerwebi.dominio.excepcion;

public class ProvinciaExistente extends RuntimeException {
    public ProvinciaExistente(String message) {
        super(message);
    }
}
