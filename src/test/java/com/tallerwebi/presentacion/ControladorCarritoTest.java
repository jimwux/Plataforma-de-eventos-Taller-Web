package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

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

        ModelAndView modelAndView = this.controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L, redirectAttributes);

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

        ModelAndView modelAndView = this.controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L, redirectAttributes);

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

        ModelAndView modelAndView = this.controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, 1L, redirectAttributes);

        assertThat(modelAndView.getModel().get("totalCarrito"), equalTo(800.0));
        verify(this.servicioCarritoMock).calcularTotalCarrito(carritoMock);
    }

    @Test
    public void alAplicarUnCodigoValidoObtendremosElPrecioFinalConDescuento() {
        String codigoDescuento = "b5d21f4a";
        Double totalCarrito = 100.0;
        Double totalConDescuento = 80.0; // 20% de descuento

        when(this.servicioCarritoMock.esCodigoDescuentoValido(eq(codigoDescuento), any(LocalDateTime.class))).thenReturn(true);
        when(this.servicioCarritoMock.calcularTotalCarritoConDescuento(totalCarrito)).thenReturn(totalConDescuento);

        Map<String, Object> resultado = this.controladorCarrito.aplicarDescuento(codigoDescuento, totalCarrito);

        assertThat(resultado.get("descuentoAplicado"), is(true));
        assertThat(resultado.get("totalConDescuento"), equalTo(totalConDescuento));
    }

    @Test
    public void alAplicarUnCodigoInvalidoNoObtendremosElPrecioFinalConDescuento() {
        String codigoDescuento = "d5gf43aa";
        Double totalCarrito = 100.0;

        when(this.servicioCarritoMock.esCodigoDescuentoValido(codigoDescuento, LocalDateTime.now())).thenReturn(false);
        Map<String, Object> resultado = this.controladorCarrito.aplicarDescuento(codigoDescuento, totalCarrito);

        assertThat(resultado.get("descuentoAplicado"), is(false));
        assertThat(resultado.get("totalConDescuento"), equalTo(null));
    }

    @Test
    public void dadoQueSeAproboUnaCompraSeGeneraYEnviaPorCorreoUnCodigoDeDescuentoAlComprador() {
        String codigoDescuento = "b5d21f4a";  // El código de descuento esperado.
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
        when(this.servicioEntradaMock.reducirStock(1L, 2)).thenReturn(true); // Asegurarse de que se reduce el stock
        when(this.servicioCarritoMock.generarCodigoDescuento()).thenReturn(codigoDescuento);

        ModelAndView resultado = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        verify(this.servicioEntradaUsuarioMock, times(1))
                .guardarEntradasDeTipo(eq(2), any(Usuario.class), any(Entrada.class), eq(codigoTransaccion));

        assertThat(resultado.getViewName(), equalTo("compraRealizada"));
        assertThat(resultado.getModel().get("codigoDescuento"), equalTo(codigoDescuento));
        verify(this.servicioCarritoMock).guardarCodigoDescuento(codigoDescuento);
        verify(this.servicioEmailMock).enviarCodigoDescuento(emailUsuario, codigoDescuento);
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
        when(this.servicioEntradaMock.reducirStock(1L, 2)).thenReturn(true); // Asegurarse de que se reduce el stock

        ModelAndView resultado = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        verify(this.servicioEntradaUsuarioMock, times(1))
                .guardarEntradasDeTipo(eq(2), any(Usuario.class), any(Entrada.class), eq(codigoTransaccion));

        // Verificar otros aspectos
        assertThat(resultado.getViewName(), equalTo("compraRealizada"));
        assertThat(datosCompra.getEstado(), equalTo("completada"));
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

    @Test
    public void dadoQueHayStockSuficienteSeAgreganEntradasAlCarrito() {
        List<Long> idsEntradas = List.of(1L, 2L);
        List<Integer> cantidades = List.of(2, 1);
        Long eventoId = 10L;

        Evento eventoMock = new Evento();
        eventoMock.setId(eventoId);

        when(this.servicioEntradaMock.validarStockEntradas(idsEntradas, cantidades)).thenReturn(true);

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setPrecio(100.0);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setPrecio(200.0);

        List<Carrito> carritoMock = List.of(
                new Carrito(entrada1, 2, 200.0),
                new Carrito(entrada2, 1, 200.0)
        );

        when(this.servicioCarritoMock.obtenerEntradasDelCarrito(idsEntradas, cantidades)).thenReturn(carritoMock);
        when(this.servicioCarritoMock.calcularTotalCarrito(carritoMock)).thenReturn(400.0);
        when(this.servicioEventoMock.obtenerEventoPorId(eventoId)).thenReturn(eventoMock);

        ModelAndView modelAndView = this.controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, eventoId, redirectAttributes);

        assertThat(modelAndView.getViewName(), is("formularioPago"));
        assertThat(modelAndView.getModel().containsKey("entradasCarrito"), is(true));
        assertThat(modelAndView.getModel().containsKey("totalCarrito"), is(true));
        assertThat(modelAndView.getModel().get("totalCarrito"), equalTo(400.0));
        assertThat(modelAndView.getModel().get("evento"), equalTo(eventoMock));

        verify(this.servicioEntradaMock, times(1)).validarStockEntradas(idsEntradas, cantidades);
        verify(this.servicioCarritoMock, times(1)).obtenerEntradasDelCarrito(idsEntradas, cantidades);
        verify(this.servicioCarritoMock, times(1)).calcularTotalCarrito(carritoMock);
        verify(this.servicioEventoMock, times(1)).obtenerEventoPorId(eventoId);
    }

    @Test
    public void dadoQueNoHayStockSuficienteLasEntradasNoSeAgreganAlCarritoYTeRedirigeAlEvento() {
        List<Long> idsEntradas = List.of(1L, 2L);
        List<Integer> cantidades = List.of(2, 5);
        Long eventoId = 10L;

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(this.servicioEntradaMock.validarStockEntradas(idsEntradas, cantidades)).thenReturn(false);
        ModelAndView modelAndView = this.controladorCarrito.agregarAlCarrito(idsEntradas, cantidades, eventoId, redirectAttributes);

        assertThat(modelAndView.getViewName(), is("redirect:/eventos/" + eventoId));

        verify(this.servicioEntradaMock, times(1)).validarStockEntradas(idsEntradas, cantidades);
        verifyNoMoreInteractions(this.servicioCarritoMock, this.servicioEventoMock);

        assertThat(redirectAttributes.getFlashAttributes().containsKey("error"), is(true));
        assertThat(redirectAttributes.getFlashAttributes().get("error"), is("No hay suficiente stock disponible o has superado el límite de 4 entradas por tipo."));
    }

    @Test
    public void dadoQueHayStockSuficienteSeEfectuaLaCompra() {
        String codigoTransaccion = "ABC123";
        String status = "approved";
        String emailUsuario = "usuario@ejemplo.com";

        DatosCompra datosCompraMock = new DatosCompra();
        datosCompraMock.setCodigoTransaccion(codigoTransaccion);
        datosCompraMock.setEmailUsuario(emailUsuario);

        EntradaCompra entradaCompraMock = new EntradaCompra(1L, 3); // IdEntrada = 1L, cantidad = 3
        List<EntradaCompra> listaEntradasMock = List.of(entradaCompraMock);
        datosCompraMock.setEntradasCompradas(listaEntradasMock);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail(emailUsuario);

        Entrada entradaMock = new Entrada();
        entradaMock.setId(1L);
        entradaMock.setStock(10);

        when(this.servicioDatosCompraMock.obtenerCompraPorCodigoTransaccion(codigoTransaccion)).thenReturn(datosCompraMock);
        when(this.servicioLoginMock.verificarSiExiste(emailUsuario)).thenReturn(usuarioMock);
        when(this.servicioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entradaMock);
        when(this.servicioEntradaMock.reducirStock(1L, 3)).thenReturn(true);

        ModelAndView modelAndView = this.controladorCarrito.mostrarVistaCompraFinalizada(codigoTransaccion, status);

        assertThat(modelAndView.getViewName(), is("compraRealizada"));
        assertThat(modelAndView.getModel().containsKey("codigoDescuento"), is(true));
        assertThat(modelAndView.getModel().containsKey("error"), is(false));

        verify(this.servicioEntradaMock, times(1)).reducirStock(1L, 3);
    }



}
