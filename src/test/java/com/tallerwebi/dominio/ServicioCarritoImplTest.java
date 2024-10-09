package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicioCarritoImplTest {

    private ServicioCarrito servicioCarrito;
    private RepositorioEntrada repositorioEntradaMock;

    @BeforeEach
    public void init() {
        this.repositorioEntradaMock = mock(RepositorioEntrada.class);
        this.servicioCarrito = new ServicioCarritoImpl(repositorioEntradaMock);
    }

    @Test
    public void dadoQueExisteUnCarritoPodemosObtenerLosItemsDelMismo () {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombre("Concierto A");
        entrada1.setPrecio(100.0);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setNombre("Concierto B");
        entrada2.setPrecio(200.0);

        when(this.repositorioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entrada1);
        when(this.repositorioEntradaMock.obtenerEntradaPorId(2L)).thenReturn(entrada2);

        List<Carrito> carrito = servicioCarrito.obtenerEntradasDelCarrito(idsEntradas, cantidades);

        assertThat(carrito, hasSize(2));
        assertThat(carrito.get(0).getTotalCarrito(), equalTo(200.0)); // 100 * 2
        assertThat(carrito.get(1).getTotalCarrito(), equalTo(600.0)); // 200 * 3
    }

    @Test
    public void dadoQueExisteUnCarritoPodemosObtenerElValorFinalDelMismo () {
        List<Long> idsEntradas = Arrays.asList(1L, 2L);
        List<Integer> cantidades = Arrays.asList(2, 3);

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setNombre("Concierto A");
        entrada1.setPrecio(100.0);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setNombre("Concierto B");
        entrada2.setPrecio(200.0);

        Double totalEsperado = (100.0 * 2) + (200.0 * 3);

        when(this.repositorioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entrada1);
        when(this.repositorioEntradaMock.obtenerEntradaPorId(2L)).thenReturn(entrada2);

        List<Carrito> carrito = servicioCarrito.obtenerEntradasDelCarrito(idsEntradas, cantidades);
        Double totalObtenido = servicioCarrito.calcularTotalCarrito(carrito);

        assertThat(totalObtenido, equalTo(totalObtenido));
    }


}
