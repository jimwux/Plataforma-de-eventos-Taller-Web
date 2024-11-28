package com.tallerwebi.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ServicioCarrito {

    List<Carrito> obtenerEntradasDelCarrito(List<Long> idsEntradas, List<Integer> cantidades);

    Double calcularTotalCarrito(List<Carrito> carrito);

    String generarCodigoDescuento();

    void guardarCodigoDescuento(String codigo);

    Boolean esCodigoDescuentoValido(String codigo, LocalDateTime ahora);

    Double calcularTotalCarritoConDescuento(Double totalCarrito);


}
