package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.presentacion.dto.EmailContactoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorContacto {

    private ServicioEmail servicioEmail;

    @Autowired
    public ControladorContacto(ServicioEmail servicioEmail) {
        this.servicioEmail = servicioEmail;
    }

    @GetMapping("/contacto")
    public ModelAndView mostrarFormularioContacto() {
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("formularioContacto", new EmailContactoDTO());
        return new ModelAndView("contacto", modelo);
    }

    @PostMapping("/contacto")
    public String procesarFormularioContacto(EmailContactoDTO formularioContacto, Model model) {

        try {
            servicioEmail.enviarMensajeDeContacto(
                    formularioContacto.getNombre(),
                    formularioContacto.getApellido(),
                    formularioContacto.getEmail(),
                    formularioContacto.getAsunto(),
                    formularioContacto.getMensaje());
            model.addAttribute("formularioContacto", new EmailContactoDTO()); // Limpia el formulario
            model.addAttribute("mensajeExito", "¡Tu mensaje ha sido enviado con éxito!");
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Hubo un problema al enviar tu mensaje. Por favor, inténtalo de nuevo.");
        }
        return "contacto";
    }

}
