package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioUsuarioImplTest {

    private ServicioUsuario servicioUsuario;
    private RepositorioUsuario repositorioUsuarioMock;

    @BeforeEach
    public void init() {
        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
        this.servicioUsuario = new ServicioUsuarioImpl(repositorioUsuarioMock);
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
}

