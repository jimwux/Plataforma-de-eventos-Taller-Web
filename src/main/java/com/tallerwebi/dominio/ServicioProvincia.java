package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioProvincia {
    List<Provincia> obtenerTodasLasProvincias();

    Provincia obtenerProvinciaPorNombre(String uno);

    void agregarProvincia(Provincia provincia);
}
