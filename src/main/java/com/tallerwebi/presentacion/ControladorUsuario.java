package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/usuario")
    public ModelAndView mostrarVistaUsuario() {
        ModelMap modelo = new ModelMap();

            UsuarioVistaDTO usuarioVistaDTO = new UsuarioVistaDTO( 0L,"","");
            modelo.put("usuarioVistaDTO", usuarioVistaDTO);



        return new ModelAndView("usuarioVistaDTO", modelo);
    }
}