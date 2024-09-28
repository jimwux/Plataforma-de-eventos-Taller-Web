package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<Evento> buscarEventosPorNombre(String busqueda) {
        if (busqueda == null || busqueda.isEmpty()) {
            // Si no se especifica ningún nombre, retorna todos los eventos
            return this.repositorioEvento.obtenerTodosLosEventos();
        } else {
            // Si se especifica un nombre, busca los eventos que lo contienen
            return this.repositorioEvento.buscarEventosPorNombre(busqueda);
        }
    }

    @Override
    public Evento obtenerEventoPorId(Long id) {
        return this.repositorioEvento.obtenerEventoPorId(id);
    }

    @Override
    public List<Evento> obtenerEventosPorCategoria(String categoria) {
        return repositorioEvento.obtenerEventosPorCategoria(categoria);
    }


}
