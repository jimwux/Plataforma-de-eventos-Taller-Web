package com.tallerwebi.dominio.excepcion;

public class CiudadExistente extends RuntimeException {
    public CiudadExistente(String message) {
        super(message);
    }
}
