package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EventoNoEncontradoException;
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
private ServicioEntrada servicioEntrada;

@Autowired
public ControladorEvento(ServicioEvento servicioEvento, ServicioEntrada servicioEntrada) {
    this.servicioEvento = servicioEvento;
    this.servicioEntrada = servicioEntrada;
}

    @GetMapping("/eventos")
    public ModelAndView mostrarVistaEventos(@RequestParam(value = "nombre", required = false) String nombre,
                                            @RequestParam(value = "provinciaNombre", required = false) String nombreProvincia,
                                            @RequestParam(value = "ciudadNombre", required = false) String nombreCiudad,
                                            @RequestParam(value = "categoria", required = false) String categoria) {
        ModelMap modelo = new ModelMap();
        String mensajeException = "";
        try{

            List<Evento> eventos;


        Boolean sinFiltros = (nombre == null || nombre.isEmpty()) &&
                (nombreProvincia == null || nombreProvincia.isEmpty()) &&
                (nombreCiudad == null || nombreCiudad.isEmpty()) &&
                (categoria == null || categoria.isEmpty());

        if (sinFiltros) {
            eventos = this.servicioEvento.obtenerEventosOrdenadosPorFecha();
        } else {
            eventos = this.servicioEvento.filtrarEventos(nombre, nombreProvincia, nombreCiudad, categoria);
        }


            modelo.put("eventos", eventos);
            return new ModelAndView("eventos", modelo);
        } catch (EventoNoEncontradoException e) {
            mensajeException = e.getMensaje();
        }
        modelo.put("mensaje", mensajeException);
        return new ModelAndView("eventos", modelo);
    }

    @GetMapping("/eventos/{id}")
    public ModelAndView mostrarVistas(@PathVariable Long id) {
        Evento eventoBuscado = servicioEvento.obtenerEventoPorId(id);
        ModelMap vistas = new ModelMap();
        vistas.put("vista", eventoBuscado);

        if(eventoBuscado != null) {
            List<Entrada> entradas = servicioEntrada.obtenerEntradasDeUnEvento(id);
            vistas.put("entradas", entradas);

            List<Evento> eventosCarrousel = servicioEvento.obtenerEventosAleatorios(eventoBuscado.getCiudad().getNombre());
            vistas.put("eventosCarrousel", eventosCarrousel);
            String mensajeCarrusel = servicioEvento.obtenerMensajeSobreEventosAleatorios(eventosCarrousel, eventoBuscado.getCiudad().getNombre());
            vistas.put("mensajeCarrusel", mensajeCarrusel);
        }

        return new ModelAndView("vista", vistas);
    }
    
    public List<Evento> obtenerEventosOrdenadosPorFecha() {
        return this.servicioEvento.obtenerEventosOrdenadosPorFecha();
    }

    public List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return this.servicioEvento.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin);
    }





}
