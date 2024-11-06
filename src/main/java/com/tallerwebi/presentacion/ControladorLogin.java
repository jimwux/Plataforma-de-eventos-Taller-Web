package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.dto.DatosLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView mostrarFormularioLogin() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("datosLoginDTO", new DatosLoginDTO());
        return modelAndView;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLoginDTO") DatosLoginDTO datosLoginDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLoginDTO.getEmail(), datosLoginDTO.getPassword());

        if (usuarioBuscado != null) {

            request.getSession().setAttribute("ID", usuarioBuscado.getId());
            request.getSession().setAttribute("email", usuarioBuscado.getEmail());
            return new ModelAndView("redirect:/eventos");
        } else {
            model.put("error", "Usuario o clave incorrecta");
            model.put("datosLoginDTO", new DatosLoginDTO());
            return new ModelAndView("login", model);
        }
    }

    @GetMapping("/eventos/login")
    public String irAHome() {
        return "home";
    }





}
