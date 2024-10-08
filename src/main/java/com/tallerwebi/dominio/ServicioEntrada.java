package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEntrada {
    List<Entrada> obtenerTodasLasEntradas();

    Entrada obtenerEntradaPorId(Long id);

    List<Entrada> obtenerEntradasDeUnEvento(Long id);
}
