package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicioUsuarioImplTest {

    @Mock
    private RepositorioUsuario repositorioUsuarioMock;

    @InjectMocks
    private ServicioUsuarioImpl servicioUsuario;

    @BeforeEach
    public void init() {
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
