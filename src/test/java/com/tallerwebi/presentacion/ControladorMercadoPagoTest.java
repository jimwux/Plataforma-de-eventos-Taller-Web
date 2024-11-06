package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ControladorMercadoPagoTest {

    private ControladorMercadoPago controladorMercadoPago;
    private ServicioCarrito servicioCarritoMock;
    private ServicioRegistro servicioRegistroMock;
    private ServicioLogin servicioLoginMock;
    private ServicioEmail servicioEmailMock;
    private ServicioDatosCompra servicioDatosCompraMock;
    private ServicioEntradaCompra servicioEntradaCompraMock;


    @BeforeEach
    public void init(){
        this.servicioCarritoMock = mock(ServicioCarrito.class);
        this.servicioRegistroMock = mock(ServicioRegistro.class);
        this.servicioEmailMock = mock(ServicioEmail.class);
        this.servicioDatosCompraMock = mock(ServicioDatosCompra.class);
        this.servicioLoginMock = mock(ServicioLogin.class);
        this.servicioEntradaCompraMock = mock(ServicioEntradaCompra.class);
        this.controladorMercadoPago = new ControladorMercadoPago(servicioCarritoMock, servicioRegistroMock, servicioLoginMock, servicioEmailMock, servicioDatosCompraMock, servicioEntradaCompraMock);
    }

    @Test
    public void testUsuarioNoExistenteYRegistro() throws UsuarioExistente {
        String email =  "usuario@ejemplo.com";
        String nombre = "Juan";
        String apellido = "Perez";
        String telefono = "12345678";
        String dni = "12345678";

        when(servicioLoginMock.verificarSiExiste(email)).thenReturn(null);

        Usuario usuario = controladorMercadoPago.verificarYRegistrarUsuario(email, nombre, apellido, telefono, dni);

        verify(servicioRegistroMock).registrar(usuario);
        verify(servicioEmailMock).enviarContraseniaAUsuarios(email, usuario.getPassword());
    }

    @Test
    public void alGuardarLosDatosDeLaCompraSeCreaUnObjetoEntradaCompraPorCadaTipoDeEntrada () {
        List<Integer> cantidades = Arrays.asList(2, 3, 5);
        List<Long> idsEntradas = Arrays.asList(101L, 102L, 103L);
        String emailUsuario = "usuario@ejemplo.com";
        String codigoTransaccion = "TX12345";

        this.controladorMercadoPago.guardarDatosCompra(cantidades, idsEntradas, emailUsuario, codigoTransaccion);

        verify(servicioEntradaCompraMock, times(3)).guardar(any(EntradaCompra.class));
        verify(servicioDatosCompraMock, times(1)).guardar(any(DatosCompra.class));
    }




}
