package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ciudad;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.Provincia;
import com.tallerwebi.dominio.ServicioProvincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorProvincia {

    private ServicioProvincia servicioProvincia;

    @Autowired
    public ControladorProvincia(ServicioProvincia servicioProvincia) {
        this.servicioProvincia = servicioProvincia;
    }

    @GetMapping("/provincias")
    @ResponseBody
    public List<Provincia> obtenerProvincias() {
        return this.servicioProvincia.obtenerTodasLasProvincias();
    }



}
