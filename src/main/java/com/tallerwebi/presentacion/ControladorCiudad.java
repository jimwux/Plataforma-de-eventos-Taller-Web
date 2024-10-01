package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ciudad;
import com.tallerwebi.dominio.ServicioCiudad;
import com.tallerwebi.dominio.ServicioProvincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControladorCiudad {
    
    private ServicioCiudad servicioCiudad;

    @Autowired
    public ControladorCiudad(ServicioCiudad servicioCiudad) {
        this.servicioCiudad = servicioCiudad;
    }

    @GetMapping("/ciudades/{nombreProvincia}")
    public List<Ciudad> obtenerCiudadesPorProvincia(@PathVariable String nombreProvincia) {
        return servicioCiudad.obtenerCiudadesPorProvincia(nombreProvincia);
    }

}
