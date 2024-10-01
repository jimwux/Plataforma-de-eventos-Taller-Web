package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorEvento {

private ServicioEvento servicioEvento;

@Autowired
public ControladorEvento(ServicioEvento servicioEvento) {
    this.servicioEvento = servicioEvento;
}

    @GetMapping("/eventos")
    public ModelAndView mostrarVistaEventos(@RequestParam(value = "nombre", required = false) String nombre,
                                            @RequestParam(value = "provinciaNombre", required = false) String nombreProvincia,
                                            @RequestParam(value = "ciudadNombre", required = false) String nombreCiudad) {
        ModelMap modelo = new ModelMap();
        List<Evento> eventos;

        Boolean sinFiltros = (nombre == null || nombre.isEmpty()) &&
                (nombreProvincia == null || nombreProvincia.isEmpty()) &&
                (nombreCiudad == null || nombreCiudad.isEmpty());

        if (sinFiltros) {
            eventos = this.servicioEvento.obtenerTodosLosEventos();
        } else {
            eventos = this.servicioEvento.filtrarEventos(nombre, nombreProvincia, nombreCiudad);
        }

        modelo.put("eventos", eventos);
        return new ModelAndView("eventos", modelo);
    }

    @GetMapping("/eventos/{id}")
    public ModelAndView mostrarVistas(@PathVariable Long id) {
        Evento eventoBuscado = servicioEvento.obtenerEventoPorId(id);
        ModelMap vistas = new ModelMap();
        vistas.put("vista", eventoBuscado);
        return new ModelAndView("vista", vistas);
    }

    public List<Evento> obtenerEventosOrdenadosPorFecha() {
        return this.servicioEvento.obtenerEventosOrdenadosPorFecha();
    }

    public List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return this.servicioEvento.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin);
    }
}
