package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.ControladorLogin;
import com.tallerwebi.presentacion.dto.DatosLoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.reactive.server.JsonPathAssertions;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLoginDTO datosLoginDTOMock;
	private ServicioLogin servicioLoginMock;
	private HttpSession session;
	private HttpServletRequest request;

	@BeforeEach
	public void init() {
		datosLoginDTOMock = new DatosLoginDTO("a@gmail.com", "123");
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("a@gmail.com");
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock);
		session = Mockito.mock(HttpSession.class);
		request = Mockito.mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome() {
		// Preparación
		DatosLoginDTO datosLogin = new DatosLoginDTO("a@gmail.com", "123");
		Usuario usuarioMock = new Usuario();
		usuarioMock.setId(1L);
		usuarioMock.setEmail("a@gmail.com");
		usuarioMock.setPassword("123");
		usuarioMock.setNombre("brian");
		usuarioMock.setApellido("hidalgo");
		usuarioMock.setTelefono("1123895568");
		usuarioMock.setDni("44318250");

		// Mock del servicio para que devuelva el usuarioMock
		when(servicioLoginMock.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword())).thenReturn(usuarioMock);

		// Mock de request para que devuelva la sesion
		when(request.getSession(false)).thenReturn(session);

		// Ejecución
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginDTOMock, request);

		// Validacion del ID
		verify(session).setAttribute("ID", usuarioMock.getId());

		// Validación de la redirección
		assertThat(modelAndView.getViewName(), equalTo("redirect:/eventos"));

	}




	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente() {
		// preparacion
		DatosLoginDTO datosLogin = new DatosLoginDTO("a@gmail.com", "123");
		when(servicioLoginMock.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginDTOMock, request);

		// validacion
		assertNull(session.getAttribute("ID"));
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
	}
}

