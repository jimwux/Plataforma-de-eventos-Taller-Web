package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioEntradaImplTest {

    private ServicioEntradaImpl servicioEntrada;
    private RepositorioEntrada repositorioEntradaMock;


    @BeforeEach
    public void init() {
        this.repositorioEntradaMock = mock(RepositorioEntrada.class);
        this.servicioEntrada = new ServicioEntradaImpl(repositorioEntradaMock);
    }

    @Test
    public void dadoQueExistenEntradasQueSePuedanMostrarTodasEllas(){
        List<Entrada> entradas = new ArrayList<>();

        Entrada entrada = new Entrada();
        entrada.setNombre("General");
        entradas.add(entrada);

        Entrada entrada2 = new Entrada();
        entrada2.setNombre("Campo");
        entradas.add(entrada2);

        Entrada entrada3 = new Entrada();
        entrada3.setNombre("Vip");
        entradas.add(entrada3);

        when(this.repositorioEntradaMock.obtenerTodasLasEntradas()).thenReturn(entradas);
        List <Entrada> obtenidas = this.servicioEntrada.obtenerTodasLasEntradas();

        assertThat(obtenidas.size(), equalTo(3));
        assertThat(obtenidas.get(0).getNombre(), equalTo("General"));
        verify(repositorioEntradaMock, times(1)).obtenerTodasLasEntradas();
    }

    @Test
    public void dadoQueExistenEntradasQueAlBuscarUnaPorCodigoMeLaDevuelva(){

        Entrada entrada = new Entrada();
        entrada.setNombre("General");
        entrada.setId(1L);

        when(this.repositorioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entrada);
        Entrada obtenida = this.servicioEntrada.obtenerEntradaPorId(1L);

        assertThat(obtenida.getNombre(), equalTo("General"));
        verify(repositorioEntradaMock, times(1)).obtenerEntradaPorId(1L);
    }






}
