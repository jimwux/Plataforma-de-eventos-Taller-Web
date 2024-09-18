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

    public Evento obtenerEventoPorId(Long id);
}
