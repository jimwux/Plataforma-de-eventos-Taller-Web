package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Ciudad;
import com.tallerwebi.dominio.Provincia;
import com.tallerwebi.dominio.ServicioCiudad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ControladorCiudadTest {

    private ControladorCiudad controladorCiudad;
    private ServicioCiudad servicioCiudadMock;


    @BeforeEach
    public void init(){
        this.servicioCiudadMock = mock(ServicioCiudad.class);
        this.controladorCiudad = new ControladorCiudad(servicioCiudadMock);
    }

    @Test
    public void dadoQueExistenCiudadesEnProvinciasAlIngresarBsAsSeObtienenSoloCiudadesDeEsaProvincia () {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        Provincia cordoba = new Provincia();
        cordoba.setNombre("Córdoba");

        Ciudad moron = new Ciudad();
        moron.setProvincia(buenosAires);

        Ciudad laPlata = new Ciudad();
        laPlata.setProvincia(buenosAires);

        List<Ciudad> ciudadesADevolver = Arrays.asList(moron, laPlata);

        when(this.servicioCiudadMock.obtenerCiudadesPorProvincia("Buenos Aires")).thenReturn(ciudadesADevolver);
        List<Ciudad> ciudades = this.controladorCiudad.obtenerCiudadesPorProvincia("Buenos Aires");

        assertThat(ciudades, is(not(empty())));
        assertThat(ciudades, hasSize(2));
        assertThat(ciudades, containsInAnyOrder(moron, laPlata));
        verify(this.servicioCiudadMock, times(1)).obtenerCiudadesPorProvincia("Buenos Aires");
    }

}
