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
    private ServicioEventoImpl servicioEventoMock;

    @BeforeEach
    public void init(){
        this.servicioEventoMock = mock(ServicioEventoImpl.class);
        this.controladorEvento = new ControladorEvento(servicioEventoMock);
    }

    @Test
    public void debeRetornarLaVistaEventosCuandoSeEjecutaElMetodoMostrarEventos() {
        ModelAndView modelAndView = controladorEvento.mostrarEventos(null);
       assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
    }

    @Test
    public void testMostrarEventosSinNombre() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(servicioEventoMock.obtenerTodosLosEventos()).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarEventos(null);

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1));
        verify(servicioEventoMock, times(1)).obtenerTodosLosEventos();
    }

    @Test
    public void testMostrarEventosConNombre() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(servicioEventoMock.buscarEventosPorNombre("creamfields")).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarEventos("creamfields");

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, not(empty()));
        assertThat(eventos, hasSize(1)); // Comprobar que contiene 1 elemento
        verify(servicioEventoMock, times(1)).buscarEventosPorNombre("creamfields");
    }

    @Test
    public void testMostrarEventosNombreVacio() {
        List<Evento> listaDeEventos = new ArrayList<>();
        listaDeEventos.add(new Evento());

        when(servicioEventoMock.obtenerTodosLosEventos()).thenReturn(listaDeEventos);
        ModelAndView modelAndView = controladorEvento.mostrarEventos("");

        List<Evento> eventos = (List<Evento>) modelAndView.getModel().get("eventos");
        assertThat(eventos, is(not(empty())));
        assertThat(eventos, hasSize(1)); // Comprobar que contiene 1 elemento
        verify(servicioEventoMock, times(1)).obtenerTodosLosEventos();
    }
    
    @Test
    public void debeRetornarLaVistaDetalleDeUnEventoCuandoSePresionaEseEventoEnParticular(){
        ModelAndView modelAndView = controladorEvento.mostrarVistas(1L);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("vista"));
    }
}
