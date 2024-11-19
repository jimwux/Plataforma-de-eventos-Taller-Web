package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaEvento;
import com.tallerwebi.punta_a_punta.vistas.VistaDetalle;
import com.tallerwebi.punta_a_punta.vistas.VistaPago;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaEventoE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaEvento vistaEvento;
    VistaDetalle vistaDetalle;
    VistaPago vistaPago;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        //browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));

    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaEvento = new VistaEvento(page);

        vistaDetalle = new VistaDetalle(page, "6");
        vistaPago = new VistaPago(page);
       // vistaMP = new VistaMP(page);
       // vistaCompraFinalizada = new VistaCompraFinalizada(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaDecirEquipoMokitoEnElNav() {
        String titulo = vistaEvento.obtenerTituloDelNav();

        assertThat("Equipo Mokito", equalToIgnoringCase(titulo));
    }

//    @Test
//    void deberiaActualizarUrlAlPresionarUnEvento() {
//        vistaEvento.hacerClickEnUnEvento(); // Simula el clic en el primer evento
//        String urlActual = vistaEvento.obtenerURLActual(); // Obtiene la URL actual después del clic
//        assertThat(urlActual, matchesPattern(".*/eventos/\\d+$"));
//    }


    @Test
    void deberiaDecirLaInformacionDelFooter() {
        String footer= vistaEvento.obtenerInformacionDelFooter();

        assertThat("© 2024 Equipo Mokito", equalToIgnoringCase(footer));
    }


    @Test
    void deberiaComprarUnaEntrada(){

        //Debemos estar parados en la vista localhost:8080/equipomokito/eventos
        //Debemos hacer click en una de las tarjetas de eventos


        // Esperar a que la página cargue completamente
        vistaEvento.cargarPagina();
        // Asegurarse de que el evento esté disponible
        vistaEvento.cargarElementoVisible();
        // Hacer clic en el evento
        vistaEvento.hacerClickEnElPrimerEvento();
        // Validar la URL actual
        String url = vistaEvento.obtenerURLActual();
        assertThat(url, containsString("eventos/"));




/*
        vistaEvento.seleccionarCantidadDeUnTipoDeEntrada(2);
        vistaEvento.darClickEnElBotonDeVistaDetalle();

        String url2 = vistaEvento.obtenerURLActual();
        assertThat(url2, containsString("equipomokito/pago"));
*/

        //Debemos seleccionar la cantidad de entradas y el tipo
        //Presionar abonar

        //Debemos rellenar los datos del formulario
        //Debemos presionar abonar

        //Debemos inspeccionar los botones de mercado pago para poder presionarlos
        //Abonariamos

        //Debemos ver la vista de compra finalizada

        //Podriamos probar iniciando sesion con esa cuenta
        //Revisar en mis entradas que este ese elemento comprado





    }



}
