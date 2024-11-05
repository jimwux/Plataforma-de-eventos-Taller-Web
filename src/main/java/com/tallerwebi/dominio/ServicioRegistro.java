package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioRegistro {

    String registrar(Usuario usuario) throws UsuarioExistente;

    String generarContrasena();



}
