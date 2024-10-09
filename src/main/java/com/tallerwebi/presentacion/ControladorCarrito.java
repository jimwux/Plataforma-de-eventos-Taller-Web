package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.Entrada;
import com.tallerwebi.dominio.ServicioEntrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorCarrito {

    private ServicioEntrada servicioEntrada;

    @Autowired
    public ControladorCarrito(ServicioEntrada servicioEntrada) {
        this.servicioEntrada = servicioEntrada;
    }


    @PostMapping("/pago")
    public ModelAndView agregarAlCarrito(@RequestParam("idsEntradas") List<Long> idsEntradas,
                                 @RequestParam("cantidades") List<Integer> cantidades) {

        List<Carrito> entradasCarrito = new ArrayList<>();
        ModelMap modeloEntradas = new ModelMap();

            // Iterar sobre los IDs y las cantidades
            for (int i = 0; i < idsEntradas.size(); i++) {
                Long idEntrada = idsEntradas.get(i);
                int cantidad = cantidades.get(i);

                // Obtener la entrada por su id
                Entrada entradaEvento = this.servicioEntrada.obtenerEntradaPorId(idEntrada);
                Double precio = entradaEvento.getPrecio();

                // Calcular el total por esta entrada
                Double totalPrecio = precio * cantidad;

                // Crear un nuevo ItemCarrito
                if (entradaEvento != null && cantidad > 0) {
                    Carrito entradaCarrito = new Carrito(entradaEvento,cantidad, totalPrecio);
                    entradasCarrito.add(entradaCarrito);
                }


            }


            // Calcular el total del carrito
            Double totalCarrito = 0.0;

            // Añadir los items y el total al modelo
            modeloEntradas.put("entradasCarrito", entradasCarrito);
            modeloEntradas.put("totalCarrito", totalCarrito);

        return new ModelAndView("formularioPago", modeloEntradas);  // Retornar la vista del carrito
    }










}
