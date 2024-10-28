package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipomokito")
public class ControladorCompra {

    @Autowired
    private ServicioCorreo servicioCorreo;


    @GetMapping("/compraFinalizada")
    public String confirmarCompra(@RequestParam("payment_id") String payment_id,
                                  @RequestParam("status") String status,
                                  @RequestParam("correo") String emailUsuario,
                                  @RequestParam("external_reference") String externalReference) {

        String asunto = "Compra exitosa: ";
        String mensaje = "Gracias por tu compra. Aquí están tus entradas para el evento " + ".";
        if (status.equals("approved")) {
            servicioCorreo.enviarCorreo("javimolina073@gmail.com",asunto , mensaje);
        }
        return "compraFinalizada";
    }

}
