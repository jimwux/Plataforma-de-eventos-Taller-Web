package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioEntradaImplTest {

    private ServicioEntradaImpl servicioEntrada;
    private RepositorioEntrada repositorioEntradaMock;


    @BeforeEach
    public void init() {
        this.repositorioEntradaMock = mock(RepositorioEntrada.class);
        this.servicioEntrada = new ServicioEntradaImpl(repositorioEntradaMock);
    }

    @Test
    public void dadoQueExistenEntradasQueSePuedanMostrarTodasEllas(){
        List<Entrada> entradas = new ArrayList<>();

        Entrada entrada = new Entrada();
        entrada.setNombre("General");
        entradas.add(entrada);

        Entrada entrada2 = new Entrada();
        entrada2.setNombre("Campo");
        entradas.add(entrada2);

        Entrada entrada3 = new Entrada();
        entrada3.setNombre("Vip");
        entradas.add(entrada3);

        when(this.repositorioEntradaMock.obtenerTodasLasEntradas()).thenReturn(entradas);
        List <Entrada> obtenidas = this.servicioEntrada.obtenerTodasLasEntradas();

        assertThat(obtenidas.size(), equalTo(3));
        assertThat(obtenidas.get(0).getNombre(), equalTo("General"));
        verify(repositorioEntradaMock, times(1)).obtenerTodasLasEntradas();
    }

    @Test
    public void dadoQueExistenEntradasQueAlBuscarUnaPorCodigoMeLaDevuelva(){

        Entrada entrada = new Entrada();
        entrada.setNombre("General");
        entrada.setId(1L);

        when(this.repositorioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entrada);
        Entrada obtenida = this.servicioEntrada.obtenerEntradaPorId(1L);

        assertThat(obtenida.getNombre(), equalTo("General"));
        verify(repositorioEntradaMock, times(1)).obtenerEntradaPorId(1L);
    }

    @Test
    public void dadoQueExistenEntradasAlBuscarUnEventoNosDebeDevolverLasEntradasDelMismo(){
        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNombre("Wasabi");

        Entrada entrada = new Entrada();
        entrada.setNombre("General");
        entrada.setEvento(evento);

        Entrada entradaDos = new Entrada();
        entradaDos.setNombre("General");
        entradaDos.setEvento(evento);

        List<Entrada> entradas = new ArrayList<>();
        entradas.add(entrada);
        entradas.add(entradaDos);


        when(this.repositorioEntradaMock.obtenerEntradasDeUnEvento(evento.getId())).thenReturn(entradas);
        List<Entrada> obtenidas = this.servicioEntrada.obtenerEntradasDeUnEvento(evento.getId());

        assertThat(obtenidas.size(), equalTo(2));

    }

    @Test
    public void alHaberEntradasSuficientesParaLaCompraSeValidaSuStock() {
        List<Long> idsEntradas = List.of(1L, 2L);
        List<Integer> cantidades = List.of(2, 3);

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setStock(10);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setStock(5);

        // Simula que hay suficiente stock
        when(this.repositorioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entrada1);
        when(this.repositorioEntradaMock.obtenerEntradaPorId(2L)).thenReturn(entrada2);

        boolean resultado = this.servicioEntrada.validarStockEntradas(idsEntradas, cantidades);
        assertThat(resultado, is(true));
    }

    @Test
    public void alNoHaberEntradasSuficientesParaLaCompraNoSeValidaSuStock() {
        List<Long> idsEntradas = List.of(1L, 2L);
        List<Integer> cantidades = List.of(2, 6);  // Supera el stock de la entrada 2

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setStock(10);

        Entrada entrada2 = new Entrada();
        entrada2.setId(2L);
        entrada2.setStock(5);  // Stock insuficiente para la cantidad solicitada

        // Simula que no hay suficiente stock para la entrada 2
        when(this.repositorioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entrada1);
        when(this.repositorioEntradaMock.obtenerEntradaPorId(2L)).thenReturn(entrada2);

        boolean resultado = this.servicioEntrada.validarStockEntradas(idsEntradas, cantidades);
        assertThat(resultado, is(false));
    }

    @Test
    public void alQuererSuperarElLimiteDeCompraDeEntradasNoSeValida() {
        List<Long> idsEntradas = List.of(1L);
        List<Integer> cantidades = List.of(5);  // Cantidad supera el límite de 4

        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        entrada1.setStock(10);

        // Simula que hay stock suficiente, pero la cantidad solicitada supera el límite de 4
        when(this.repositorioEntradaMock.obtenerEntradaPorId(1L)).thenReturn(entrada1);

        boolean resultado = this.servicioEntrada.validarStockEntradas(idsEntradas, cantidades);
        assertThat(resultado, is(false));
    }

    @Test
    public void alReducirElStockExitosamenteObtenemosLaCantidadDeFilasAfectadas () {
        Long idEntrada = 1L;
        Integer cantidad = 3;

        // Simula que la operación de reducción afecta 1 fila (éxito)
        when(this.repositorioEntradaMock.reducirStock(idEntrada, cantidad)).thenReturn(1);

        boolean resultado = this.servicioEntrada.reducirStock(idEntrada, cantidad);

        assertThat(resultado, is(true));
        verify(this.repositorioEntradaMock, times(1)).reducirStock(idEntrada, cantidad);
    }

    @Test
    public void alNoReducirElStockExitosamenteNoObtenemosLaCantidadDeFilasAfectadas() {
        Long idEntrada = 1L;
        Integer cantidad = 3;

        // Simula que la operación de reducción no afecta ninguna fila (fallo)
        when(this.repositorioEntradaMock.reducirStock(idEntrada, cantidad)).thenReturn(0);

        boolean resultado = this.servicioEntrada.reducirStock(idEntrada, cantidad);

        assertThat(resultado, is(false));
        verify(this.repositorioEntradaMock, times(1)).reducirStock(idEntrada, cantidad);
    }

}
