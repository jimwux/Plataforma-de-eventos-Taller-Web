package com.tallerwebi.dominio;


import java.util.List;

public interface RepositorioEntradaUsuario {

    void guardar (EntradaUsuario entradaUsuario);

    List<EntradaUsuario> obtenerEntradaPorUsuario(String email);

    List<EntradaUsuario> obtenerEntradaPorUsuarioYCategoria(String email, String categoria);

    List<EntradaUsuario> obtenerEntradasDeUnaTransaccion(String codigoTransaccion);

}
