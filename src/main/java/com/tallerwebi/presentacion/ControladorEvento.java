package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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

        if(eventoBuscado != null) {
            List<Entrada> entradas = servicioEntrada.obtenerEntradasDeUnEvento(id);
            vistas.put("entradas", entradas);
        }

        return new ModelAndView("vista", vistas);
    }

    @GetMapping("/eventos/categoria")
    public ModelAndView mostrarEventosFiltradosPorCategoria(@RequestParam("categoria") String categoria) {
        List<Evento> eventosBuscados = servicioEvento.obtenerEventosPorCategoria(categoria);
        ModelMap modelo = new ModelMap();
        modelo.put("eventos", eventosBuscados);
        return new ModelAndView("eventos", modelo);
    }


}
