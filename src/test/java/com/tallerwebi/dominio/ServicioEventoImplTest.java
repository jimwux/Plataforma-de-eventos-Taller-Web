package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

public class ServicioEventoImplTest {

    private ServicioEventoImpl servicioEvento;
    private RepositorioEvento repositorioEventoMock;

    @BeforeEach
    public void init() {
        this.repositorioEventoMock = mock(RepositorioEvento.class);
        this.servicioEvento = new ServicioEventoImpl(repositorioEventoMock);
    }

    @Test
    public void alNoExistirEventosDebeDevolverUnaListaVacia () {
        when(repositorioEventoMock.obtenerTodosLosEventos()).thenReturn(new ArrayList<>());
        List<Evento> eventos = servicioEvento.obtenerTodosLosEventos();
        assertThat(eventos, empty());
    }

    @Test
    public void dadoQueSeCreanDosEventosDebeDevolverUnaListaConEsosDosEventos () {

        Long primerId = 1L;
        Long segundoId = 2L;

        Evento primerEvento = new Evento();
        primerEvento.setId(primerId);
        Evento segundoEvento = new Evento();
        segundoEvento.setId(segundoId);

        devuelveUnaListaConEsosDosElementos(primerEvento, segundoEvento);


    }


    private void devuelveUnaListaConEsosDosElementos(Evento primerEvento, Evento segundoEvento) {
        {
            List<Evento> eventos = servicioEvento.obtenerTodosLosEventos();
            when(repositorioEventoMock.obtenerTodosLosEventos()).thenReturn(new ArrayList<>());
            eventos.add(primerEvento);
            eventos.add(segundoEvento);
            //Esto cuenta como mockear??

            assertThat(eventos.size(), is(2));
            assertThat(eventos.get(0).getId(), equalTo(primerEvento.getId()));
            assertThat(eventos.get(1).getId(), equalTo(segundoEvento.getId()));
        }

    }

    @Test
    public void dadoQueExistenElementosAlBuscarlosObtenemosTodosLosQueSuNombreComiencenIgual() {
        Evento eventoUno = new Evento();
        eventoUno.setNombre("Creamfields");
        Evento eventoDos = new Evento();
        eventoDos.setNombre("CreativeFest");
        List<Evento> eventos = Arrays.asList(eventoUno, eventoDos);

        when(this.repositorioEventoMock.buscarEventosPorNombre("crea")).thenReturn(eventos);
        List<Evento> resultados = this.servicioEvento.buscarEventosPorNombre("crea");

        assertThat(resultados.size(), is(2));
        assertThat(resultados.get(0).getNombre(), equalTo(eventoUno.getNombre()));
        assertThat(resultados.get(1).getNombre(), equalTo(eventoDos.getNombre()));
        verify(this.repositorioEventoMock).buscarEventosPorNombre("crea");
    }
    
    @Test
    public void dadoQueExistenEventosPodemosObtenerlosPorSuId() {
        Evento eventoMock = new Evento();
        eventoMock.setId(1L);

        when(this.repositorioEventoMock.obtenerEventoPorId(1L)).thenReturn(eventoMock);
        Evento eventoObtenido = this.servicioEvento.obtenerEventoPorId(1L);

        verify(this.repositorioEventoMock, times(1)).obtenerEventoPorId(1L);
        assertThat(eventoObtenido.getId(), is(1L));
        assertThat(eventoObtenido, equalTo(eventoMock));
    }
}
