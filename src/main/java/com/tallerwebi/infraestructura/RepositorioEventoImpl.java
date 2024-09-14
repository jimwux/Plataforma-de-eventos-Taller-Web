package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioEventoImpl implements RepositorioEvento {


    @Override
    public List<Evento> obtenerTodosLosEventos() {
        return new ArrayList<Evento>();
    }
}
