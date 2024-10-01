package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CiudadExistente;
import com.tallerwebi.dominio.excepcion.ProvinciaExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ServicioCiudadImplTest {

    private ServicioCiudad servicioCiudad;
    private RepositorioCiudad repositorioCiudadMock;

    @BeforeEach
    public void init() {
        this.repositorioCiudadMock = mock(RepositorioCiudad.class);
        this.servicioCiudad = new ServicioCiudadImpl(repositorioCiudadMock);
    }

    @Test
    public void alNoExistirCiudadesDebeDevolverUnaListaVacia () {
        when(this.repositorioCiudadMock.obtenerTodasLasCiudades()).thenReturn(new ArrayList<>());
        List<Ciudad> ciudades = servicioCiudad.obtenerTodasLasCiudades();
        assertThat(ciudades, empty());
    }

    @Test
    public void dadoQueSeCreanDosCiudadesDebeDevolverUnaListaConEsosDosEventos () {
        Ciudad ciudadUno = new Ciudad();
        Ciudad ciudadDos = new Ciudad();
        List<Ciudad> ciudadesADevolver = Arrays.asList(ciudadUno, ciudadDos);

        when(this.repositorioCiudadMock.obtenerTodasLasCiudades()).thenReturn(ciudadesADevolver);
        List<Ciudad> ciudades = this.servicioCiudad.obtenerTodasLasCiudades();

        assertThat(ciudades.size(), is(2));
        assertThat(ciudades, containsInAnyOrder(ciudadUno, ciudadDos));
    }

    @Test
    public void dadoQueSeCreanCiudadesPuedoObtenerUnaPorSuNombre () {
        Ciudad ciudadUno = new Ciudad();
        ciudadUno.setNombre("Uno");

        when(this.repositorioCiudadMock.obtenerCiudadPorNombre("Uno")).thenReturn(ciudadUno);
        Ciudad ciudad = this.servicioCiudad.obtenerCiudadPorNombre("Uno");

        assertThat(ciudad, equalTo(ciudadUno));
        assertThat(ciudad.getNombre(), equalTo("Uno"));
    }

    @Test
    public void alIntentarAgregarCiudadesConElMismoNombreSoloDebeMantenerALaPrimeraDeEllas () {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Uno");

        Ciudad ciudadCopia = new Ciudad();
        ciudadCopia.setNombre("Uno");

        // La primera vez que ejecuta esta parte (if) devuelve nulo, la segunda vez ya agregó así que devuelve la provincia
        when(this.repositorioCiudadMock.obtenerCiudadPorNombre("Uno")).thenReturn(null);
        servicioCiudad.agregarCiudad(ciudad);

        when(this.repositorioCiudadMock.obtenerCiudadPorNombre("Uno")).thenReturn(ciudad);
        Exception excepcionObtenida = assertThrows(CiudadExistente.class, () -> {
            servicioCiudad.agregarCiudad(ciudadCopia);
        });

        assertThat("La ciudad ya existe", equalTo(excepcionObtenida.getMessage()));
        verify(this.repositorioCiudadMock, times(1)).guardar(ciudad);

    }

    @Test
    public void dadoQueExisteUnaCiudadEnBsAsYUnaEnTDFDebeDevolverUnaSolaCiudadCuandoBuscoCiudadesEnBsAs () {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        Ciudad moron = new Ciudad();
        moron.setNombre("Morón");
        moron.setProvincia(buenosAires);

        Provincia tierraDelFuego = new Provincia();
        tierraDelFuego.setNombre("Tierra del Fuego");
        Ciudad ushuaia = new Ciudad();
        ushuaia.setNombre("Ushuaia");
        ushuaia.setProvincia(tierraDelFuego);

        when(this.repositorioCiudadMock.obtenerCiudadesPorProvincia("Buenos Aires")).thenReturn(Arrays.asList(moron));
        List<Ciudad> ciudadesDeBsAs= servicioCiudad.obtenerCiudadesPorProvincia("Buenos Aires");

        assertThat(ciudadesDeBsAs.size(), is(1));
        assertThat(ciudadesDeBsAs.get(0).getNombre(), equalTo("Morón"));
        assertThat(ciudadesDeBsAs.get(0).getProvincia().getNombre(), equalTo("Buenos Aires"));
        verify(this.repositorioCiudadMock, times(1)).obtenerCiudadesPorProvincia("Buenos Aires");
    }


}
