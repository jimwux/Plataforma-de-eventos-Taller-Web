package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.EventoNombreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ServicioEventoImpl implements ServicioEvento {

    private RepositorioEvento repositorioEvento;
    private Random random;


    @Autowired
    public ServicioEventoImpl(RepositorioEvento repositorioEvento) {

        this.repositorioEvento = repositorioEvento;
        this.random = new Random();
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
    public List<Evento> obtenerEventosPorCategoria(String categoria) {
        return repositorioEvento.obtenerEventosPorCategoria(categoria);
    }

    @Override
    public List<Evento> filtrarEventos(String nombre, String nombreProvincia, String nombreCiudad, String categoria) {
        List<Evento> eventosFiltrados = new ArrayList<>();

            if (nombre != null && !nombre.isEmpty() &&
                    categoria != null && !categoria.isEmpty() &&
                    nombreProvincia != null && !nombreProvincia.isEmpty() &&
                    nombreCiudad != null && !nombreCiudad.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.buscarEventosPorNombreCategoriaProvinciaYCiudad(nombre,nombreProvincia, nombreCiudad, categoria);
//
            }  else if(nombreProvincia != null && !nombreProvincia.isEmpty() &&
                    nombreCiudad != null && !nombreCiudad.isEmpty() &&
                    categoria != null && !categoria.isEmpty()) {

                  eventosFiltrados = this.repositorioEvento.buscarEventosPorProvinciaCiudadYCategoria(nombreCiudad, nombreProvincia, categoria);
//
            }

            else if (nombre != null && !nombre.isEmpty() &&
                    categoria != null && !categoria.isEmpty() &&
                    nombreProvincia != null && !nombreProvincia.isEmpty()) {

                 eventosFiltrados = this.repositorioEvento.buscarEventosPorNombreCategoriaYProvincia(nombre, nombreProvincia, categoria);
//

            } else if (nombre != null && !nombre.isEmpty() &&
                    categoria != null && !categoria.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.buscarEventosPorNombreYCategoria(nombre, categoria);
//
            } else if (nombre != null && !nombre.isEmpty() &&
                    nombreProvincia != null && !nombreProvincia.isEmpty() &&
                    nombreCiudad != null && !nombreCiudad.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.buscarEventosPorCiudadYNombre(nombreCiudad, nombre);
//
            } else if (nombre != null && !nombre.isEmpty() &&
                    nombreProvincia != null && !nombreProvincia.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.buscarEventosPorProvinciaYNombre(nombreProvincia, nombre);

            } else if (nombreProvincia != null && !nombreProvincia.isEmpty() &&
                    nombreCiudad != null && !nombreCiudad.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.buscarEventosPorCiudad(nombreCiudad);

            } else if(nombreProvincia != null && !nombreProvincia.isEmpty() &&
                    categoria != null && !categoria.isEmpty()) {

                 eventosFiltrados = this.repositorioEvento.buscarEventosPorProvinciaYCategoria(nombreProvincia, categoria);

            } else if (nombreProvincia != null && !nombreProvincia.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.buscarEventosPorProvincia(nombreProvincia);

            } else if (nombre != null && !nombre.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.buscarEventosPorNombre(nombre);

            } else if (categoria != null && !categoria.isEmpty()) {

                eventosFiltrados = this.repositorioEvento.obtenerEventosPorCategoria(categoria);
    //
            }

            return eventosFiltrados;
    }

    @Override
    public List<Evento> obtenerEventosAleatorios(String nombreCiudad) {
        return obtenerEventos(nombreCiudad, this.random.nextBoolean());
    }


    public List<Evento> obtenerEventosOrdenadosPorFecha() {
        return this.repositorioEvento.obtenerEventosOrdenadosPorFecha();
    }

    public List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return this.repositorioEvento.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio, fechaFin);
    }

    public List<Evento> obtenerEventos(String nombreCiudad, boolean opcion) {
        if (opcion) {
            return this.repositorioEvento.buscarEventosPorCiudad(nombreCiudad);
        } else {
            LocalDate hoy = LocalDate.now();
            LocalDate dosMesesDespues = hoy.plusMonths(2);
            return this.repositorioEvento.obtenerEventosDentroDeUnRangoDeFechas(hoy, dosMesesDespues);
        }
    }

    @Override
    public List<EventoNombreDTO> obtenerNombresDeEventos() {
        List<EventoNombreDTO> eventosNombre = new ArrayList<>();

        for (Evento evento : repositorioEvento.obtenerTodosLosEventos() ) {
            eventosNombre.add(new EventoNombreDTO(evento.getNombre()));
        }

        return eventosNombre ;
    }

    @Override
    public String obtenerMensajeSobreEventosAleatorios( List<Evento> eventosAleatorios, String nombreCiudad) {

        Integer coincidencias = 0;
        boolean camino = true;

        //List<Evento> eventosObtenidos = obtenerEventos(nombreCiudad, camino);
        List<Evento> eventosObtenidos = this.repositorioEvento.buscarEventosPorCiudad(nombreCiudad);
        for (Evento evento : eventosAleatorios){
            for (Evento eventoObtenido : eventosObtenidos){
                if(evento.getId().equals(eventoObtenido.getId())){
                    coincidencias += 1;
                }
            }
        }
        if(coincidencias == eventosObtenidos.size() && !eventosObtenidos.isEmpty()){
            return "Mas eventos en la ciudad de " + nombreCiudad;
        } else {
            return "Mas eventos en los proximos meses";
        }
    }
}



