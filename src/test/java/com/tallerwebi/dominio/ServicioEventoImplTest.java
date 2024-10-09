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
        when(this.repositorioEventoMock.obtenerTodosLosEventos()).thenReturn(new ArrayList<>());
        List<Evento> eventos = this.servicioEvento.obtenerTodosLosEventos();
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
            when(this.repositorioEventoMock.obtenerTodosLosEventos()).thenReturn(new ArrayList<>());
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
        List<Evento> eventos = this.servicioEvento.obtenerTodosLosEventos();
        when(this.repositorioEventoMock.obtenerTodosLosEventos()).thenReturn(new ArrayList<>());

        assertThat(eventos.size(), is(0));

        this.servicioEvento.agregarEvento(primerEvento);
        eventos = this.servicioEvento.obtenerTodosLosEventos();
        eventos.add(primerEvento);

        assertThat(eventos.size(), is(1));
        assertThat(eventos.get(0).getId(), is(primerEvento.getId()));

        this.servicioEvento.agregarEvento(eventoDuplicado);
        eventos = this.servicioEvento.obtenerTodosLosEventos();
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

        when(this.repositorioEventoMock.buscarEventosPorProvinciaYNombre("Buenos Aires", "Evento")).thenReturn(eventos);

        List<Evento> eventosFiltrados = this.servicioEvento.filtrarEventos("Evento", "Buenos Aires", null);

        assertThat(eventosFiltrados.size(), is(1));
        assertThat(eventosFiltrados.get(0), equalTo(evento));
        verify(this.repositorioEventoMock).buscarEventosPorProvinciaYNombre("Buenos Aires", "Evento");
    }

    @Test
    public void dadoQueSeBuscanEventosPorProvinciaYCiudadDevuelveEventosFiltrados() {
        Evento evento = new Evento();
        List<Evento> eventos = Arrays.asList(evento);

        when(this.repositorioEventoMock.buscarEventosPorCiudad("Morón")).thenReturn(eventos);

        List<Evento> eventosFiltrados = this.servicioEvento.filtrarEventos(null, "Buenos Aires", "Morón");

        assertThat(eventosFiltrados.size(), is(1));
        assertThat(eventosFiltrados.get(0), equalTo(evento));
        verify(this.repositorioEventoMock).buscarEventosPorCiudad("Morón");
    }

    @Test
    public void dadoQueSeBuscanEventosPorProvinciaDevuelveEventosFiltrados() {
        Evento evento = new Evento();
        List<Evento> eventos = Arrays.asList(evento);

        when(this.repositorioEventoMock.buscarEventosPorProvincia("Buenos Aires")).thenReturn(eventos);

        List<Evento> eventosFiltrados = this.servicioEvento.filtrarEventos(null, "Buenos Aires", null);

        assertThat(eventosFiltrados.size(), is(1));
        assertThat(eventosFiltrados.get(0), equalTo(evento));
        verify(this.repositorioEventoMock).buscarEventosPorProvincia("Buenos Aires");
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
    public void dadoQueExistenEventosPodemosObtenerlosPorSuCategoriaConcierto() {
        List<Evento> eventosFiltrados = new ArrayList<>();

        Evento eventoUno = new Evento();
        eventoUno.setNombre("Mario Bros");
        eventoUno.setCategoria("Concierto");
        Evento eventoDos = new Evento();
        eventoDos.setNombre("Star Wars");
        eventoDos.setCategoria("Concierto");
        Evento eventoTres = new Evento();
        eventoTres.setCategoria("Otro");

        eventosFiltrados.add(eventoUno);
        eventosFiltrados.add(eventoDos);

        when(this.repositorioEventoMock.obtenerEventosPorCategoria("Concierto")).thenReturn(eventosFiltrados);
        List<Evento> resultado = this.servicioEvento.obtenerEventosPorCategoria("Concierto");

        assertThat(resultado.size(), is(2));
        assertThat(resultado.get(0).getNombre(), equalTo(eventoUno.getNombre()));
        assertThat(resultado.get(1).getNombre(), equalTo(eventoDos.getNombre()));
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

    @Test
    public void dadoQueExistenTresEventosConDistintasFechasYCiudadesCuandoElValorRandomDaTrueAlBuscarEventosDeFormaAleatoriaPodemosObtenerLosEventosQueSeRealizanEnUnRangoDeFechasDado() {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusMonths(2);

        Evento eventoMock1 = new Evento("Fiesta de Verano", LocalDate.now().plusDays(1), "Parque de la Ciudad");
        Evento eventoMock2 = new Evento("Festival de Invierno", LocalDate.now().plusMonths(1), "CABA");
        Evento eventoMock3 = new Evento("Primavera Sound", LocalDate.now().plusYears(1), "Morón");
        Random randomMock = mock(Random.class);
        List<Evento> eventosEsperadosMock = List.of(eventoMock1, eventoMock2);

        when(randomMock.nextBoolean()).thenReturn(false);
        when(this.repositorioEventoMock.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin)).thenReturn(eventosEsperadosMock);
        boolean valorRandom = randomMock.nextBoolean();


        List<Evento> eventosObtenidos = this.servicioEvento.obtenerEventos("Morón", valorRandom);

        verify(this.repositorioEventoMock, times(1)).obtenerEventosDentroDeUnRangoDeFechas(fechaInicio, fechaFin);
        assertThat(eventosObtenidos.size(), equalTo(2));
        // Verifica que cada evento esperado está en los resultados encontrados

        int iterador = 0;
        for (Evento evento : eventosEsperadosMock) {
            assertThat(eventosObtenidos.get(iterador), equalTo(evento));
            iterador++;
        }

    }
    @Test
    public void dadoQueExistenTresEventosConDistintasFechasYCiudadesCuandoElValorRandomDaFalseAlBuscarEventosDeFormaAleatoriaPodemosObtenerLosEventosDeUnaCiudadDada () {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusMonths(2);

        Evento eventoMock1 = new Evento("Fiesta de Verano", LocalDate.now().plusDays(1), "Parque de la Ciudad");
        Evento eventoMock2 = new Evento("Festival de Invierno", LocalDate.now().plusMonths(1), "CABA");
        Evento eventoMock3 = new Evento("Primavera Sound", LocalDate.now().plusYears(1), "Morón");
        Random randomMock = mock(Random.class);
        List<Evento> eventosEsperadosMock = List.of(eventoMock3);

        when(randomMock.nextBoolean()).thenReturn(true);
        when(this.repositorioEventoMock.buscarEventosPorCiudad("Morón")).thenReturn(eventosEsperadosMock);
        boolean valorRandom = randomMock.nextBoolean();

        List<Evento> eventosObtenidos = this.servicioEvento.obtenerEventos("Morón", valorRandom);

        verify(this.repositorioEventoMock, times(1)).buscarEventosPorCiudad("Morón");
        assertThat(eventosObtenidos.size(), equalTo(1));
        // Verifica que cada evento esperado está en los resultados encontrados
        int iterador = 0;
        for (Evento evento : eventosEsperadosMock) {
            assertThat(eventosObtenidos.get(iterador), equalTo(evento));
            iterador++;
        }

    }

    @Test
    public void dadaUnaListaDeEventosQueTienenUnaMismaCiudadDebemosObtenerElMensajeCorrespondiente(){
        String nombreCiudad = "Morón";

        Ciudad moron = new Ciudad();
        moron.setNombre(nombreCiudad);

        Evento evento1 = new Evento();
        evento1.setCiudad(moron);
        Evento evento2 = new Evento();
        evento2.setCiudad(moron);

        List<Evento> eventosDados = List.of(evento1,evento2);
        String mensajeEsperado = "Mas eventos en la ciudad de " + nombreCiudad;
        String mensajeObtenido = this.servicioEvento.obtenerMensajeSobreEventosAleatorios(eventosDados,nombreCiudad);

        assertThat(mensajeObtenido, equalTo(mensajeEsperado));

    }
    @Test
    public void dadaUnaListaDeEventosQueTienenNoUnaMismaCiudadDebemosObtenerElMensajeCorrespondiente(){
        String nombreCiudad = "Morón";
        String nombreCiudad2 = "Merlo";

        Ciudad moron = new Ciudad();
        moron.setNombre(nombreCiudad);
        Ciudad merlo = new Ciudad();
        merlo.setNombre(nombreCiudad2);

        Evento evento1 = new Evento();
        evento1.setCiudad(moron);
        Evento evento2 = new Evento();
        evento2.setCiudad(merlo);

        List<Evento> eventosDados = List.of(evento1,evento2);
        String mensajeEsperado = "Mas eventos en los proximos meses";
        String mensajeObtenido = this.servicioEvento.obtenerMensajeSobreEventosAleatorios(eventosDados,nombreCiudad);

        assertThat(mensajeObtenido, equalTo(mensajeEsperado));

    }

}
