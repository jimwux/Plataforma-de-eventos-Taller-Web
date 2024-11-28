package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCiudad;
import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.presentacion.dto.EmailContactoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorContactoTest {

    private ControladorContacto controladorContacto;
    private ServicioEmail servicioEmailMock;
    private Model model;

    @BeforeEach
    public void init(){
        this.servicioEmailMock = mock(ServicioEmail.class);
        this.controladorContacto = new ControladorContacto(servicioEmailMock);
        this.model = new ExtendedModelMap();
    }

    @Test
    void mostrarFormularioContactodeberiaRetornarVistaYModeloInicializado() {
        ModelAndView modelAndView = controladorContacto.mostrarFormularioContacto();

        assertThat(modelAndView.getModel().get("formularioContacto"), notNullValue());
        assertThat(modelAndView.getViewName(), equalTo("contacto"));
    }

    @Test
    void alProcesarFormularioContactConDatosValidosDeberEnviarElCorreoCorrectamente() {
        EmailContactoDTO formulario = new EmailContactoDTO();
        formulario.setNombre("Juan");
        formulario.setApellido("Pérez");
        formulario.setEmail("juan.perez@ejemplo.com");
        formulario.setAsunto("Consulta");
        formulario.setMensaje("Este es un mensaje de prueba");

        doNothing().when(this.servicioEmailMock).enviarMensajeDeContacto(
                formulario.getNombre(), formulario.getApellido(),
                formulario.getEmail(), formulario.getAsunto(),
                formulario.getMensaje()
        );

        String vista = controladorContacto.procesarFormularioContacto(formulario, model);

        verify(servicioEmailMock, times(1)).enviarMensajeDeContacto(
                formulario.getNombre(), formulario.getApellido(),
                formulario.getEmail(), formulario.getAsunto(),
                formulario.getMensaje()
        );

        assertThat(vista, equalTo("contacto"));
        assertThat(model.getAttribute("mensajeExito"), notNullValue());
    }

    @Test
    void alOcurrirUnaExcepcionCuandoSeIntentaEnviarUnCorreoDebeRetornarError() {
        EmailContactoDTO formulario = new EmailContactoDTO();
        formulario.setNombre("Juan");
        formulario.setApellido("Pérez");
        formulario.setEmail("juan.perez@ejemplo.com");
        formulario.setAsunto("Consulta");
        formulario.setMensaje("Este es un mensaje de prueba");

        doThrow(new RuntimeException("Error al enviar correo"))
                .when(this.servicioEmailMock).enviarMensajeDeContacto(
                        formulario.getNombre(), formulario.getApellido(),
                        formulario.getEmail(), formulario.getAsunto(),
                        formulario.getMensaje()
                );

        String vista = controladorContacto.procesarFormularioContacto(formulario, model);

        verify(this.servicioEmailMock, Mockito.times(1)).enviarMensajeDeContacto(
                formulario.getNombre(), formulario.getApellido(),
                formulario.getEmail(), formulario.getAsunto(),
                formulario.getMensaje()
        );

        assertThat(vista, equalTo("contacto"));
        assertThat(model.getAttribute("mensajeError"), notNullValue());
    }




}
