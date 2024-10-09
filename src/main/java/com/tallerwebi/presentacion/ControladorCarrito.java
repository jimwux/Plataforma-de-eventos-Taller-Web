package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorCarrito {

    private ServicioCarrito servicioCarrito;

    @Autowired
    public ControladorCarrito(ServicioCarrito servicioCarrito) {
        this.servicioCarrito = servicioCarrito;
    }

    @PostMapping("/pago")
    public ModelAndView agregarAlCarrito(@RequestParam("idsEntradas") List<Long> idsEntradas,
                                         @RequestParam("cantidades") List<Integer> cantidades) {

        ModelMap modeloEntradas = new ModelMap();

        List<Carrito> entradasCarrito = this.servicioCarrito.obtenerEntradasDelCarrito(idsEntradas, cantidades);
        Double totalCarrito = this.servicioCarrito.calcularTotalCarrito(entradasCarrito);


        modeloEntradas.put("entradasCarrito", entradasCarrito);
        modeloEntradas.put("totalCarrito", totalCarrito);


        return new ModelAndView("formularioPago", modeloEntradas);  // Retornar la vista del carrito
    }













}
