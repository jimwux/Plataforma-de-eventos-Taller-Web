package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioEventoImpl implements ServicioEvento {

    private RepositorioEvento repositorioEvento;

    @Autowired
    public ServicioEventoImpl(RepositorioEvento repositorioEvento) {
        this.repositorioEvento = repositorioEvento;
    }

    public List<Evento> obtenerTodosLosEventos() {
        return this.repositorioEvento.obtenerTodosLosEventos();
    }

    @Override
    public void agregarEvento(Evento nuevoEvento) {
        List<Evento> eventosExistentes = this.repositorioEvento.obtenerTodosLosEventos();
        if (!eventosExistentes.contains(nuevoEvento)) {
            this.repositorioEvento.guardar(nuevoEvento);
        }
    }

    @Override
    public Evento obtenerEventoPorId(Long id) {
        return this.repositorioEvento.obtenerEventoPorId(id);
    }

    @Override
    public List<Evento> filtrarEventos(String nombre, String nombreProvincia, String nombreCiudad) {

        List<Evento> eventosFiltrados = new ArrayList<>();

        if (nombre != null && nombreProvincia != null && nombreCiudad != null) {
            eventosFiltrados = this.repositorioEvento.buscarEventosPorCiudadYNombre(nombre, nombreCiudad);
        } else if (nombre != null && nombreProvincia != null) {
            eventosFiltrados = this.repositorioEvento.buscarEventosPorProvinciaYNombre(nombre, nombreProvincia);
        } else if (nombreProvincia != null && nombreCiudad != null) {
            eventosFiltrados = this.repositorioEvento.buscarEventosPorCiudad(nombreCiudad);
        } else if (nombreProvincia != null) {
            eventosFiltrados = this.repositorioEvento.buscarEventosPorProvincia(nombreProvincia);
        } else if (nombre != null) {
            eventosFiltrados = this.repositorioEvento.buscarEventosPorNombre(nombre);
        }

        return eventosFiltrados;
    }

}