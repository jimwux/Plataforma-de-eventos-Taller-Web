package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRegistro;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorRegistroTest {

    private ControladorRegistro controladorRegistro;
    private ServicioRegistro servicioRegistro;
    private Usuario usuario;
    @BeforeEach
    public void init(){
        servicioRegistro = mock(ServicioRegistro.class);
        controladorRegistro = new ControladorRegistro(servicioRegistro);
        usuario = mock(Usuario.class);
    }

    @Test
    public void verificarRegistroExitoso() throws UsuarioExistente {
        Usuario usuario = new Usuario("a@gmail.com", "12345", "brian", "hidalgo", "1123895568", "23895568");

        when(servicioRegistro.registrar(usuario)).thenReturn("Registro Exitoso");

        String resultado = controladorRegistro.registrar(usuario);
        assertThat(resultado, equalTo("Registro Exitoso"));
    }

    @Test
    public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente {
        // preparacion
        Usuario usuario = new Usuario("a@gmail.com", "12345", "brian", "hidalgo", "1123895568", "23895568");

        // ejecucion
        ModelAndView modelAndView = controladorRegistro.registrarme(usuario);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }


    @Test
    public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
        // preparacion
        Usuario usuario = new Usuario("a@gmail.com", "12345", "brian", "hidalgo", "1123895568", "23895568");

        doThrow(RuntimeException.class).when(servicioRegistro).registrar(usuario);

        // ejecucion
        ModelAndView modelAndView = controladorRegistro.registrarme(usuario);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
    }

    @Test
    public void registrarseConUsuarioNuloDebeVolverAFormularioYMostrarError() throws UsuarioExistente {
        // preparacion
        Usuario usuario = null;

        // ejecucion
        ModelAndView modelAndView = controladorRegistro.registrarme(usuario);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario no puede ser nulo"));

    }


    @Test
    public void verificarSiElEmailExisteEntoncesDebeVolverAlFormularioYMostrarError() throws UsuarioExistente {
        // preparacion
        Usuario usuario = new Usuario("a@gmail.com", "12345", "brian", "hidalgo", "1123895568", "23895568");

        doThrow(new UsuarioExistente("Email existente")).when(servicioRegistro).registrar(usuario);

        // ejecucion
        ModelAndView modelAndView = controladorRegistro.registrarme(usuario);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El email ya existe"));
    }

    @Test
    public void alRegistrarseConUnEmailInvalidoDebeVolverAlFormularioYMostrarError() throws UsuarioExistente {
        // preparacion
        Usuario usuario = new Usuario("email-invalido", "12345", "brian", "hidalgo", "1123895568", "23895568");

        // ejecucion
        ModelAndView modelAndView = controladorRegistro.registrarme(usuario);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El email no es valido"));
    }
}
