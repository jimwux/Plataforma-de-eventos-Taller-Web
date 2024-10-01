package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

public class ServicioEventoImplTest {

    private ServicioEvento servicioEvento;
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

            assertThat(eventos.size(), is(2));
            assertThat(eventos.get(0).getId(), equalTo(primerEvento.getId()));
            assertThat(eventos.get(1).getId(), equalTo(segundoEvento.getId()));
        }

    }

    @Test
    public void alAgregarEventosDuplicadosSoloDebeMantenerAlPrimeroDeEllos() {
        Long id = 1L;
        
        Evento primerEvento = dadoUnEvento(id);
        Evento eventoDuplicado = seGeneraUnEventoDuplicadoDelPrimero(id);
        soloDebeDevolverAlPrimeroDeEllos(primerEvento, eventoDuplicado);

    }

    private void soloDebeDevolverAlPrimeroDeEllos(Evento primerEvento, Evento eventoDuplicado) {
        List<Evento> eventos = servicioEvento.obtenerTodosLosEventos();
        when(repositorioEventoMock.obtenerTodosLosEventos()).thenReturn(new ArrayList<>());

        assertThat(eventos.size(), is(0));

        servicioEvento.agregarEvento(primerEvento);
        eventos = servicioEvento.obtenerTodosLosEventos();
        eventos.add(primerEvento);

        assertThat(eventos.size(), is(1));
        assertThat(eventos.get(0).getId(), is(primerEvento.getId()));

        servicioEvento.agregarEvento(eventoDuplicado);
        eventos = servicioEvento.obtenerTodosLosEventos();
        //eventos.add(eventoDuplicado); el filtro esta en el metodo "Agregar Evento"

        assertThat(eventos.size(), is(1));

    }

    private Evento dadoUnEvento(Long id) {
        Evento primerEvento = new Evento();
        primerEvento.setId(id);
        return primerEvento;
    }

    private Evento seGeneraUnEventoDuplicadoDelPrimero(Long id) {
        Evento eventoDuplicado = new Evento();
        eventoDuplicado.setId(id);
        return eventoDuplicado;
    }

    @Test
    public void debeLlamarAlRepositorioSoloUnaVez() {
        when(repositorioEventoMock.obtenerTodosLosEventos()).thenReturn(new ArrayList<>());

        servicioEvento.obtenerTodosLosEventos();

        verify(repositorioEventoMock, times(1)).obtenerTodosLosEventos();
    }

    @Test
    public void dadoQueExistenElementosAlBuscarlosObtenemosTodosLosQueSuNombreComiencenIgual() {
        Evento eventoUno = new Evento();
        eventoUno.setNombre("Creamfields");
        Evento eventoDos = new Evento();
        eventoDos.setNombre("CreativeFest");
        List<Evento> eventos = Arrays.asList(eventoUno, eventoDos);

        when(this.repositorioEventoMock.buscarEventosPorNombre("crea")).thenReturn(eventos);
        List<Evento> resultados = this.servicioEvento.filtrarEventos("crea", null,null);

        assertThat(resultados.size(), is(2));
        assertThat(resultados.get(0).getNombre(), equalTo(eventoUno.getNombre()));
        assertThat(resultados.get(1).getNombre(), equalTo(eventoDos.getNombre()));
        verify(this.repositorioEventoMock).buscarEventosPorNombre("crea");
    }

    @Test
    public void dadoQueSeBuscanEventosPorNombreProvinciaYCiudadDevuelveEventosFiltrados() {
        Evento evento = new Evento();
        List<Evento> eventos = Arrays.asList(evento);

        when(repositorioEventoMock.buscarEventosPorCiudadYNombre("Morón", "Evento")).thenReturn(eventos);
        List<Evento> eventosFiltrados = servicioEvento.filtrarEventos("Evento", "Buenos Aires", "Morón");

        assertThat(eventosFiltrados.size(), is(1));
        assertThat(eventosFiltrados.get(0), equalTo(evento));
        verify(repositorioEventoMock).buscarEventosPorCiudadYNombre("Morón", "Evento");
    }

    @Test
    public void dadoQueSeBuscanEventosPorNombreYProvinciaDevuelveEventosFiltrados() {
        Evento evento = new Evento();
        List<Evento> eventos = Arrays.asList(evento);

        when(repositorioEventoMock.buscarEventosPorProvinciaYNombre("Buenos Aires", "Evento")).thenReturn(eventos);

        List<Evento> eventosFiltrados = servicioEvento.filtrarEventos("Evento", "Buenos Aires", null);

        assertThat(eventosFiltrados.size(), is(1));
        assertThat(eventosFiltrados.get(0), equalTo(evento));
        verify(repositorioEventoMock).buscarEventosPorProvinciaYNombre("Buenos Aires", "Evento");
    }

    @Test
    public void dadoQueSeBuscanEventosPorProvinciaYCiudadDevuelveEventosFiltrados() {
        Evento evento = new Evento();
        List<Evento> eventos = Arrays.asList(evento);

        when(repositorioEventoMock.buscarEventosPorCiudad("Morón")).thenReturn(eventos);

        List<Evento> eventosFiltrados = servicioEvento.filtrarEventos(null, "Buenos Aires", "Morón");

        assertThat(eventosFiltrados.size(), is(1));
        assertThat(eventosFiltrados.get(0), equalTo(evento));
        verify(repositorioEventoMock).buscarEventosPorCiudad("Morón");
    }

    @Test
    public void dadoQueSeBuscanEventosPorProvinciaDevuelveEventosFiltrados() {
        Evento evento = new Evento();
        List<Evento> eventos = Arrays.asList(evento);

        when(repositorioEventoMock.buscarEventosPorProvincia("Buenos Aires")).thenReturn(eventos);

        List<Evento> eventosFiltrados = servicioEvento.filtrarEventos(null, "Buenos Aires", null);

        assertThat(eventosFiltrados.size(), is(1));
        assertThat(eventosFiltrados.get(0), equalTo(evento));
        verify(repositorioEventoMock).buscarEventosPorProvincia("Buenos Aires");
    }
    
    @Test
    public void dadoQueExistenEventosPodemosObtenerlosPorSuId() {
        Evento evento = new Evento();
        evento.setId(1L);

        when(this.repositorioEventoMock.obtenerEventoPorId(1L)).thenReturn(evento);
        Evento eventoObtenido = this.servicioEvento.obtenerEventoPorId(1L);

        verify(this.repositorioEventoMock, times(1)).obtenerEventoPorId(1L);
        assertThat(eventoObtenido.getId(), is(1L));
        assertThat(eventoObtenido, equalTo(evento));
    }


    @Test
    public void dadoQueExistenTresEventosConDistintasFechaPodemosObtenerLosEventosOrdenadosPorFechaDeLaMasCercanaALaMasDistante() {


        Evento eventoMock1 = new Evento("Fiesta de Verano", LocalDate.of(2025, 1, 15), "Parque de la Ciudad");
        Evento eventoMock2 = new Evento("Festival de Invierno", LocalDate.of(2024, 12, 25), "CABA");
        Evento eventoMock3 = new Evento("Primavera Sound", LocalDate.of(2024, 11, 16), "Parque Central");

        List<Evento> eventosOrdenadosMock = List.of(eventoMock3, eventoMock2, eventoMock1);

        when(this.repositorioEventoMock.obtenerEventosOrdenadosPorFecha()).thenReturn(eventosOrdenadosMock);

        List<Evento> eventosObtenidos = this.servicioEvento.obtenerEventosOrdenadosPorFecha();

        seVerificaElOrdenDeLosMismosComparandoSusFechas(eventosObtenidos);

    }

    private void seVerificaElOrdenDeLosMismosComparandoSusFechas(List<Evento> eventosOrdenados) {
        assertThat(eventosOrdenados.size(), equalTo(3));
        assertThat(eventosOrdenados.get(0).getFecha(), equalTo(LocalDate.of(2024, 11, 16)));
        assertThat(eventosOrdenados.get(1).getFecha(), equalTo(LocalDate.of(2024, 12, 25)));
        assertThat(eventosOrdenados.get(2).getFecha(), equalTo(LocalDate.of(2025, 1, 15)));
    }

    @Test
    public void dadoQueExistenTresEventosConDistintasFechaPodemosObtenerLosEventosQueSeRealizanEnUnRangoDeFechasDado() {
        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        LocalDate fechaFin = LocalDate.of(2024, 12, 31);

        Evento eventoMock1 = new Evento("Fiesta de Verano", LocalDate.of(2025, 1, 15), "Parque de la Ciudad");
        Evento eventoMock2 = new Evento("Festival de Invierno", LocalDate.of(2024, 12, 25), "CABA");
        Evento eventoMock3 = new Evento("Primavera Sound", LocalDate.of(2024, 11, 16), "Parque Central");

        List<Evento> eventosEsperadosMock = List.of(eventoMock3, eventoMock2);

        when(this.repositorioEventoMock.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio, fechaFin)).thenReturn(eventosEsperadosMock);

        List<Evento> eventosObtenidos = this.servicioEvento.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio, fechaFin);

        seVerificaQueSoloTraigaLosEventosDentroDelRangoDeFechasDado(eventosObtenidos);

    }

    private void seVerificaQueSoloTraigaLosEventosDentroDelRangoDeFechasDado(List<Evento> eventosObtenidos) {
        assertThat(eventosObtenidos.size(), equalTo(2));
        assertThat(eventosObtenidos.get(0).getFecha(), equalTo(LocalDate.of(2024, 11, 16)));
        assertThat(eventosObtenidos.get(1).getFecha(), equalTo(LocalDate.of(2024, 12, 25)));
    }


}
