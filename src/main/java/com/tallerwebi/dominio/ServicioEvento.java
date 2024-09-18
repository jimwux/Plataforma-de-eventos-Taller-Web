package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEvento {

    public List<Evento> obtenerTodosLosEventos();

    public Evento obtenerEventoPorId(Long id);
}
