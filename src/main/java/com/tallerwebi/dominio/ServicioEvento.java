package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.EventoNombreDTO;

import java.time.LocalDate;
import java.util.List;

public interface ServicioEvento {

    List<Evento> obtenerTodosLosEventos();

    void agregarEvento(Evento nuevoEvento);

    Evento obtenerEventoPorId(Long id);

    List<Evento> obtenerEventosPorCategoria(String categoria);

    List<Evento> obtenerEventosOrdenadosPorFecha();

    List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin);

    List<Evento> filtrarEventos(String nombre, String nombreProvincia, String nombreCiudad, String categoria);

    List<Evento> obtenerEventosAleatorios(String nombreCiudad);

    List<EventoNombreDTO> obtenerNombresDeEventos();

    List<Evento> obtenerEventos(String nombreCiudad, boolean random);

    String obtenerMensajeSobreEventosAleatorios(List<Evento> eventosAleatorios,String nombreCiudad);

}
