package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorUsuarioTest {

    private ControladorUsuario controladorUsuario;
    private Usuario usuarioMock;
    private UsuarioVistaDTO usuarioVistaDTO;
    private ServicioUsuario servicioUsuarioMock;
    private HttpSession session;
    private HttpServletRequest request;

    @BeforeEach
    public void init() {
        usuarioMock = mock(Usuario.class);
        usuarioVistaDTO = new UsuarioVistaDTO(usuarioMock);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controladorUsuario = new ControladorUsuario(servicioUsuarioMock);
        session = Mockito.mock(HttpSession.class);
        request = Mockito.mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
    }
    @Test
    public void dadoUnUsuarioDTOMockEsteTieneQueEstarCargadoCorrectamenteEnLaVistaUsuario() {

        String email = "a@gmail.com";
        Usuario usuario = new Usuario(email, "123", "Jose", "Lopez", "123456789", "12345678");
        UsuarioVistaDTO usuarioVistaDTO = new UsuarioVistaDTO(usuario);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn(email);
        when(servicioUsuarioMock.obtenerUsuarioVistaDTODelRepo(email)).thenReturn(usuario);

        ModelAndView modelAndView = controladorUsuario.mostrarVistaUsuario(request);


        assertThat(modelAndView.getViewName(), equalTo("usuario"));
        ModelMap modelo = modelAndView.getModelMap();
        assertThat(modelo.containsKey("usuarioVistaDTO"), is(Boolean.TRUE));
        assertThat(modelo.get("usuarioVistaDTO"), equalTo(usuarioVistaDTO));
    }
    @Test
    public void sinHaberUnUsuarioDTOMockCargadoElModelAndViewDeLaVistaUsuarioDebeEstarVacio() {

        when(request.getSession()).thenReturn(session);

        ModelAndView modelAndView = controladorUsuario.mostrarVistaUsuario(request);


        assertThat(modelAndView.getViewName(), equalTo("usuario"));
        ModelMap modelo = modelAndView.getModelMap();
        assertThat(modelo.containsKey("usuarioVistaDTO"), is(Boolean.FALSE));
    }

    @Test
    public void dadoQueHayUnaSesionInciadaEstaSePuedaCerrar(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = request.getSession();
        session.setAttribute("ID", 1L);

        controladorUsuario.cerrarSesion(request);
        // Intenta obtener la sesión
        HttpSession actualSession = request.getSession(false);
        assertThat(actualSession, equalTo(null));
    }
    @Test
    public void dadoQueHayUnaSesionIniciadaEstaSePuedaEliminar(){
        // Configurar el request sin sesión
        when(request.getSession(false)).thenReturn(null);

        // Ejecutar el método
        String vistaRedirigida = controladorUsuario.eliminarCuenta(request);

        // Verificar que el servicio no fue llamado
        verify(servicioUsuarioMock, never()).eliminarCuentaUsuario(any());

        // Verificar la redirección al login
        assertThat(vistaRedirigida, equalTo("redirect:/login"));
    }

    @Test
    public void dadoQueUnUsuarioHayaCambiadoAlgunDatoEnLaVistaEsteSeActualice() {
        //preparacion
        String email = "a@gmail.com";
        Usuario usuario = new Usuario(email, "123456", "Maria", "Perez", "12345678", "11223344");
        String campoQueQuieroModificar = "dni";
        String nuevoValor = "45400400";
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn(email);
        when(servicioUsuarioMock.obtenerUsuarioVistaDTODelRepo(email)).thenReturn(usuario);
        //ejecurion
        ModelAndView modelAndView = controladorUsuario.modificarUsuario(campoQueQuieroModificar, nuevoValor, request);
        //verificacion
        assertThat(modelAndView.getViewName(), equalTo("redirect:/usuario"));
        verify(servicioUsuarioMock).actualizarDatoUsuario(email, campoQueQuieroModificar, nuevoValor);
    }


}
