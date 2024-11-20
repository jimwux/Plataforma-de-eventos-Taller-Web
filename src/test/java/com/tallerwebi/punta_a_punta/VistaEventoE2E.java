package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.tallerwebi.punta_a_punta.vistas.VistaEvento;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaEventoE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaEvento vistaEvento;


    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        //browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(8000));

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

        //vistaDetalle = new VistaDetalle(page, "6");
        //vistaPago = new VistaPago(page);
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


        // Seleccionar "2" en el primer select de entradas
        vistaEvento.seleccionarCantidadPrimeraEntrada("1");

        // Hacer clic en el botón "Abonar"
        vistaEvento.hacerClickEnBotonAbonar();

        String urlActual = vistaEvento.obtenerURLActual();
        assertThat(urlActual, containsString("pago"));

        //tipeamos datos de formulario
        vistaEvento.escribirNombre("Sol");
        vistaEvento.escribirApellido("Arias");
        vistaEvento.escribirCorreo("ariaasol9@gmail.com");
        vistaEvento.escribirCorreoRepetido("ariaasol9@gmail.com");
        vistaEvento.escribirTelefono("1128438706");
        vistaEvento.escribirDNI("46349257");

        vistaEvento.hacerClickEnAceptarTerminosYCondiciones();
        vistaEvento.hacerClickEnAceptarTerminos();

        //hacemos click en abonar
        vistaEvento.hacerClickEnAbonar();

        //String urlPago = vistaEvento.obtenerURLActual();
       // assertThat(urlPago, containsString("/checkout/"));

        //inicio de sesion
        vistaEvento.escribirCorreoPruebaMP("TESTUSER919898742");
        vistaEvento.hacerClickEnContinuarMP();

        vistaEvento.esperarAQueSeaVisibleLaPassDeMp();

        //contraseña
        vistaEvento.escribirContraseniaPruebaMP("fq3ul0hsDY");
        vistaEvento.hacerClickEnIniciarSesionMP();

        //COLOCACION DE DATOS DE TARJETA PARA PODER USAR MERCADO PAGO

        //presionar modificar
        vistaEvento.hacerClickEnModificarMP();

        //seleccionar tipo de tarjeta
        vistaEvento.hacerClickEnTipoTarjetaMP();

        //cargar datos de la tarjeta
        vistaEvento.inspeccionarIframe();
       // vistaEvento.escribirNumeroTarjeta("5031 7557 3453 0604");
        vistaEvento.escribirNombreTitular("APRO");
        vistaEvento.escribirVencimiento("11/25");
        vistaEvento.escribirCodigoSeguridad("123");

        vistaEvento.hacerClickEnContinuarDatosTarjeta();



        /*

        String urlMp = vistaEvento.obtenerURLActual();
        assertThat(urlMp, containsString("checkout/v1/redirect/"));

        vistaEvento.hacerClickEnBotonPagarDeMercadoPago();
*/







       /*
        String urlDetallePago = vistaEvento.obtenerURLActual();
        assertThat(urlDetallePago, containsString("/approved/"));

        vistaEvento.hacerClickParaVisualizarLaCompraFinalizada();

        String urlFinal = vistaEvento.obtenerURLActual();
        assertThat(urlFinal, containsString("/compraFinalizada"));
*/





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
