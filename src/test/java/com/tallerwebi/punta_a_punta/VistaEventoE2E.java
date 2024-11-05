package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaEvento;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.matchesPattern;
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
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));

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



}
