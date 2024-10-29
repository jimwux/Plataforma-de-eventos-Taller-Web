package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.stereotype.Service;

@Service
public class ServicioRegistroImpl implements ServicioRegistro {

    private RepositorioUsuario repositorioUsuario;

    public ServicioRegistroImpl(RepositorioUsuario repositorioUsuario) {
    this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public String registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscar(usuario.getEmail());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente("Email existente");
        }
        repositorioUsuario.guardar(usuario);
        return "Registro Exitoso";
    }
}
