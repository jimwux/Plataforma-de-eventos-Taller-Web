package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    void eliminarUsuario(Long usuarioId);
}

