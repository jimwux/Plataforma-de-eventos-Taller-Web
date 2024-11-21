package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView cerrarSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/usuario/modificar")
    public ModelAndView modificarUsuario(@RequestParam("campo") String campo,
                                         @RequestParam("nuevoValor") String nuevoValor,
                                         HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");

        if (email != null) {
            servicioUsuario.actualizarDatoUsuario(email, campo, nuevoValor);
        }

        return new ModelAndView( "redirect:/usuario");
    }

    @PostMapping("/usuario/cambiarContrasenia")
    public ModelAndView cambiarContrasenia(@RequestParam("contraseniaActual") String contraseniaActual,
                                           @RequestParam("nuevaContrasenia") String nuevaContrasenia,
                                           HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");

        if (email != null) {
            Boolean esValida = this.servicioUsuario.validarContraseniaActual(email, contraseniaActual);
            if (esValida) {
                servicioUsuario.cambiarContrasenia(email, nuevaContrasenia);
                return new ModelAndView("redirect:/usuario").addObject("mensaje", "Contraseña actualizada con éxito.");
            } else {
                return new ModelAndView("redirect:/usuario").addObject("error", "La contraseña actual es incorrecta.");
            }
        }

        return new ModelAndView("redirect:/login").addObject("error", "Debe iniciar sesión.");
    }

    @PostMapping("/eliminar-cuenta")
    public String eliminarCuenta(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long usuarioId = (session != null) ? (Long) session.getAttribute("ID") : null;

        if (usuarioId != null ) {
            servicioUsuario.eliminarCuentaUsuario(usuarioId);
            session.invalidate();
        }

        return "redirect:/login";
    }




}
