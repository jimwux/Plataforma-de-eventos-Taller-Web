package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioEvento {

    List<Evento> obtenerTodosLosEventos();

    void guardar(Evento evento);

    void actualizarLugar(Evento evento);

    void actualizarNombre(Evento evento);

    void eliminarEvento(Evento evento);

    List<Evento> obtenerLosEventosPorFecha(LocalDate of);

    List<Evento> buscarEventosPorNombre(String busqueda);

    Evento obtenerEventoPorId(Long id);

    List<Evento> obtenerEventosPorCategoria(String categoria);

    List<Evento> obtenerEventosOrdenadosPorFecha();

    List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin);

    List<Evento> buscarEventosPorCiudad(String nombreCiudad);

    List<Evento> buscarEventosPorProvincia(String nombreProvincia);

    List<Evento> buscarEventosPorCiudadYNombre(String nombreCiudad, String busqueda);

    List<Evento> buscarEventosPorProvinciaYNombre(String nombreProvincia, String busqueda);

    List<Evento> buscarEventosPorNombreYCategoria(String nombre, String categoria);
}
