package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/compraFinalizada")
      public ModelAndView mostrarVistaCompraFinalizada(){

        ModelMap modelo = new ModelMap();

        String codigoDescuento = servicioCarrito.generarCodigoDescuento();
        servicioCarrito.guardarCodigoDescuento(codigoDescuento);

        modelo.put("codigoDescuento", codigoDescuento);

        return new ModelAndView("compraRealizada", modelo);
    }

    @GetMapping("/aplicarDescuento")
    @ResponseBody
    public Map<String, Object> aplicarDescuento(@RequestParam("codigoDescuento") String codigoDescuento,
                                                @RequestParam("totalCarrito") Double totalCarrito) {
        Map<String, Object> resultado = new HashMap<>();

        if (servicioCarrito.esCodigoDescuentoValido(codigoDescuento, LocalDateTime.now())) {
            Double totalConDescuento = servicioCarrito.calcularTotalCarritoConDescuento(totalCarrito);
            resultado.put("descuentoAplicado", true);
            resultado.put("totalConDescuento", totalConDescuento);
        } else {
            resultado.put("descuentoAplicado", false);
        }
        return resultado;
    }

}
