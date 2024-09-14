package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class ServicioEvento {

    public List<Evento> obtenerTodosLosEventos() {
        return new ArrayList<>();
    }
}
