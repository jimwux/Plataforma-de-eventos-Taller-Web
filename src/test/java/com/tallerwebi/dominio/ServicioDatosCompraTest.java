package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioDatosCompraTest {

    private ServicioDatosCompra servicioDatosCompra;
    private RepositorioDatosCompra repositorioDatosCompraMock;

    @BeforeEach
    public void init() {
        this.repositorioDatosCompraMock = mock(RepositorioDatosCompra.class);
        this.servicioDatosCompra = new ServicioDatosCompraImpl(this.repositorioDatosCompraMock);
    }

    @Test
    public void alGuardarCompraPendienteSeLlamaRepositorioGuardar() {
        DatosCompra datosCompra = new DatosCompra();
        this.servicioDatosCompra.guardar(datosCompra);

        verify(this.repositorioDatosCompraMock, times(1)).guardar(datosCompra);
    }

    @Test
    public void alEliminarCompraPorCodigoTransaccionSeLlamaRepositorioEliminar() {
        String codigoTransaccion = "12345";

        this.servicioDatosCompra.eliminarCompraPorCodigoTransaccion(codigoTransaccion);

        verify(this.repositorioDatosCompraMock, times(1)).eliminarCompraPorCodigoTransaccion(codigoTransaccion);
    }

    @Test
    public void cuandoObtenerCompraPorCodigoTransaccionSeDevuelveCompraCorrecta() {
        String codigoTransaccion = "12345";
        DatosCompra datosCompraEsperado = new DatosCompra();

        when(this.repositorioDatosCompraMock.obtenerCompraPorCodigoTransaccion(codigoTransaccion)).thenReturn(datosCompraEsperado);
        DatosCompra resultado = this.servicioDatosCompra.obtenerCompraPorCodigoTransaccion(codigoTransaccion);

        verify(this.repositorioDatosCompraMock, times(1)).obtenerCompraPorCodigoTransaccion(codigoTransaccion);
        assertThat(resultado, equalTo(datosCompraEsperado));
    }


}
