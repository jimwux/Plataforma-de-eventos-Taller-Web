package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;

public interface ServicioUsuario {
    Usuario obtenerUsuarioVistaDTODelRepo(String email);

    void actualizarDatoUsuario(String email, String campo, String nuevoValor);

    Boolean validarContraseniaActual(String email, String contraseniaActual);

    void cambiarContrasenia(String email, String nuevaContrasenia);

    void eliminarCuentaUsuario(Long usuarioId);
}
