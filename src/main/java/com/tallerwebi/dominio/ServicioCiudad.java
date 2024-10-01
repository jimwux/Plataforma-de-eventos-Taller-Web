package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCiudad {
    List<Ciudad> obtenerTodasLasCiudades();

    Ciudad obtenerCiudadPorNombre(String nombreCiudad);

    void agregarCiudad(Ciudad ciudadCopia);

    List<Ciudad> obtenerCiudadesPorProvincia(String nombreProvincia);
}
