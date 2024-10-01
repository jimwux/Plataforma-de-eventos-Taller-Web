package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProvincia {
    void guardar(Provincia buenosAires);

    Provincia obtenerProvinciaPorNombre(String buenosAires);

    List<Provincia> obtenerTodasLasProvincias();

    void eliminarProvincia(Provincia provincia);
}
