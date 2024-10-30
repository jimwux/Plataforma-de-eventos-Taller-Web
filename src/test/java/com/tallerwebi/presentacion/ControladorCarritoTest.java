package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.dominio.ServicioEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class ControladorCarritoTest {

    private ControladorCarrito controladorCarrito;
    private ServicioCarrito servicioCarritoMock;
    private ServicioEvento servicioEventoMock;
    private ServicioEmail servicioEmailMock;


    @BeforeEach
    public void init(){
        this.servicioCarritoMock = mock(ServicioCarrito.class);
        this.servicioEventoMock = mock(ServicioEvento.class);
        this.servicioEmailMock = mock(ServicioEmail.class);
        this.controladorCarrito = new ControladorCarrito(servicioCarritoMock, servicioEventoMock, servicioEmailMock);
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

    @Test
    public void alAplicarUnCodigoValidoObtendremosElPrecioFinalConDescuento() {
        String codigoDescuento = "b5d21f4a";
        Double totalCarrito = 100.0;
        Double totalConDescuento = 80.0; // 20% de descuento

        when(servicioCarritoMock.esCodigoDescuentoValido(eq(codigoDescuento), any(LocalDateTime.class))).thenReturn(true);
        when(servicioCarritoMock.calcularTotalCarritoConDescuento(totalCarrito)).thenReturn(totalConDescuento);

        Map<String, Object> resultado = controladorCarrito.aplicarDescuento(codigoDescuento, totalCarrito);

        assertThat(resultado.get("descuentoAplicado"), is(true));
        assertThat(resultado.get("totalConDescuento"), equalTo(totalConDescuento));
    }

    @Test
    public void alAplicarUnCodigoInvalidoNoObtendremosElPrecioFinalConDescuento() {
        String codigoDescuento = "d5gf43aa";
        Double totalCarrito = 100.0;

        when(servicioCarritoMock.esCodigoDescuentoValido(codigoDescuento, LocalDateTime.now())).thenReturn(false);
        Map<String, Object> resultado = controladorCarrito.aplicarDescuento(codigoDescuento, totalCarrito);

        assertThat(resultado.get("descuentoAplicado"), is(false));
        assertThat(resultado.get("totalConDescuento"), equalTo(null));
    }


}
