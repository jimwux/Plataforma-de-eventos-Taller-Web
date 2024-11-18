package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEntrada {

    void guardarEntrada (Entrada entrada);

    List<Entrada> obtenerTodasLasEntradas();

    Entrada obtenerEntradaPorId(Long id);

    Entrada obtenerEntradaPorNombre(String nombre);

    List<Entrada> obtenerEntradasDeUnEvento(Long id);

    int reducirStock(Long idEntrada, Integer cantidad);
}
