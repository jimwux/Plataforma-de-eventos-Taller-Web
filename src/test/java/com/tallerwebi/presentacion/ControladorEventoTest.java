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
import java.util.ArrayList;
import java.util.List;

public class ControladorEventoTest {
    private ControladorEvento controladorEvento;
    private ServicioEvento servicioEventoMock;


    @BeforeEach
    public void init() {
        this.servicioEventoMock = mock(ServicioEvento.class);
        this.controladorEvento = new ControladorEvento(servicioEventoMock);
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
}