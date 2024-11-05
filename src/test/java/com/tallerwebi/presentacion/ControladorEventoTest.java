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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControladorEventoTest {
    private ControladorEvento controladorEvento;
    private ServicioEventoImpl servicioEventoMock;
    private ServicioEntradaImpl servicioEntradaMock;
    private HttpServletRequest request;

    @BeforeEach
    public void init(){
        this.servicioEventoMock = mock(ServicioEventoImpl.class);
        this.controladorEvento = new ControladorEvento(servicioEventoMock, servicioEntradaMock);
        this.request = mock(HttpServletRequest.class);
    }

    @Test
    public void debeRetornarLaVistaEventosCuandoSeEjecutaElMetodoMostrarEventos() {
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos(null, null, null, null, request);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseTodosLosEventosOrdenadosPorFechaCuandoNoSeUtilizaElBuscador() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(this.servicioEventoMock.obtenerEventosOrdenadosPorFecha()).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos(null, null, null, null, request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(this.servicioEventoMock, times(1)).obtenerEventosOrdenadosPorFecha();
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosQueCorrespondanALaBusquedaCuandoSeUtilizaElBuscador() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del nuevo metodo filtrarEventos
        when(this.servicioEventoMock.filtrarEventos("creamfields", null, null, null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos("creamfields", null, null, null, request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1)); // Comprobar que contiene 1 elemento
        verify(this.servicioEventoMock, times(1)).filtrarEventos("creamfields", null, null, null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseTodosLosEventosOrdenadosPorFechaCuandoElBuscadorEstaVacio () {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(this.servicioEventoMock.obtenerEventosOrdenadosPorFecha()).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos("", "", "", "", request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, is(not(empty())));
        assertThat(eventos, hasSize(1));
        verify(this.servicioEventoMock, times(1)).obtenerEventosOrdenadosPorFecha();
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeIngresanLosParametrosNombreProvinciaYCiudad() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con nombre, provincia y ciudad
        when(servicioEventoMock.filtrarEventos("creamfields", "Buenos Aires", "Morón", null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarVistaEventos("creamfields", "Buenos Aires", "Morón", null, request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).filtrarEventos("creamfields", "Buenos Aires", "Morón", null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeIngresanLosParametrosNombreYProvincia() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con nombre y provincia
        when(this.servicioEventoMock.filtrarEventos("creamfields", "Buenos Aires", null, null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos("creamfields", "Buenos Aires", null, null, request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(this.servicioEventoMock, times(1)).filtrarEventos("creamfields", "Buenos Aires", null, null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeBuscaPorNombreEnElBuscadorYSeSeleccionaUnaCategoria() {
        List<Evento> listaDeEventos = new ArrayList<>();

        Evento evento = new Evento();
        evento.setNombre("Fiesta privada");
        evento.setCategoria("fiesta");

        listaDeEventos.add(evento);

        when(this.servicioEventoMock.filtrarEventos("F", null, null, "fiesta")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos("F", null, null, "fiesta", request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeSeleccionaUnaProvinciaYUnaCategoria() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(this.servicioEventoMock.filtrarEventos(null, "Santa Fe", null, "deporte")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos(null, "Santa Fe", null, "deporte", request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(this.servicioEventoMock, times(1)).filtrarEventos(null, "Santa Fe", null, "deporte");
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeSeleccionaUnaProvinciaCiudadYUnaCategoria() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(this.servicioEventoMock.filtrarEventos(null, "Buenos Aires", "La Plata", "deporte")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos(null, "Buenos Aires", "La Plata", "deporte", request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(this.servicioEventoMock, times(1)).filtrarEventos(null, "Buenos Aires", "La Plata", "deporte");
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeBuscaPorNombreEnElBuscadorYSeSeleccionaUnaProvinciaYUnaCategoria() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(this.servicioEventoMock.filtrarEventos("Wasabi", "Buenos Aires", null, "fiesta")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos("Wasabi", "Buenos Aires", null, "fiesta", request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
    }

    @Test
    public void debenObtenerLosEventosFiltradosPorBusquedaDeNombreYPorSeleccionDeProvinciaCiudadYCategoria() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(this.servicioEventoMock.filtrarEventos("Wasabi", "Buenos Aires", "Capital", "fiesta")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos("Wasabi", "Buenos Aires", "Capital", "fiesta", request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
    }




    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeIngresanLosParametrosProviciaYCiudad() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con provincia y ciudad
        when(this.servicioEventoMock.filtrarEventos(null, "Buenos Aires", "Morón", null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos(null, "Buenos Aires", "Morón", null, request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(this.servicioEventoMock, times(1)).filtrarEventos(null, "Buenos Aires", "Morón", null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void debenObtenerseLosEventosCorrespondientesCuandoSeBuscaPorProvincia() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        // Mock del metodo filtrarEventos con provincia
        when(this.servicioEventoMock.filtrarEventos(null, "Buenos Aires", null, null)).thenReturn(listaDeEventos);
        ModelAndView modelAndView = this.controladorEvento.mostrarVistaEventos(null, "Buenos Aires", null, null, request);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(this.servicioEventoMock, times(1)).filtrarEventos(null, "Buenos Aires", null, null);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }


    @Test
    public void debeRetornarLaVistaDetalleDeUnEventoCuandoSePresionaEseEventoEnParticular () {
        ModelAndView modelAndView = this.controladorEvento.mostrarVistas(1L);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("vista"));
    }




    @Test
    public void debenObtenerseTodosLosEventosOrdenadosPorFecha() {
        Evento eventoMock1 = new Evento("Fiesta de Verano", LocalDate.of(2025, 1, 15), "Parque de la Ciudad");
        Evento eventoMock2 = new Evento("Festival de Invierno", LocalDate.of(2024, 12, 25), "CABA");
        Evento eventoMock3 = new Evento("Primavera Sound", LocalDate.of(2024, 11, 16), "Parque Central");

        List<Evento> eventosMock = Arrays.asList(eventoMock3, eventoMock2, eventoMock1);

        when(this.servicioEventoMock.obtenerEventosOrdenadosPorFecha()).thenReturn(eventosMock);

        List<Evento> eventosObtenidos = this.controladorEvento.obtenerEventosOrdenadosPorFecha();

        seVerificaElOrdenDeLosMismosComparandoSusFechas(eventosObtenidos);

        verify(this.servicioEventoMock, times(1)).obtenerEventosOrdenadosPorFecha();
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

        when(this.servicioEventoMock.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin)).thenReturn(eventosMock);

        List<Evento> eventosObtenidos = this.controladorEvento.obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin);

        seVerificaQueSoloEstenLosEventosCuyasFechasCumplanConElRequerimiento(eventosObtenidos);

        verify(this.servicioEventoMock, times(1)).obtenerEventosDentroDeUnRangoDeFechas(fechaInicio,fechaFin);
    }
  
    private void seVerificaQueSoloEstenLosEventosCuyasFechasCumplanConElRequerimiento(List<Evento> eventosOrdenados) {
        assertThat(eventosOrdenados.size(), equalTo(2));
        assertThat(eventosOrdenados.get(0).getFecha(), equalTo(LocalDate.of(2024, 11, 16)));
        assertThat(eventosOrdenados.get(1).getFecha(), equalTo(LocalDate.of(2024, 12, 25)));
    }

}



