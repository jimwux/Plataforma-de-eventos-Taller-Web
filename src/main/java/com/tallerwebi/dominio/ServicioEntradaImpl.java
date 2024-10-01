package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEntradaImpl implements ServicioEntrada {

    private RepositorioEntrada repositorioEntrada;

    @Autowired
    public ServicioEntradaImpl(RepositorioEntrada repositorioEntrada) {
        this.repositorioEntrada = repositorioEntrada;
    }

    @Override
    public List<Entrada> obtenerTodasLasEntradas() {
        return repositorioEntrada.obtenerTodasLasEntradas();
    }

    @Override
    public Entrada obtenerEntradaPorId(Long id) {
        return repositorioEntrada.obtenerEntradaPorId(id);
    }

    @Override
    public List<Entrada> obtenerEntradasDeUnEvento(Long id) {
        return this.repositorioEntrada.obtenerEntradasDeUnEvento(id);
    }


}
