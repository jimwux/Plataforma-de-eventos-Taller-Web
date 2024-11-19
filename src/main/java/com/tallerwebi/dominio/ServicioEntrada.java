package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEntrada {
    List<Entrada> obtenerTodasLasEntradas();

    Entrada obtenerEntradaPorId(Long id);

    List<Entrada> obtenerEntradasDeUnEvento(Long id);

    Boolean validarStockEntradas(List<Long> idsEntradas, List<Integer> cantidades);

    boolean reducirStock(Long idEntrada, Integer cantidad);
}
