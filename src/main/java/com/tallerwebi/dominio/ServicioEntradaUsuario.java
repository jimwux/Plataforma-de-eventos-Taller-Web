package com.tallerwebi.dominio;

public interface ServicioEntradaUsuario {

    void guardar (EntradaUsuario entradaUsuario);

    void guardarEntradasDeTipo(Integer cantidad, Usuario user, Entrada entradaActual, String codigoTransaccion);
}
