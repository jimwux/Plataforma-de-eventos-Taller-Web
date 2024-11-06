package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;


public class ControladorEntradaUsuarioTest {


    private ServicioEntradaUsuarioImpl servicioEntradaUsuarioMock;
    private ControladorEntradaUsuario controladorEntradaUsuario;
    private ServicioUsuarioImpl servicioUsuarioMock;

    private MockHttpServletRequest request;
    private MockHttpSession session;

    @BeforeEach
    public void init(){
        this.servicioUsuarioMock = mock(ServicioUsuarioImpl.class);
        this.servicioEntradaUsuarioMock = mock(ServicioEntradaUsuarioImpl.class);
        this.controladorEntradaUsuario = new ControladorEntradaUsuario(servicioEntradaUsuarioMock, servicioUsuarioMock);

        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session); // Asigna la sesión al request

    }


    @Test
    public void debenObtenerTodasLasEntradasDeUnUsuario() {

        String email = "test@usuario.com";
        session.setAttribute("email", email);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        when(this.servicioUsuarioMock.obtenerUsuarioVistaDTODelRepo(email)).thenReturn(usuario);

        List<EntradaUsuario> listaDeEntradasUs = new ArrayList<>();
        listaDeEntradasUs.add(new EntradaUsuario());

        when(this.servicioEntradaUsuarioMock.obtenerEntradasDeUsuario(usuario.getEmail())).thenReturn(listaDeEntradasUs);

        ModelAndView modelAndView = this.controladorEntradaUsuario.mostrarEntradasDelUsuario(request);


        assertThat(modelAndView.getViewName(), equalToIgnoringCase("misEntradas"));
    }


}
