package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorCarritoTest {

    private ControladorCarrito controladorCarrito;
    private ServicioCarrito servicioCarritoMock;
    private ServicioEvento servicioEventoMock;


    @BeforeEach
    public void init(){
        this.servicioCarritoMock = mock(ServicioCarrito.class);
        this.servicioEventoMock = mock(ServicioEvento.class);
        this.controladorCarrito = new ControladorCarrito(servicioCarritoMock, servicioEventoMock);
    }

    @Test
    public void alAgregarObtejosAlCarritoSeRetornaALaVistaCorrecta() {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        List<Carrito> carritoMock = Arrays.asList(new Carrito(), new Carrito());
        when(this.servicioCarritoMock.obtenerEntradasDelCarrito(idsEntradas, cantidades)).thenReturn(carritoMock);
        when(this.servicioCarritoMock.calcularTotalCarrito(carritoMock)).thenReturn(800.0);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L);
        assertThat(modelAndView.getViewName(), equalTo("formularioPago"));
    }

    @Test
    public void cuandoSeAgreganEntradasAlCarritoEntoncesElModeloContieneLasEntradasCorrectas() {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        List<Carrito> carritoMock = Arrays.asList(new Carrito(), new Carrito());
        when(this.servicioCarritoMock.obtenerEntradasDelCarrito(idsEntradas, cantidades)).thenReturn(carritoMock);
        when(this.servicioCarritoMock.calcularTotalCarrito(carritoMock)).thenReturn(800.0);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L);

        assertThat(modelAndView.getModel().get("entradasCarrito"), equalTo(carritoMock));
        verify(this.servicioCarritoMock).obtenerEntradasDelCarrito(idsEntradas, cantidades);
    }

    @Test
    public void cuandoSeAgreganEntradasAlCarritoEentoncesElModeloContieneElTotalCorrecto() {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        List<Carrito> carritoMock = Arrays.asList(new Carrito(), new Carrito());
        when(this.servicioCarritoMock.obtenerEntradasDelCarrito(idsEntradas, cantidades)).thenReturn(carritoMock);
        when(this.servicioCarritoMock.calcularTotalCarrito(carritoMock)).thenReturn(800.0);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L);

        assertThat(modelAndView.getModel().get("totalCarrito"), equalTo(800.0));
        verify(this.servicioCarritoMock).calcularTotalCarrito(carritoMock);
    }



}
