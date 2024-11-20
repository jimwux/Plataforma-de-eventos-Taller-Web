package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;

public interface ServicioUsuario {
    public Usuario obtenerUsuarioVistaDTODelRepo(String email);

    void actualizarDatoUsuario(String email, String campo, String nuevoValor);

    public Boolean validarContraseniaActual(String email, String contraseniaActual);

    public void cambiarContrasenia(String email, String nuevaContrasenia);
}
