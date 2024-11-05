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
    private ServicioEmail servicioEmail;
    private ServicioDatosCompra servicioDatosCompra;
    private ServicioLogin servicioLogin;
    private ServicioEntrada servicioEntrada;
    private ServicioEntradaUsuario servicioEntradaUsuario;

    @Autowired
    public ControladorCarrito(ServicioCarrito servicioCarrito, ServicioEvento servicioEvento, ServicioEmail servicioEmail, ServicioDatosCompra servicioDatosCompra, ServicioLogin servicioLogin, ServicioEntrada servicioEntrada, ServicioEntradaUsuario servicioEntradaUsuario) {
        this.servicioCarrito = servicioCarrito;
        this.servicioEvento = servicioEvento;
        this.servicioEmail = servicioEmail;
        this.servicioDatosCompra = servicioDatosCompra;
        this.servicioLogin = servicioLogin;
        this.servicioEntrada = servicioEntrada;
        this.servicioEntradaUsuario = servicioEntradaUsuario;
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
      public ModelAndView mostrarVistaCompraFinalizada(@RequestParam("codigoTransaccion") String codigoTransaccion,
                                                       @RequestParam("status") String status){

        ModelMap modelo = new ModelMap();

        if ("approved".equals(status)) {
            DatosCompra datosCompra = this.servicioDatosCompra.obtenerCompraPorCodigoTransaccion(codigoTransaccion);
            if (datosCompra == null) {
                modelo.put("error", "No se encontró la compra con el ID proporcionado.");
                return new ModelAndView("error", modelo);
            }


            datosCompra.setEstado("completada");

            String emailUsuario = datosCompra.getEmailUsuario();
            if (emailUsuario == null) {
                modelo.put("error", "No se encontró el correo electrónico del usuario en la compra.");
                return new ModelAndView("error", modelo);
            }

            Usuario user = this.servicioLogin.verificarSiExiste(emailUsuario);
            if (user == null) {
                modelo.put("error", "No se encontró el usuario en el sistema.");
                return new ModelAndView("error", modelo);
            }

            List<EntradaCompra> datosEntradas = datosCompra.getEntradasCompradas();

            if (datosEntradas == null) {
                modelo.put("error", "Faltan detalles de las entradas en la compra.");
                return new ModelAndView("error", modelo);
            }


            for (EntradaCompra entradaCompra : datosEntradas) {

                for (int j = 0; j < entradaCompra.getCantidad(); j++) {

                    Entrada entradaActual = this.servicioEntrada.obtenerEntradaPorId(entradaCompra.getIdEntrada());

                    EntradaUsuario entradaUsuario = new EntradaUsuario(user, entradaActual, codigoTransaccion);

                    if (entradaActual == null) {
                        System.err.println("Error: No se encontró Entrada con ID " + entradaCompra.getIdEntrada());
                        continue; // Pasa al siguiente registro si no se encuentra
                    }

                    try {
                        this.servicioEntradaUsuario.guardar(entradaUsuario);
                    } catch (Exception e) {
                        // Aquí podrías registrar el error
                        System.err.println("Error al guardar EntradaUsuario: " + e.getMessage());
                        e.printStackTrace(); // Imprimir el stack trace para más detalles
                    }
                }
            }



            // Enviar correo y generar código de descuento
            String codigoDescuento = servicioCarrito.generarCodigoDescuento();
            servicioCarrito.guardarCodigoDescuento(codigoDescuento);
            servicioEmail.enviarCodigoDescuento(datosCompra.getEmailUsuario(), codigoDescuento);

            modelo.put("codigoDescuento", codigoDescuento);

        } else {
            // Si el pago no fue aprobado, eliminar la compra pendiente
            servicioDatosCompra.eliminarCompraPorCodigoTransaccion(codigoTransaccion);
            modelo.put("error", "La compra no se completó con éxito y ha sido eliminada.");
        }

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
