package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEvento {

    public List<Evento> obtenerTodosLosEventos();


    public void agregarEvento(Evento nuevoEvento);


    List<Evento> buscarEventosPorNombre(String busqueda);

    public Evento obtenerEventoPorId(Long id);

}
