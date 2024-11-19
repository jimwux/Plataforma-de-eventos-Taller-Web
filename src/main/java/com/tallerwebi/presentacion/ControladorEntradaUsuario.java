package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
public class ControladorEntradaUsuario {

    private ServicioEntradaUsuario servicioEntradaUsuario;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorEntradaUsuario(ServicioEntradaUsuario servicioEntradaUsuario, ServicioUsuario servicioUsuario) {
        this.servicioEntradaUsuario = servicioEntradaUsuario;
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/misEntradas")
    public ModelAndView mostrarEntradasDelUsuario(HttpServletRequest request,
                                                  @RequestParam(value = "categoria", required = false) String categoria)
                                                   {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap modeloEntradaUsuario = new ModelMap();
        String email = (String) session.getAttribute("email");

        UsuarioVistaDTO usuarioVistaDTO = new UsuarioVistaDTO(servicioUsuario.obtenerUsuarioVistaDTODelRepo(email));
        modeloEntradaUsuario.put("usuarioVistaDTO", usuarioVistaDTO);


        List<EntradaUsuario> entradasUs;

        if(categoria == null || categoria.isEmpty()) {
            entradasUs = this.servicioEntradaUsuario.obtenerEntradasDeUsuario(usuarioVistaDTO.getEmail());

            modeloEntradaUsuario.put("entradasUs", entradasUs);
        }
        else{
            entradasUs = this.servicioEntradaUsuario.obtenerEntradasDeUsuarioPorCategoria(usuarioVistaDTO.getEmail(), categoria);

            modeloEntradaUsuario.put("entradasUs", entradasUs);
        }

        String mensaje = "No hay entradas compradas";
        if(entradasUs.isEmpty()){
            modeloEntradaUsuario.put("error", mensaje);
        }

        return new ModelAndView("misEntradas", modeloEntradaUsuario);
    }
}
