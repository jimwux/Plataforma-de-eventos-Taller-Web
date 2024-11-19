package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaPago extends VistaWeb {

    public VistaPago(Page page) {
        super(page);
        page.navigate("localhost:8080/equipomokito/pago");
    }



}
