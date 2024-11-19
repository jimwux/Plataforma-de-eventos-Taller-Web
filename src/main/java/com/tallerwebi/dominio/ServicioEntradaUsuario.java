package com.tallerwebi.dominio;


import java.util.List;

public interface ServicioEntradaUsuario {

    void guardar (EntradaUsuario entradaUsuario);

    void guardarEntradasDeTipo(Integer cantidad, Usuario user, Entrada entradaActual, String codigoTransaccion);

    List<EntradaUsuario> obtenerEntradasDeUsuario(String email);

    List<EntradaUsuario> obtenerEntradasDeUsuarioPorCategoria(String email, String categoria);
}
