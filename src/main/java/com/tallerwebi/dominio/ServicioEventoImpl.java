package com.tallerwebi.dominio;

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
    public List<Evento> obtenerEventosPorCategoria(String categoria) {
        return repositorioEvento.obtenerEventosPorCategoria(categoria);
    }

    @Override
    public List<Evento> filtrarEventos(String nombre, String nombreProvincia, String nombreCiudad, String categoria) {

        List<Evento> eventosFiltrados = new ArrayList<>();

        //nombre si, provincia no, ciudad no, categoria no
        //nombre si, provincia si, ciudad si, categoria no
        //nombre si, provincia si, ciudad si, categoria si
        //nombre si, provincia no, ciudad no, categoria si
        //nombre no, provincia no, ciudad no, categoria si
        //nombre no, provincia si, ciudad si, categoria si
        //nombre no, provincia si, ciudad si, categoria no
        //nombre si, provincia si, ciudad no, categoria no
        //nombre si, provincia si, ciudad no, categoria si

        //NOMBRE SI -- PROVINCIA SI -- CIUDAD SI -- CATEGORIA NO
        if (nombre != null && !nombre.isEmpty() &&
                nombreProvincia != null && !nombreProvincia.isEmpty() &&
                nombreCiudad != null && !nombreCiudad.isEmpty()) {

            eventosFiltrados = this.repositorioEvento.buscarEventosPorCiudadYNombre(nombreCiudad, nombre);

        } else if (nombre != null && !nombre.isEmpty() &&
                nombreProvincia != null && !nombreProvincia.isEmpty()) {

            eventosFiltrados = this.repositorioEvento.buscarEventosPorProvinciaYNombre(nombreProvincia, nombre);

        } else if (nombreProvincia != null && !nombreProvincia.isEmpty() &&
                nombreCiudad != null && !nombreCiudad.isEmpty()) {

            eventosFiltrados = this.repositorioEvento.buscarEventosPorCiudad(nombreCiudad);

        } else if(nombre != null && !nombre.isEmpty() && categoria != null && !categoria.isEmpty()) {

            eventosFiltrados = this.repositorioEvento.buscarEventosPorNombreYCategoria(nombre, categoria);

        } else if (nombreProvincia != null && !nombreProvincia.isEmpty()) {

            eventosFiltrados = this.repositorioEvento.buscarEventosPorProvincia(nombreProvincia);

        } else if (nombre != null && !nombre.isEmpty()) {

            eventosFiltrados = this.repositorioEvento.buscarEventosPorNombre(nombre);

        } else if (categoria != null && !categoria.isEmpty()) {

            eventosFiltrados = this.repositorioEvento.obtenerEventosPorCategoria(categoria);

        } else if(nombreProvincia != null && !nombreProvincia.isEmpty() &&
                categoria != null && !categoria.isEmpty()) {

           // eventosFiltrados = this.repositorioEvento.buscarEventosPorProvinciaYCategoria(nombreProvincia, categoria);

        }else if(nombreProvincia != null && !nombreProvincia.isEmpty() &&
                nombreCiudad != null && !nombreCiudad.isEmpty() &&
                categoria != null && !categoria.isEmpty()) {

          //  eventosFiltrados = this.repositorioEvento.buscarEventosPorCiudadYCategoria(nombreCiudad, categoria);

        }else if (nombre != null && !nombre.isEmpty() &&
                categoria != null && !categoria.isEmpty() &&
                nombreProvincia != null && !nombreProvincia.isEmpty()) {

        //    eventosFiltrados = this.repositorioEvento.buscarEventosPorNombreCategoriaYProvincia(nombre, nombreProvincia, categoria);

        } else if(nombre != null && !nombre.isEmpty() &&
                categoria != null && !categoria.isEmpty() &&
                nombreProvincia != null && !nombreProvincia.isEmpty() &&
                nombreCiudad != null && !nombreCiudad.isEmpty()) {

           // eventosFiltrados = this.repositorioEvento.buscarEventosPorNombreCategoriaYCiudad(nombre, nombreCiudad, categoria);
        }

        return eventosFiltrados;
    }


    public List<Evento> obtenerEventosOrdenadosPorFecha() {
        return this.repositorioEvento.obtenerEventosOrdenadosPorFecha();
    }

    public List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return this.repositorioEvento.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio, fechaFin);
    }

    public List<Evento> obtenerEventosAleatorios(String nombreCiudad) {
        Random random = new Random();
        //mensajito SEGUNDO PARAMETRO PARA PODER TESTEAR (opcional)
        if (random.nextBoolean()) {
            return this.repositorioEvento.buscarEventosPorCiudad(nombreCiudad);
        } else {
            LocalDate hoy = LocalDate.now();
            LocalDate dosMesesDespues = hoy.plusMonths(2);
            return this.repositorioEvento.obtenerEventosDentroDeUnRangoDeFechas(hoy, dosMesesDespues);
        }
    }

}



