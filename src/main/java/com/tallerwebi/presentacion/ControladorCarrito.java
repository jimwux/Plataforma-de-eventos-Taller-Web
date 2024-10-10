package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorCarrito {

    private ServicioCarrito servicioCarrito;
    private ServicioEvento servicioEvento;

    @Autowired
    public ControladorCarrito(ServicioCarrito servicioCarrito, ServicioEvento servicioEvento) {
        this.servicioCarrito = servicioCarrito;
        this.servicioEvento = servicioEvento;
    }

    @PostMapping("/pago")
    public ModelAndView agregarAlCarrito(@RequestParam("idsEntradas") List<Long> idsEntradas,
                                         @RequestParam("cantidades") List<Integer> cantidades,
                                         @RequestParam("eventoId") Long eventoId) {

        ModelMap modeloEntradas = new ModelMap();

        List<Carrito> entradasCarrito = this.servicioCarrito.obtenerEntradasDelCarrito(idsEntradas, cantidades);
        Double totalCarrito = this.servicioCarrito.calcularTotalCarrito(entradasCarrito);

        modeloEntradas.put("entradasCarrito", entradasCarrito);
        modeloEntradas.put("totalCarrito", totalCarrito);

        Evento evento = this.servicioEvento.obtenerEventoPorId(eventoId);
        modeloEntradas.put("evento", evento);

        return new ModelAndView("formularioPago", modeloEntradas);  // Retornar la vista del carrito
    }


    @PostMapping("/compraFinalizada")
    public ModelAndView mostrarVistaCompraFinalizada(@RequestParam("nombre") String nombre,
                                                     @RequestParam("apellido") String apellido){
        ModelMap modelo = new ModelMap();
        modelo.put("nombre", nombre);
        modelo.put("apellido", apellido);

        return new ModelAndView("compraRealizada", modelo);
    }










}
