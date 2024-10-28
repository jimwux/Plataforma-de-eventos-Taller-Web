package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCarrito {

    List<Carrito> obtenerEntradasDelCarrito(List<Long> idsEntradas, List<Integer> cantidades);

    Double calcularTotalCarrito(List<Carrito> carrito);

    String generarCodigoDescuento();

    void guardarCodigoDescuento(String codigo);

    Boolean esCodigoDescuentoValido(String codigo);

    Double calcularTotalCarritoConDescuento(Double totalCarrito);

}
