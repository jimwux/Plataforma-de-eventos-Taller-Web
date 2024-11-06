package com.tallerwebi.dominio;


import java.util.List;

public interface RepositorioEntradaUsuario {

    void guardar (EntradaUsuario entradaUsuario);

    List<EntradaUsuario> obtenerEntradaPorUsuario(String email);

}
