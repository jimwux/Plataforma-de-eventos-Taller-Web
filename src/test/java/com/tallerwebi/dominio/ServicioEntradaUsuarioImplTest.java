package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioEntradaUsuarioImplTest {

    private ServicioEntradaUsuario servicioEntradaUsuario;
    private RepositorioEntradaUsuario repositorioEntradaUsuarioMock;
    private GeneradorCodigoQr generadorCodigoQrMock;

    @BeforeEach
    public void init() {
        this.repositorioEntradaUsuarioMock = mock(RepositorioEntradaUsuario.class);
        this.servicioEntradaUsuario = new ServicioEntradaUsuarioImpl(this.repositorioEntradaUsuarioMock, generadorCodigoQrMock);
    }

    @Test
    void alGuardarCiertaCantidadDeEntradasDeUnTipoElMetodoGuardarDeRepositorioSeDebeEjecutarEsaCantidadDeVeces() {
        // Datos de prueba
        Integer cantidad = 3;
        Usuario usuario = new Usuario();
        Entrada entrada = new Entrada();
        String codigoTransaccion = "codigo123";

        servicioEntradaUsuario.guardarEntradasDeTipo(cantidad, usuario, entrada, codigoTransaccion);

        EntradaUsuario entradaUsuarioEsperada = new EntradaUsuario(usuario, entrada, codigoTransaccion);
        verify(repositorioEntradaUsuarioMock, times(cantidad)).guardar(argThat(entradaUsuario ->
                entradaUsuario.getUsuario().equals(usuario) &&
                        entradaUsuario.getEntrada().equals(entrada) &&
                        entradaUsuario.getCompraId().equals(codigoTransaccion)
        ));
    }

    @Test
    void alSeleccionarLaCategoriaFiestaDeEventosSeVeanLasEntradasCorrespondientesAEsaCategoriaDeEvento(){
        String correo = "solmaca0550@gmail.com";
        String categoria = "Fiesta";

        Usuario usuario = new Usuario();
        usuario.setEmail(correo);

        Evento evento = new Evento();
        evento.setCategoria(categoria);

        Entrada entrada = new Entrada();
        entrada.setEvento(evento);

        EntradaUsuario entradaUsuario = new EntradaUsuario();
        entradaUsuario.setEntrada(entrada);
        entradaUsuario.setUsuario(usuario);

        EntradaUsuario entradaUsuario1 = new EntradaUsuario();
        entradaUsuario1.setUsuario(usuario);
        entradaUsuario1.setEntrada(entrada);

        List<EntradaUsuario> entradasUsuario = new ArrayList<>();
        entradasUsuario.add(entradaUsuario1);
        entradasUsuario.add(entradaUsuario);

        when(this.repositorioEntradaUsuarioMock.obtenerEntradaPorUsuarioYCategoria(correo, categoria)).thenReturn(entradasUsuario);


        List<EntradaUsuario> entradasUs = this.servicioEntradaUsuario.obtenerEntradasDeUsuarioPorCategoria(correo, categoria);

        assertThat(entradasUs.size(), equalTo(2));


    }


}
