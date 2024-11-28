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
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(5500));

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

        // Esperar a que la página cargue completamente
        vistaEvento.cargarPagina();
        // Asegurarse de que el evento esté disponible
        vistaEvento.cargarElementoVisible();
        // Hacer clic en el evento
        vistaEvento.hacerClickEnElPrimerEvento();

        String urlDetalle = vistaEvento.obtenerURLActual();
        assertThat(urlDetalle, containsString("eventos/"));


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
        vistaEvento.inspeccionarIframeNumeroTarjeta();
        vistaEvento.escribirNombreTitular("APRO");
        vistaEvento.inspeccionarIframeFechaVencimiento();
        vistaEvento.inspeccionarIframeCodigoSeguridad();
        vistaEvento.hacerClickEnContinuarDatosTarjeta();

        //llenar ultimo dato
        vistaEvento.escribirDniMP("12345678");
        vistaEvento.hacerClickEnContinuarMPDni();

        //cuota
        vistaEvento.hacerClickEnLaCuota();

        //pagar
        vistaEvento.hacerClickEnBotonPagarDeMercadoPago();

        //volver a compraFinalizada
        vistaEvento.hacerClickEnVolverAlSitio();

        String urlFinal = vistaEvento.obtenerURLActual();
        assertThat(urlFinal, containsString("/compraFinalizada"));

        vistaEvento.hacerClickEnVolverAEventos();
        String urlEventos = vistaEvento.obtenerURLActual();
        assertThat(urlEventos, containsString("/eventos"));

        vistaEvento.hacerClickEnIngresar();

        vistaEvento.escribirEmail("ariaasol9@gmail.com");
        vistaEvento.escribirContrasenia("123456789");

        vistaEvento.hacerClickEnIniciarSesion();

        vistaEvento.hacerClickEnMisEntradas();

        String nombreEntrada = vistaEvento.leerEntradaComprada();
        String urlMisEntradas = vistaEvento.obtenerURLActual();
        assertThat(nombreEntrada, equalToIgnoringCase("Parense de Manos II"));
        assertThat(urlMisEntradas, containsString("/misEntradas"));

    }



}
