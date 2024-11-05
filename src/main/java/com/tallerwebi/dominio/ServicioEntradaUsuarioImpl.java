package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioEntradaUsuarioImpl implements ServicioEntradaUsuario {

    private RepositorioEntradaUsuario repositorioEntradaUsuario;

    @Autowired
    public ServicioEntradaUsuarioImpl(RepositorioEntradaUsuario repositorioEntradaUsuario) {
        this.repositorioEntradaUsuario = repositorioEntradaUsuario;
    }

    @Override
    public void guardar(EntradaUsuario entradaUsuario) {
        this.repositorioEntradaUsuario.guardar(entradaUsuario);
    }
}
