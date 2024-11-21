package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
public class ServicioUsuarioImplTest {

    private ServicioUsuarioImpl servicioUsuario;
    private RepositorioUsuario repositorioUsuarioMock;
    private RepositorioEntradaUsuario repositorioEntradaUsuarioMock;
    private RepositorioEntradaCompra repositorioEntradaCompraMock;
    private RepositorioDatosCompra repositorioDatosCompraMock;

    @BeforeEach
    public void init() {
        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
        this.repositorioEntradaUsuarioMock = mock(RepositorioEntradaUsuario.class);
        this.repositorioEntradaCompraMock = mock(RepositorioEntradaCompra.class);
        this.repositorioDatosCompraMock = mock(RepositorioDatosCompra.class);
        this.servicioUsuario = new ServicioUsuarioImpl(this.repositorioUsuarioMock, this.repositorioEntradaUsuarioMock, this.repositorioEntradaCompraMock, this.repositorioDatosCompraMock);
    }

    @Test
    public void dadoUnUsuarioMockSeTieneQuePoderObtenerAEsteMismoDelRepositorioUsandoSuEmailDeReferencia() {
        String email = "a@gmail.com";
        Usuario usuarioEsperado = new Usuario(email, "123", "Jose", "Lopez", "123456789", "12345678");

        when(repositorioUsuarioMock.buscar(email)).thenReturn(usuarioEsperado);

        Usuario usuarioObtenido = servicioUsuario.obtenerUsuarioVistaDTODelRepo(email);

        assertThat(usuarioObtenido.getEmail(), equalTo(usuarioEsperado.getEmail()));
        assertThat(usuarioObtenido, equalTo(usuarioEsperado));
    }
    @Test
    public void dadoQueUnUsuarioQuieraModificarUnDatoQueNoExisteEsteTireUnaExcepcion() {
        //preparacion
        String email = "a@gmail.com";
        Usuario usuario = new Usuario(email, "123", "Maria", "Rodriguez", "123456789", "44555666");
        String campoAModificarInexistente = "Género";
        String nuevoValor = "Hombre";
        //ejecucion
        when(repositorioUsuarioMock.buscar(email)).thenReturn(usuario);
        //verificacion
        assertThrows(IllegalArgumentException.class, () ->{
            servicioUsuario.actualizarDatoUsuario(email, campoAModificarInexistente, nuevoValor);
        });
    }

//    @Test
//    public void dadoUnUsuarioMockSeTieneQuePoderObtenerAEsteMismoDelRepositorioUsandoSuEmailDeReferencia() {
//        // Datos de prueba
//        String email = "a@gmail.com";
//        Usuario usuarioEsperado = new Usuario(email, "123", "Jose", "Lopez", "123456789", "12345678");
//
//        // Configurar el mock para que devuelva el usuario esperado
//        when(repositorioUsuarioMock.buscar(email)).thenReturn(usuarioEsperado);
//
//        // Llamar al método de la clase bajo prueba
//        Usuario usuarioObtenido = servicioUsuario.obtenerUsuarioVistaDTODelRepo(email);
//
//        // Verificar que el resultado sea el esperado
//        assertThat(usuarioObtenido.getEmail(), equalTo(usuarioEsperado.getEmail()));
//        assertThat(usuarioObtenido, equalTo(usuarioEsperado));
//    }
}
