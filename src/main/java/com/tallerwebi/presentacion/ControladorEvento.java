package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.ServicioEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Controller
public class ControladorEvento {
private ServicioEvento servicioEvento;

@Autowired
public ControladorEvento(ServicioEvento servicioEvento) {
    this.servicioEvento = servicioEvento;
}

    @RequestMapping("/eventos")
    public ModelAndView mostrarEventos(){
        List<Evento> eventos = servicioEvento.obtenerTodosLosEventos();
    ModelMap modelo = new ModelMap();
    modelo.put("eventos", eventos);
    return new ModelAndView("eventos", modelo);
    }
}
