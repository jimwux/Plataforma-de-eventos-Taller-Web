package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;

public interface ServicioUsuario {
    public Usuario obtenerUsuarioVistaDTODelRepo(String email);
}
