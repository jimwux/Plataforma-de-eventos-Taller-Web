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

    List<Evento> obtenerEventosOrdenadosPorFecha();

    List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin);


}
