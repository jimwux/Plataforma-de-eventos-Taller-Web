package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCarrito {

    List<Carrito> obtenerEntradasDelCarrito(List<Long> idsEntradas, List<Integer> cantidades);

    Double calcularTotalCarrito(List<Carrito> carrito);

}
