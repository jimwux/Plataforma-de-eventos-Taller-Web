package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRegistro;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {
    private final RepositorioUsuarioImpl repositorioUsuario;
    private ServicioRegistro servicioRegistro;

    public ControladorRegistro(ServicioRegistro servicioRegistro, RepositorioUsuarioImpl repositorioUsuario) {
    this.servicioRegistro = servicioRegistro;
        this.repositorioUsuario = repositorioUsuario;
    }

    public String registrar(Usuario usuario) throws UsuarioExistente {
        return servicioRegistro.registrar(usuario);
    }

    @GetMapping(path = "/registro")
    public ModelAndView mostrarFormularioRegistro() {
        return new ModelAndView("registro").addObject("usuario", new Usuario());
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        // validar si el usuario es nulo
            if (usuario == null) {
                ModelAndView modelAndView = new ModelAndView("registro");
                modelAndView.addObject("error", "El usuario no puede ser nulo");
                return modelAndView;
            }
            // validar si el email es valido
            if(!esEmailValido(usuario.getEmail())){
            ModelAndView modelAndView = new ModelAndView("registro");
            modelAndView.addObject("error", "El email no es valido");
            return modelAndView;
            }

        ModelMap model = new ModelMap();
        try{
            this.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El email ya existe");
            return new ModelAndView("registro", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("registro", model);
        }
        return new ModelAndView("redirect:/login");
    }

    private boolean esEmailValido(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
