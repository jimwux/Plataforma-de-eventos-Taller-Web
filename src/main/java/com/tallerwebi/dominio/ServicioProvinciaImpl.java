package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ProvinciaExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioProvinciaImpl implements ServicioProvincia {

    private RepositorioProvincia repositorioProvincia;

    @Autowired
    public ServicioProvinciaImpl(RepositorioProvincia repositorioProvinciaMock) {
        this.repositorioProvincia = repositorioProvinciaMock;
    }


    @Override
    public List<Provincia> obtenerTodasLasProvincias() {
        return this.repositorioProvincia.obtenerTodasLasProvincias();
    }

    @Override
    public Provincia obtenerProvinciaPorNombre(String nombreProvincia) {
        return this.repositorioProvincia.obtenerProvinciaPorNombre(nombreProvincia);
    }

    @Override
    public void agregarProvincia(Provincia provincia) {
        if (this.repositorioProvincia.obtenerProvinciaPorNombre(provincia.getNombre()) != null) {
            throw new ProvinciaExistente("La provincia ya existe");
        }
    }
}
