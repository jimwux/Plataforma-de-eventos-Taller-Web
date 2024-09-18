package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEvento {

    public List<Evento> obtenerTodosLosEventos();

    List<Evento> buscarEventosPorNombre(String busqueda);

    public Evento obtenerEventoPorId(Long id);
}
