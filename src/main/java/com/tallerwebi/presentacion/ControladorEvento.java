package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView mostrarEventos(@RequestParam(value = "nombre", required = false) String nombre) {
        ModelMap modelo = new ModelMap();
        List<Evento> eventos;

        if (nombre == null || nombre.isEmpty()) {
            eventos = this.servicioEvento.obtenerTodosLosEventos();
        } else {
            eventos = this.servicioEvento.buscarEventosPorNombre(nombre);
        }
        modelo.put("eventos", eventos);
        return new ModelAndView("eventos", modelo);
    }

    @GetMapping("/eventos/{id}")
    public ModelAndView mostrarVistas(@PathVariable Long id) {
        Evento eventoBuscado = servicioEvento.obtenerEventoPorId(id);
        ModelMap vistas = new ModelMap();
        vistas.put("vista", eventoBuscado);
        List<Entrada> entradas = servicioEntrada.obtenerEntradasDeUnEvento(id);
        vistas.put("entradas", entradas);
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
