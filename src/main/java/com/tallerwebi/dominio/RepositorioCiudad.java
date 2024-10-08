package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCiudad {

    void guardar(Ciudad ciudad);

    Ciudad obtenerCiudadPorNombre(String nombreCiudad);

    List<Ciudad> obtenerTodasLasCiudades();

    void eliminarCiudad(Ciudad moron);

    List<Ciudad> obtenerCiudadesPorProvincia(String nombreProvincia);
}
