package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
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
    private ServicioDatosCompra servicioDatosCompraMock;
    private ServicioLogin servicioLoginMock;
    private ServicioEntrada servicioEntradaMock;
    private ServicioEntradaUsuario servicioEntradaUsuarioMock;
    private RedirectAttributes redirectAttributes;


    @BeforeEach
    public void init(){
        this.servicioCarritoMock = mock(ServicioCarrito.class);
        this.servicioEventoMock = mock(ServicioEvento.class);
        this.servicioEmailMock = mock(ServicioEmail.class);
        this.servicioDatosCompraMock = mock(ServicioDatosCompra.class);
        this.servicioLoginMock = mock(ServicioLogin.class);
        this.servicioEntradaMock = mock(ServicioEntrada.class);
        this.servicioEntradaUsuarioMock = mock(ServicioEntradaUsuario.class);
        this.redirectAttributes = mock(RedirectAttributes.class);
        this.controladorCarrito = new ControladorCarrito(servicioCarritoMock, servicioEventoMock, servicioEmailMock, servicioDatosCompraMock, servicioLoginMock, servicioEntradaMock, servicioEntradaUsuarioMock);
    }

    @Test
    public void alAgregarObtejosAlCarritoSeRetornaALaVistaCorrecta() {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        when(this.servicioEntradaMock.validarStockEntradas(idsEntradas, cantidades)).thenReturn(true);

        List<Carrito> carritoMock = Arrays.asList(new Carrito(), new Carrito());
        when(this.servicioCarritoMock.obtenerEntradasDelCarrito(idsEntradas, cantidades)).thenReturn(carritoMock);
        when(this.servicioCarritoMock.calcularTotalCarrito(carritoMock)).thenReturn(800.0);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L, redirectAttributes);

        assertThat(modelAndView.getViewName(), equalTo("formularioPago"));
    }

    @Test
    public void cuandoSeAgreganEntradasAlCarritoEntoncesElModeloContieneLasEntradasCorrectas() {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        when(this.servicioEntradaMock.validarStockEntradas(idsEntradas, cantidades)).thenReturn(true);

        List<Carrito> carritoMock = Arrays.asList(new Carrito(), new Carrito());
        when(this.servicioCarritoMock.obtenerEntradasDelCarrito(idsEntradas, cantidades)).thenReturn(carritoMock);
        when(this.servicioCarritoMock.calcularTotalCarrito(carritoMock)).thenReturn(800.0);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L, redirectAttributes);

        assertThat(modelAndView.getModel().get("entradasCarrito"), equalTo(carritoMock));
        verify(this.servicioCarritoMock).obtenerEntradasDelCarrito(idsEntradas, cantidades);
    }

    @Test
    public void cuandoSeAgreganEntradasAlCarritoEentoncesElModeloContieneElTotalCorrecto() {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        when(this.servicioEntradaMock.validarStockEntradas(idsEntradas, cantidades)).thenReturn(true);

        List<Carrito> carritoMock = Arrays.asList(new Carrito(), new Carrito());
        when(this.servicioCarritoMock.obtenerEntradasDelCarrito(idsEntradas, cantidades)).thenReturn(carritoMock);
        when(this.servicioCarritoMock.calcularTotalCarrito(carritoMock)).thenReturn(800.0);

        ModelAndView modelAndView = controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L, redirectAttributes);

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

    @Test
    public void dadoQueSeAproboUnaCompraSeGeneraYEnviaPorCorreoUnCodigoDeDescuentoAlComprador() {
        String codigoTransaccion = "12345";
        String emailUsuario = "usuario@ejemplo.com";
        String status = "approved";

        DatosCompra datosCompra = new DatosCompra();
        datosCompra.setEmailUsuario(emailUsuario);
        datosCompra.setEntradasCompradas(List.of(new EntradaCompra()));

        when(this.servicioDatosCompraMock.obtenerCompraPorCodigoTransaccion(codigoTransaccion)).thenReturn(datosCompra);
        when(this.servicioLoginMock.verificarSiExiste(emailUsuario)).thenReturn(new Usuario());
        when(this.servicioCarritoMock.generarCodigoDescuento()).thenReturn("b5d21f4a");

        ModelAndView resultado = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        assertThat(resultado.getModel().get("codigoDescuento"), is("b5d21f4a"));
        verify(this.servicioCarritoMock).guardarCodigoDescuento("b5d21f4a");
        verify(this.servicioEmailMock).enviarCodigoDescuento(emailUsuario, "b5d21f4a");
    }

    @Test
    public void alComprarEntradasSeLeAsignanAlUsuario() {
        String codigoTransaccion = "12345";
        String emailUsuario = "usuario@ejemplo.com";
        String status = "approved";

        DatosCompra datosCompra = new DatosCompra();
        datosCompra.setEmailUsuario(emailUsuario);
        EntradaCompra entradaCompra = new EntradaCompra();
        entradaCompra.setIdEntrada(1L);
        entradaCompra.setCantidad(2);
        datosCompra.setEntradasCompradas(List.of(entradaCompra));

        when(this.servicioDatosCompraMock.obtenerCompraPorCodigoTransaccion(codigoTransaccion)).thenReturn(datosCompra);
        when(this.servicioLoginMock.verificarSiExiste(emailUsuario)).thenReturn(new Usuario());
        when(this.servicioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(new Entrada());

        ModelAndView resultado = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        assertThat(resultado.getViewName(), equalTo("compraRealizada"));
        assertThat(datosCompra.getEstado(), equalTo("completada"));
        verify(this.servicioEntradaUsuarioMock, times(1)).guardarEntradasDeTipo(2, new Usuario(), new Entrada(), codigoTransaccion);
    }

    @Test
    void alLlevarACaboUnaCompraEstaCambiaSuEstadoDePendienteACompletada() {
        String codigoTransaccion = "codigo123";
        String emailUsuario = "usuario@ejemplo.com";
        String status = "approved";

        DatosCompra datosCompra = new DatosCompra();
        datosCompra.setEmailUsuario(emailUsuario);
        EntradaCompra entradaCompra = new EntradaCompra();
        entradaCompra.setIdEntrada(1L);
        entradaCompra.setCantidad(2);
        datosCompra.setEntradasCompradas(List.of(entradaCompra));

        when(this.servicioDatosCompraMock.obtenerCompraPorCodigoTransaccion(codigoTransaccion)).thenReturn(datosCompra);
        when(this.servicioLoginMock.verificarSiExiste(emailUsuario)).thenReturn(new Usuario());
        ModelAndView modelAndView = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        assertThat(datosCompra.getEstado(), equalTo("completada"));

    }


    @Test
    public void dadoQueUnaCompraNoFueAprobadaSeEliminanSusDatosDeLaBaseDeDatos() {
        String codigoTransaccion = "12345";
        String status = "declined"; // Estado no aprobado

        ModelAndView resultado = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        assertThat(resultado.getViewName(), is("compraRealizada"));
        verify(this.servicioDatosCompraMock).eliminarCompraPorCodigoTransaccion(codigoTransaccion);
        assertThat(resultado.getModel().get("error"), is("La compra no se completó con éxito y ha sido eliminada."));
    }

    @Test
    public void dadoQueNoHayEntradasCompradasNoSeGuardaNingunaEntrada() {
        String codigoTransaccion = "12345";
        String emailUsuario = "usuario@ejemplo.com";
        String status = "approved";

        DatosCompra datosCompra = new DatosCompra();
        datosCompra.setEmailUsuario(emailUsuario);
        datosCompra.setEntradasCompradas(Collections.emptyList()); // Lista vacía de entradas compradas

        when(this.servicioDatosCompraMock.obtenerCompraPorCodigoTransaccion(codigoTransaccion)).thenReturn(datosCompra);
        when(this.servicioLoginMock.verificarSiExiste(emailUsuario)).thenReturn(new Usuario());

        ModelAndView resultado = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        assertThat(resultado.getViewName(), is("compraRealizada"));
        verify(this.servicioEntradaUsuarioMock, never()).guardarEntradasDeTipo(anyInt(), any(), any(), any());
    }


}
