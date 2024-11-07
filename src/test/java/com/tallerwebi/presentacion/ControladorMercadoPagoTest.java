package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


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
    public void cuandoCompraUnUsuarioExistenteNoSeVuelveARegistrar() throws UsuarioExistente {
        String email = "usuario@ejemplo.com";
        String nombre = "Juan";
        String apellido = "Perez";
        String telefono = "12345678";
        String dni = "12345678";

        Usuario usuarioExistente = new Usuario(email, "contraseniaExistente", nombre, apellido, telefono, dni);

        when(servicioLoginMock.verificarSiExiste(email)).thenReturn(usuarioExistente);
        Usuario usuario = controladorMercadoPago.verificarYRegistrarUsuario(email, nombre, apellido, telefono, dni);

        assertThat(usuarioExistente, equalTo(usuario));
        verify(servicioRegistroMock, never()).registrar(usuarioExistente);
        verify(servicioEmailMock, never()).enviarContraseniaAUsuarios(anyString(), anyString());
    }

    @Test
    public void cuandoCompraUnUsuarioQueNoEstaRegistradoDebemosRegistrarlo () throws UsuarioExistente {
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
    public void alRegistrarUnUsuarioQueNoExistiaSeLeDebeEnviarLaContraseniaCorrecta () throws UsuarioExistente {
        String email = "usuario@ejemplo.com";
        String nombre = "Juan";
        String apellido = "Perez";
        String telefono = "12345678";
        String dni = "12345678";

        String contrasenaGenerada = "contraseñaGenerada";

        when(servicioLoginMock.verificarSiExiste(email)).thenReturn(null);
        when(servicioRegistroMock.generarContrasena()).thenReturn(contrasenaGenerada);
        Usuario usuario = controladorMercadoPago.verificarYRegistrarUsuario(email, nombre, apellido, telefono, dni);

        assertThat(contrasenaGenerada, equalTo(usuario.getPassword()));
        verify(servicioRegistroMock).registrar(usuario);
        verify(servicioEmailMock).enviarContraseniaAUsuarios(email, contrasenaGenerada);
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

    @Test
    public void noSePuedenCuardarLosDatosDeLaCompraSiNoCoincidenLasCantidadesDeLosDatosDeLasEntradas () {
        List<Integer> cantidades = Arrays.asList(2, 3);
        List<Long> idsEntradas = Arrays.asList(101L, 102L, 103L);
        String emailUsuario = "usuario@ejemplo.com";
        String codigoTransaccion = "TX12345";

        assertThrows(IllegalArgumentException.class, () -> {
            controladorMercadoPago.guardarDatosCompra(cantidades, idsEntradas, emailUsuario, codigoTransaccion);
        });

        verify(servicioEntradaCompraMock, never()).guardar(any(EntradaCompra.class));
        verify(servicioDatosCompraMock, never()).guardar(any(DatosCompra.class));
    }


}
