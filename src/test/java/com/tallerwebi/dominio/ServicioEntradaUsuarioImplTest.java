package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        verify(repositorioEntradaUsuarioMock, times(cantidad)).guardar(entradaUsuarioEsperada);
    }


}
