package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;

import com.tallerwebi.dominio.ServicioEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorEventoTest {
    private ControladorEvento controladorEvento;
    private ServicioEvento servicioEventoMock;

    @BeforeEach
    public void init(){
        servicioEventoMock = mock(ServicioEvento.class);
        controladorEvento = new ControladorEvento(servicioEventoMock);
    }
@Test
public void debeRetornarLaVistaEventosCuandoSeEjecutaElMetodoMostrarEventos() {

    //Contrastacion
    ModelAndView modelAndView = controladorEvento.mostrarEventos();


    //Verificacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("eventos"));
}

}
