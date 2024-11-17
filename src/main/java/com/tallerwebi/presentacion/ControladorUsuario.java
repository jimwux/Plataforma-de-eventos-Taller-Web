package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/usuario")
    public ModelAndView mostrarVistaUsuario(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        String email = (String) request.getSession().getAttribute("email");
        if (email != null) {
            UsuarioVistaDTO usuarioVistaDTO = new UsuarioVistaDTO(servicioUsuario.obtenerUsuarioVistaDTODelRepo(email));
            modelo.put("usuarioVistaDTO", usuarioVistaDTO);
        }

        return new ModelAndView("usuario", modelo);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ModelAndView cerrarSesion(HttpServletRequest request){
            HttpSession session = request.getSession(false);
            if(session != null){
                session.invalidate();
        }
            return new ModelAndView("redirect:/login");
    }
}