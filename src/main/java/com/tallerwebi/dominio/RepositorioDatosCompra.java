package com.tallerwebi.dominio;

public interface RepositorioDatosCompra {

    void guardar(DatosCompra datosCompra);

    DatosCompra obtenerCompraPorCodigoTransaccion(String codigoTransaccion);

    void eliminarCompraPorCodigoTransaccion(String codigoTransaccion);

}
