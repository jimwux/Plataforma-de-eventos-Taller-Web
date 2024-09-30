package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEntrada {

    List<Entrada> obtenerTodasLasEntradas();

    Entrada obtenerEntradaPorId(Long id);

    Entrada obtenerEntradaPorNombre(String nombre);
}
