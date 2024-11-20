package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario{

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public Usuario obtenerUsuarioVistaDTODelRepo(String email){
        return repositorioUsuario.buscar(email);
    }



}
