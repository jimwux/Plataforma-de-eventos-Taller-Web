package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import com.tallerwebi.presentacion.dto.EmailContactoDTO;
import com.tallerwebi.presentacion.dto.FormularioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
                                         @RequestParam("eventoId") Long eventoId,
                                         RedirectAttributes redirectAttributes) {

        ModelMap modeloEntradas = new ModelMap();

        Boolean stockValido = servicioEntrada.validarStockEntradas(idsEntradas, cantidades);

        if (!stockValido) {
            // Si la validación de stock falla, agregar mensaje de error a RedirectAttributes
            redirectAttributes.addFlashAttribute("error", "No hay suficiente stock disponible o has superado el límite de 4 entradas por tipo.");
            // Redirigir de vuelta a la vista del evento original
            return new ModelAndView("redirect:/eventos/" + eventoId);
        }

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
            datosCompra.setEstado("completada");

            String emailUsuario = datosCompra.getEmailUsuario();
            Usuario user = this.servicioLogin.verificarSiExiste(emailUsuario);

            List<EntradaCompra> datosEntradas = datosCompra.getEntradasCompradas();

            for (EntradaCompra entradaCompra : datosEntradas) {
                Entrada entradaActual = this.servicioEntrada.obtenerEntradaPorId(entradaCompra.getIdEntrada());

                boolean stockReducido = this.servicioEntrada.reducirStock(entradaCompra.getIdEntrada(), entradaCompra.getCantidad());
                if (!stockReducido) {
                    modelo.put("error", "No hay suficiente stock para completar la compra.");
                    return new ModelAndView("compraRealizada", modelo);
                }

                this.servicioEntradaUsuario.guardarEntradasDeTipo(entradaCompra.getCantidad(), user,  entradaActual, codigoTransaccion);
            }
            // Enviar correo y generar código de descuento
            String codigoDescuento = servicioCarrito.generarCodigoDescuento();
            servicioCarrito.guardarCodigoDescuento(codigoDescuento);
            servicioEmail.enviarCodigoDescuento(emailUsuario, codigoDescuento);

            List<EntradaUsuario> entradasUsuarioDeLaCompra = this.servicioEntradaUsuario.obtenerEntradasDeUnaTransaccion(codigoTransaccion);
            servicioEmail.enviarEntradasConQR(emailUsuario,entradasUsuarioDeLaCompra);

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

    @GetMapping("/pago")
    public ModelAndView mostrarFormularioDTO() {
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("formulario", new FormularioDTO());
        return new ModelAndView("formularioPago", modelo);
    }

    @PostMapping("/procesarFormulario")
    public String procesarFormularioDTO(FormularioDTO formularioDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Si hay errores, redirigir al formulario para mostrar los mensajes de error
            model.addAttribute("formulario", formularioDTO);  // Para mantener los valores del formulario
            return "formularioPago";  // Retorna a la vista donde el formulario está
        }

        model.addAttribute("mensajeExito", "Formulario procesado correctamente.");
        return "redirect:checkout/create-payment"; // Redirige a una página de resultado o confirmación
    }

}
