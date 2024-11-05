package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaEvento extends VistaWeb{

    public VistaEvento(Page page) {
        super(page);
        page.navigate("localhost:8080/equipomokito/eventos");
    }

    public String obtenerTituloDelNav(){
        return this.obtenerTextoDelElemento("#titulo-pagina");
    }


    public void hacerClickEnUnEvento() {
        this.darClickEnElElemento(".evento a");
    }


    public String obtenerInformacionDelFooter(){
        return this.obtenerTextoDelElemento(".pie-de-pagina p");
    }

}
