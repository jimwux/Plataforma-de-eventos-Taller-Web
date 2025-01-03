package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioEntradaCompraImpl implements ServicioEntradaCompra {

    private RepositorioEntradaCompra repositorioEntradaCompra;

    @Autowired
    public ServicioEntradaCompraImpl(RepositorioEntradaCompra repositorioEntradaCompra) {
        this.repositorioEntradaCompra = repositorioEntradaCompra;
    }

    @Override
    public void guardar(EntradaCompra entradaCompra) {
        this.repositorioEntradaCompra.guardar(entradaCompra);
    }
}
