package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaDetalle extends VistaWeb {

    public VistaDetalle(Page page, String idEvento) {
        super(page);
        page.navigate("localhost:8080/equipomokito/" + idEvento);
    }



}
