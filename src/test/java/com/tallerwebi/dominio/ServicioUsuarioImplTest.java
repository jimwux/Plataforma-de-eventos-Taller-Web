package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
}

