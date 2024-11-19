package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaEvento extends VistaWeb{

    public VistaEvento(Page page) {
        super(page);
        page.navigate("localhost:8080/equipomokito/eventos");
    }

    public void cargarPagina(){
       this.esperarPaginaCargada();
   }

   public void cargarElementoVisible(){
        this.esperarElementoVisible(".evento a");
   }

    public String obtenerTituloDelNav(){
        return this.obtenerTextoDelElemento("#titulo-pagina");
    }


    public void hacerClickEnElPrimerEvento() {
        this.darClickEnElPrimerEvento(".evento a");
    }




   /* public void darClickEnElBotonDeVistaDetalle(){
        this.darClickEnElElemento("#boton-comprar");
    }
*/






    public String obtenerInformacionDelFooter(){
        return this.obtenerTextoDelElemento(".pie-de-pagina p");
    }

/*
    public void seleccionarCantidadDeUnTipoDeEntrada(Integer cantidad){
        this.seleccionarCantidadDeElPrimerTipoDeEntrada(cantidad);
    }
*/
}
