package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CiudadExistente;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioCiudadImpl implements ServicioCiudad{

    private RepositorioCiudad repositorioCiudad;

    public ServicioCiudadImpl(RepositorioCiudad repositorioCiudadMock) {
        this.repositorioCiudad = repositorioCiudadMock;
    }

    @Override
    public List<Ciudad> obtenerTodasLasCiudades() {
        return this.repositorioCiudad.obtenerTodasLasCiudades();
    }

    @Override
    public Ciudad obtenerCiudadPorNombre(String nombreCiudad) {
        return this.repositorioCiudad.obtenerCiudadPorNombre(nombreCiudad);
    }

    @Override
    public void agregarCiudad(Ciudad ciudadCopia) {
        if (this.repositorioCiudad.obtenerCiudadPorNombre(ciudadCopia.getNombre()) != null) {
            throw new CiudadExistente("La ciudad ya existe");
        } this.repositorioCiudad.guardar(ciudadCopia);
    }

    @Override
    public List<Ciudad> obtenerCiudadesPorProvincia(String nombreProvincia) {
        return this.repositorioCiudad.obtenerCiudadesPorProvincia(nombreProvincia);
    }
}
