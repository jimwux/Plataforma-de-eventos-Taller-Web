package com.tallerwebi.dominio;

public interface ServicioDatosCompra {

    void guardar(DatosCompra datosCompraPendiente);

    void eliminarCompraPorCodigoTransaccion(String codigoTransaccion);

    DatosCompra obtenerCompraPorCodigoTransaccion(String codigoTransaccion);


}
