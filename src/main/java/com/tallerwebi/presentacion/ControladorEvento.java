package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.ServicioEvento;
import com.tallerwebi.dominio.ServicioEventoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorEvento {

private ServicioEvento servicioEvento;

@Autowired
public ControladorEvento(ServicioEvento servicioEvento) {
    this.servicioEvento = servicioEvento;
}

    /*@RequestMapping("/eventos")
    public ModelAndView mostrarEventos(){
        List<Evento> eventos = servicioEvento.obtenerTodosLosEventos();
        ModelMap modelo = new ModelMap();
        modelo.put("eventos", eventos);
        return new ModelAndView("eventos", modelo);
    }

    @GetMapping("/buscar")
    public List<Evento> buscarEventos(@RequestParam("nombre") String nombre) {
        List<Evento> eventos = this.servicioEvento.buscarEventosPorNombre(nombre);
        return eventos;  // Esto devuelve una lista de eventos en formato JSON
    }*/

    @RequestMapping(value = "/eventos", method = RequestMethod.GET)
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

}
