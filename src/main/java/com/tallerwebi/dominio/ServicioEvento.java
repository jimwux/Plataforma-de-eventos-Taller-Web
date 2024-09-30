package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEvento {

    List<Evento> obtenerTodosLosEventos();

    void agregarEvento(Evento nuevoEvento);

    List<Evento> buscarEventosPorNombre(String busqueda);

    Evento obtenerEventoPorId(Long id);

    List<Evento> filtrarEventos(String nombre, String provinciaId, String ciudadId);
}
