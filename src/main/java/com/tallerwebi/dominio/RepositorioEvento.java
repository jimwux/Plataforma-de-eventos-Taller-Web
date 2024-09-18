package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEvento {

    List<Evento> obtenerTodosLosEventos();

    void guardar(Evento evento);

    void actualizarLugar(Evento evento);

    void actualizarNombre(Evento evento);

    List<Evento> buscarEventosPorNombre(String busqueda);
}
