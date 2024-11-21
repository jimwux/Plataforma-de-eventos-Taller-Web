package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioDatosCompraImpl implements ServicioDatosCompra {

    private RepositorioDatosCompra repositorioDatosCompra;

    @Autowired
    public ServicioDatosCompraImpl(RepositorioDatosCompra repositorioDatosCompra) {
        this.repositorioDatosCompra = repositorioDatosCompra;
    }

    @Override
    public void guardar(DatosCompra datosCompraPendiente) {
        this.repositorioDatosCompra.guardar(datosCompraPendiente);
    }

    @Override
    public void eliminarCompraPorCodigoTransaccion(String codigoTransaccion) {
        this.repositorioDatosCompra.eliminarCompraPorCodigoTransaccion(codigoTransaccion);
    }

    @Override
    public DatosCompra obtenerCompraPorCodigoTransaccion(String codigoTransaccion) {
        return this.repositorioDatosCompra.obtenerCompraPorCodigoTransaccion(codigoTransaccion);
    }

}
