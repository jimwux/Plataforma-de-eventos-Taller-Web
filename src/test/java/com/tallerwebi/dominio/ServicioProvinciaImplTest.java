package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ProvinciaExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioProvinciaImplTest {

    private ServicioProvincia servicioProvincia;
    private RepositorioProvincia repositorioProvinciaMock;

    @BeforeEach
    public void init() {
        this.repositorioProvinciaMock = mock(RepositorioProvincia.class);
        this.servicioProvincia = new ServicioProvinciaImpl(repositorioProvinciaMock);
    }

    @Test
    public void alNoExistirProvinciasDebeDevolverUnaListaVacia () {
        when(repositorioProvinciaMock.obtenerTodasLasProvincias()).thenReturn(new ArrayList<>());
        List<Provincia> provincias = servicioProvincia.obtenerTodasLasProvincias();
        assertThat(provincias, empty());
    }

    @Test
    public void dadoQueSeCreanDosProvinciasDebeDevolverUnaListaConEsosDosEventos () {
       Provincia provinciaUno = new Provincia();
       Provincia provinciaDos = new Provincia();
       List<Provincia> provinciasADevolver = Arrays.asList(provinciaUno, provinciaDos);

       when(this.repositorioProvinciaMock.obtenerTodasLasProvincias()).thenReturn(provinciasADevolver);
       List<Provincia> provincias = this.servicioProvincia.obtenerTodasLasProvincias();

       assertThat(provincias.size(), is(2));
       assertThat(provincias, containsInAnyOrder(provinciaUno, provinciaDos));
    }

    @Test
    public void dadoQueSeCreanProvinciasPuedoObtenerUnaPorSuNombre () {
        Provincia provinciaUno = new Provincia();
        provinciaUno.setNombre("Uno");

        when(this.repositorioProvinciaMock.obtenerProvinciaPorNombre("Uno")).thenReturn(provinciaUno);
        Provincia provincia = this.servicioProvincia.obtenerProvinciaPorNombre("Uno");

        assertThat(provincia, equalTo(provinciaUno));
        assertThat(provincia.getNombre(), equalTo("Uno"));
    }

    @Test
    public void alIntentarAgregarProvinciasConElMismoNombreSoloDebeMantenerALaPrimeraDeEllas () {
        Provincia provincia = new Provincia();
        provincia.setNombre("Uno");

        Provincia provinciaCopia = new Provincia();
        provinciaCopia.setNombre("Uno");

        // La primera vez que ejecuta esta parte (if) devuelve nulo, la segunda vez ya agregó así que devuelve la provincia
        when(this.repositorioProvinciaMock.obtenerProvinciaPorNombre("Uno")).thenReturn(null);
        servicioProvincia.agregarProvincia(provincia);

        when(this.repositorioProvinciaMock.obtenerProvinciaPorNombre("Uno")).thenReturn(provincia);
        Exception excepcionObtenida = assertThrows(ProvinciaExistente.class, () -> {
            servicioProvincia.agregarProvincia(provinciaCopia);
        });

        assertThat("La provincia ya existe", equalTo(excepcionObtenida.getMessage()));
        verify(this.repositorioProvinciaMock, times(1)).guardar(provincia);

    }

}
