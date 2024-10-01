package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.ServicioEventoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorEventoTest {
    private ControladorEvento controladorEvento;
    private ServicioEventoImpl servicioEventoMock;
    private ServicioEntradaImpl servicioEntradaMock;


    @BeforeEach
    public void init(){
        this.servicioEventoMock = mock(ServicioEventoImpl.class);
        this.controladorEvento = new ControladorEvento(servicioEventoMock, servicioEntradaMock);
    }

    @Test
    public void debeRetornarLaVistaEventosCuandoSeEjecutaElMetodoMostrarEventos() {
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos(null, null, null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseTodosLosEventosCuandoNoSeUtilizaElBuscador() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(servicioEventoMock.obtenerTodosLosEventos()).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos(null, null, null);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).obtenerTodosLosEventos();
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosQueCorrespondanALaBusquedaCuandoSeUtilizaElBuscador() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del nuevo metodo filtrarEventos
        when(servicioEventoMock.filtrarEventos("creamfields", null, null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos("creamfields", null, null);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1)); // Comprobar que contiene 1 elemento
        verify(servicioEventoMock, times(1)).filtrarEventos("creamfields", null, null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseTodosLosEventosCuandoElBuscadorEstaVacio () {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(servicioEventoMock.obtenerTodosLosEventos()).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos("", "", "");

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, is(not(empty())));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).obtenerTodosLosEventos();
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeIngresanLosParametrosNombreProvinciaYCiudad() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con nombre, provincia y ciudad
        when(servicioEventoMock.filtrarEventos("creamfields", "Buenos Aires", "Morón")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos("creamfields", "Buenos Aires", "Morón");

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).filtrarEventos("creamfields", "Buenos Aires", "Morón");
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeIngresanLosParametrosNombreYProvincia() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con nombre y provincia
        when(servicioEventoMock.filtrarEventos("creamfields", "Buenos Aires", null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos("creamfields", "Buenos Aires", null);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).filtrarEventos("creamfields", "Buenos Aires", null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeIngresanLosParametrosProviciaYCiudad() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con provincia y ciudad
        when(servicioEventoMock.filtrarEventos(null, "Buenos Aires", "Morón")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos(null, "Buenos Aires", "Morón");

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).filtrarEventos(null, "Buenos Aires", "Morón");
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeBuscaPorProvincia() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con provincia
        when(servicioEventoMock.filtrarEventos(null, "Buenos Aires", null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos(null, "Buenos Aires", null);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).filtrarEventos(null, "Buenos Aires", null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }


    @Test
    public void debeRetornarLaVistaDetalleDeUnEventoCuandoSePresionaEseEventoEnParticular () {
        ModelAndView modelAndView = controladorEvento.mostrarVistas(1L);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("vista"));
    }


    @Test
    public void debenRetornarEventosDeConciertoAlPresionarElBotonDeConcierto(){
        List<Evento> listaDeEventos = new ArrayList<>();

        Evento evento = new Evento();
        evento.setCategoria("Concierto");
        Evento evento2 = new Evento();
        evento2.setCategoria("Concierto");
        Evento evento3 = new Evento();
        evento3.setCategoria("otro");

        listaDeEventos.add(evento);
        listaDeEventos.add(evento2);


        when(servicioEventoMock.obtenerEventosPorCategoria("Concierto")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarEventosFiltradosPorCategoria("Concierto");

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");

        assertThat(eventos, hasSize(2));
        assertThat(eventos.get(0).getCategoria(), is(equalToIgnoringCase("concierto")));
    }

    @Test
    public void debenObtenerseTodosLosEventosOrdenadosPorFecha() {
        Evento eventoMock1 = new Evento("Fiesta de Verano", LocalDate.of(2025, 1, 15), "Parque de la Ciudad");
        Evento eventoMock2 = new Evento("Festival de Invierno", LocalDate.of(2024, 12, 25), "CABA");
        Evento eventoMock3 = new Evento("Primavera Sound", LocalDate.of(2024, 11, 16), "Parque Central");

        List<Evento> eventosMock = Arrays.asList(eventoMock3, eventoMock2, eventoMock1);

        when(servicioEventoMock.obtenerEventosOrdenadosPorFecha()).thenReturn(eventosMock);

        List<Evento> eventosObtenidos = this.controladorEvento.obtenerEventosOrdenadosPorFecha();

        seVerificaElOrdenDeLosMismosComparandoSusFechas(eventosObtenidos);

        verify(servicioEventoMock, times(1)).obtenerEventosOrdenadosPorFecha();
    }
  
    private void seVerificaElOrdenDeLosMismosComparandoSusFechas(List<Evento> eventosOrdenados) {
        assertThat(eventosOrdenados.size(), equalTo(3));
        assertThat(eventosOrdenados.get(0).getFecha(), equalTo(LocalDate.of(2024, 11, 16)));
        assertThat(eventosOrdenados.get(1).getFecha(), equalTo(LocalDate.of(2024, 12, 25)));
        assertThat(eventosOrdenados.get(2).getFecha(), equalTo(LocalDate.of(2025, 1, 15)));
    }
  
    @Test
    public void debenObtenerseTodosLosEventosDentroDeUnRangoDeFechasDado() {
        // PARA IMPLEMTAR USAR:
        //LocalDate fechaInicio1 = LocalDate.now();    //FECHA ACTUAL
        //LocalDate fechaFin2 = fechaInicio1.plusMonths(2); //DOS MESES POST FECHA ACTUAL

        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        LocalDate fechaFin = LocalDate.of(2024, 12, 31);

        Evento eventoMock1 = new Evento("Fiesta de Verano", LocalDate.of(2025, 1, 15), "Parque de la Ciudad");
        Evento eventoMock2 = new Evento("Festival de Invierno", LocalDate.of(2024, 12, 25), "CABA");
        Evento eventoMock3 = new Evento("Primavera Sound", LocalDate.of(2024, 11, 16), "Parque Central");

        List<Evento> eventosMock = Arrays.asList(eventoMock3, eventoMock2);

        when(servicioEventoMock.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin)).thenReturn(eventosMock);

        List<Evento> eventosObtenidos = this.controladorEvento.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin);

        seVerificaQueSoloEstenLosEventosCuyasFechasCumplanConElRequerimiento(eventosObtenidos);

        verify(servicioEventoMock, times(1)).obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin);
    }
  
    private void seVerificaQueSoloEstenLosEventosCuyasFechasCumplanConElRequerimiento(List<Evento> eventosOrdenados) {
        assertThat(eventosOrdenados.size(), equalTo(2));
        assertThat(eventosOrdenados.get(0).getFecha(), equalTo(LocalDate.of(2024, 11, 16)));
        assertThat(eventosOrdenados.get(1).getFecha(), equalTo(LocalDate.of(2024, 12, 25)));
    }

}



