package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

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




   /* public void darClickEnElBotonDeVistaDetalle(){
        this.darClickEnElElemento("#boton-comprar");
    }
*/


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


    public void hacerClickParaVisualizarLaCompraFinalizada() {
        this.darClickEnElElemento(".button-link");
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

    public void escribirNumeroTarjeta(String numero) {
        this.escribirEnElElemento("#cardNumber", numero);
    }

    public void escribirNombreTitular(String nombre) {
        this.escribirEnElElemento(".andes-form-control__field", nombre);
    }

    public void escribirCodigoSeguridad(String codigo) {
        this.escribirEnElElemento("#securityCode", codigo);
    }

    public void escribirVencimiento(String fechaV) {
        this.escribirEnElElemento("#expirationDate", fechaV);
    }

    public void hacerClickEnContinuarDatosTarjeta() {
        this.darClickEnElElemento("#submit");
    }


    public void inspeccionarIframe(){
        Locator iframeLocator = page.locator("iframe[name='cardNumber']");
        iframeLocator.waitFor(new Locator.WaitForOptions().setTimeout(50000));
        Locator cardNumberLocator = iframeLocator.frameLocator("iframe").locator("#cardNumber");


        cardNumberLocator.type("5031 7557 3453 0604");

    }










/*
    public void seleccionarCantidadDeUnTipoDeEntrada(Integer cantidad){
        this.seleccionarCantidadDeElPrimerTipoDeEntrada(cantidad);
    }
*/
}
