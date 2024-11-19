package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class VistaWeb {
    protected Page page;

    public VistaWeb(Page page) {
        this.page = page;
    }

    public String obtenerURLActual(){
        return page.url();
    }

    protected String obtenerTextoDelElemento(String selectorCSS){
        return this.obtenerElemento(selectorCSS).textContent();
    }

    // Esperar a que la página cargue completamente
    protected void esperarPaginaCargada() {
        page.waitForLoadState();
    }

    // Esperar a que un elemento esté visible
    protected void esperarElementoVisible(String selectorCSS) {
        page.waitForSelector(selectorCSS);
    }

    protected void darClickEnElElemento(String selectorCSS){
        this.obtenerElemento(selectorCSS).click();
    }


    //estamos seleccionando el primer elemento de la lista de eventos que tenemos
    //mostrara temporalmente mario por como se ven el en html (ya que se ordenan por fecha)
    protected void darClickEnElPrimerEvento(String selectorCSS){
        this.obtenerElemento(selectorCSS).nth(0).click();
    }

  /*  protected void seleccionarCantidadDeElPrimerTipoDeEntrada(Integer cantidad){
        this.obtenerElemento(".entrada:nth-child(1) select").selectOption(cantidad.toString());
    }
*/

    protected void escribirEnElElemento(String selectorCSS, String texto){
        this.obtenerElemento(selectorCSS).type(texto);
    }

    private Locator obtenerElemento(String selectorCSS){
        return page.locator(selectorCSS);
    }
}
