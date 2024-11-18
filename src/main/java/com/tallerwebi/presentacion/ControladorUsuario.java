package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
}
//        if (usuario != null) {
//            switch (campo) {
//                case "nombre":
//                    usuario.setNombre(nuevoValor);
//                    break;
//                case "apellido":
//                    usuario.setApellido(nuevoValor);
//                    break;
//                case "email":
//                    usuario.setEmail(nuevoValor);
//                    break;
//                case "telefono":
//                    usuario.setTelefono(nuevoValor);
//                    break;
//                case "dni":
//                    usuario.setDni(nuevoValor);
//                    break;
//                default:
//                    return null;
//            }

