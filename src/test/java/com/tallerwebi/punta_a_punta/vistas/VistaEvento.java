package com.tallerwebi.punta_a_punta.vistas;


import com.microsoft.playwright.FrameLocator;

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


    public String obtenerInformacionDelFooter(){
        return this.obtenerTextoDelElemento(".pie-de-pagina p");
    }



    public void hacerClickEnElPrimerEvento() {
        this.darClickEnElPrimerEvento(".evento a");
    }

    public void seleccionarCantidadPrimeraEntrada(String cantidad) {
        // Seleccionar el primer `select` encontrado dentro del contenedor
        page.selectOption("#contenedor_entradas select:first-of-type", cantidad);
    }

    public void hacerClickEnBotonAbonar() {
        page.click("#boton-comprar");
    }

    public void escribirNombre(String nombre){
        this.escribirEnElElemento("#nombre", nombre);
    }

    public void escribirApellido(String apellido){
        this.escribirEnElElemento("#apellido", apellido);
    }

    public void escribirCorreo(String correo){
        this.escribirEnElElemento("#correo", correo);
    }

    public void escribirCorreoRepetido(String correoRep){
        this.escribirEnElElemento("#correoRep", correoRep);
    }

    public void escribirTelefono(String telefono){
        this.escribirEnElElemento("#telefono", telefono);
    }

    public void escribirDNI(String dni){
        this.escribirEnElElemento("#dni", dni);
    }

    public void hacerClickEnAceptarTerminosYCondiciones(){
        this.darClickEnElElemento("#aceptacionTerminos");
    }

    public void hacerClickEnAceptarTerminos() {
        this.darClickEnElElemento("#aceptarTerminosBoton");
    }


    public void hacerClickEnAbonar() {
        this.darClickEnElElemento("#boton-enviar");
    }

    public void escribirCorreoPruebaMP(String correoTester) {
        this.escribirEnElElemento("#user_id", correoTester);
    }


    public void hacerClickEnContinuarMP() {
        this.darClickEnElElemento(".login-form__submit");
    }

    public void escribirContraseniaPruebaMP(String contrasenia) {
        this.escribirEnElElemento("#password", contrasenia);
    }

    public void hacerClickEnIniciarSesionMP() {
        this.darClickEnElElemento("#action-complete");
    }

    public void hacerClickEnBotonPagarDeMercadoPago() {
        this.darClickEnElElemento("button:has-text('Pagar')");
    }


    public void esperarAQueSeaVisibleLaPassDeMp(){
        page.waitForSelector("#password");
    }



    //modificar de mercado pago
    public void hacerClickEnModificarMP() {
        this.darClickEnElElemento(".card-option-cta");
    }

    public void hacerClickEnTipoTarjetaMP() {
        this.darClickEnElElemento("#debit_and_prepaid_card_row");
    }


    public void escribirNombreTitular(String nombre) {
        this.escribirEnElElemento("#fullname", nombre);
    }

    public void hacerClickEnContinuarDatosTarjeta() {
        this.darClickEnElElemento("#submit");
    }


    public void inspeccionarIframeNumeroTarjeta(){
        // Acceder al iframe
        FrameLocator iframe = page.frameLocator("iframe").first();

        // Esperar e interactuar con el campo
        iframe.locator("input#cardNumber").waitFor();
        iframe.locator("input#cardNumber").fill("5031 7557 3453 0604");

    }


    public void inspeccionarIframeFechaVencimiento(){
        // Acceder al iframe
        FrameLocator iframe = page.frameLocator("iframe").nth(1);

        // Esperar e interactuar con el campo
        iframe.locator("input#expirationDate").waitFor();
        iframe.locator("input#expirationDate").fill("11/25");

    }

    public void inspeccionarIframeCodigoSeguridad(){
        // Acceder al iframe
        FrameLocator iframe = page.frameLocator("iframe").nth(2);

        // Esperar e interactuar con el campo
        iframe.locator("input#securityCode").waitFor();
        iframe.locator("input#securityCode").fill("123");

    }

    public void escribirDniMP(String number) {
        this.escribirEnElElemento("#number", number);
    }

    public void hacerClickEnContinuarMPDni() {
        this.darClickEnElElemento("#submit");
    }

    public void hacerClickEnLaCuota() {
        this.darClickEnElElemento("label[for='1']");

    }


    public void hacerClickEnVolverAlSitio() {
        this.darClickEnElElemento("text=Volver al sitio");
    }

    public void hacerClickEnVolverAEventos() {
        this.darClickEnElElemento(".compraFin");
    }

    public void hacerClickEnIngresar() {
        this.darClickEnElElemento(".ingresar");

    }

    public void escribirEmail(String mail) {
        this.escribirEnElElemento("#email", mail);
    }

    public void escribirContrasenia(String pass) {
        this.escribirEnElElemento("#password", pass);
    }

    public void hacerClickEnIniciarSesion() {
        this.darClickEnElElemento("#btn-login");
    }

    public void hacerClickEnMisEntradas() {
        this.darClickEnElElemento(".misEnt");
    }

    public String leerEntradaComprada() {
      return  page.locator(".cajita2 h3:has-text('Parense de Manos II')").nth(0).textContent();

    }
}
