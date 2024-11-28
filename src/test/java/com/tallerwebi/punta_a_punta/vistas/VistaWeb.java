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

    protected void esperarPaginaCargada() {
        page.waitForLoadState();
        //page representa la ventana del navegador que se esta ejecutando
        //el metodo pausa la ejecucion del codigo hasta que la pagina cargue
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
        this.obtenerElemento(selectorCSS).nth(8).click();
    }


    protected void escribirEnElElemento(String selectorCSS, String texto){
        this.obtenerElemento(selectorCSS).type(texto);
    }

    private Locator obtenerElemento(String selectorCSS){
        return page.locator(selectorCSS);
    }
}
