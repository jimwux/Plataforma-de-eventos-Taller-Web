package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEvento {

    List<Evento> obtenerTodosLosEventos();

    void agregarEvento(Evento nuevoEvento);

    Evento obtenerEventoPorId(Long id);

    List<Evento> obtenerEventosPorCategoria(String categoria);

    List<Evento> filtrarEventos(String nombre, String nombreProvincia, String nombreCiudad);

}
