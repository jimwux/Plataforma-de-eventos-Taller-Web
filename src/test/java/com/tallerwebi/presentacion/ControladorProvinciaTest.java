package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Provincia;
import com.tallerwebi.dominio.ServicioEventoImpl;
import com.tallerwebi.dominio.ServicioProvincia;
import com.tallerwebi.dominio.ServicioProvinciaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class ControladorProvinciaTest {

    private ControladorProvincia controladorProvincia;
    private ServicioProvincia servicioProvinciaMock;


    @BeforeEach
    public void init(){
        this.servicioProvinciaMock = mock(ServicioProvinciaImpl.class);
        this.controladorProvincia = new ControladorProvincia(servicioProvinciaMock);
    }

    @Test
    public void dadoQueExistenProvinciasDebeRetornarUnaListaNoVacia () {
        Provincia buenosAires = new Provincia();
        Provincia cordoba = new Provincia();
        List<Provincia> provinciasADevolver = Arrays.asList(buenosAires, cordoba);

        when(this.servicioProvinciaMock.obtenerTodasLasProvincias()).thenReturn(provinciasADevolver);
        List<Provincia> provincias = this.controladorProvincia.obtenerProvincias();

        assertThat(provincias, is(not(empty())));
        assertThat(provincias, hasSize(2));
        assertThat(provincias, containsInAnyOrder(buenosAires, cordoba));
        verify(this.servicioProvinciaMock, times(1)).obtenerTodasLasProvincias();
    }


}
