package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
//import com.tallerwebi.config.MercadoPagoServiceConfig;

//import com.mercadopago.resources.common.Phone;
//import com.mercadopago.resources.customer.Identification;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.dto.FormularioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checkout")
public class ControladorMercadoPago {

    private ServicioCarrito servicioCarrito;
    private ServicioRegistro servicioRegistro;
    private ServicioLogin servicioLogin;
    private ServicioEmail servicioEmail;
    private ServicioDatosCompra servicioDatosCompra;
    private ServicioEntradaCompra servicioEntradaCompra;


    @Autowired
    public ControladorMercadoPago(ServicioCarrito servicioCarrito, ServicioRegistro servicioRegistro, ServicioLogin servicioLogin, ServicioEmail servicioEmail, ServicioDatosCompra servicioDatosCompra, ServicioEntradaCompra servicioEntradaCompra) {
        this.servicioCarrito = servicioCarrito;
        this.servicioRegistro = servicioRegistro;
        this.servicioLogin = servicioLogin;
        this.servicioEmail = servicioEmail;
        this.servicioDatosCompra = servicioDatosCompra;
        this.servicioEntradaCompra = servicioEntradaCompra;
    }

    @Value("${mercadoPago.accessToken}")
    private String mercadoPagoAccessToken;

    @PostMapping("/create-payment")
    public void crearPago(HttpServletRequest request, HttpServletResponse response,
                          @Valid @ModelAttribute FormularioDTO formularioPagoDTO,
                          BindingResult result,
                          @RequestParam("cantidades") List<Integer> cantidades,
                          @RequestParam("idsEntradas") List<Long> idsEntradas,
                          @RequestParam("precioEntrada") List<Double> precioEntrada,
                          @RequestParam("tipoEntrada") List<String> tipoEntradas,
                          @RequestParam("nombreEvento") String nombreEvento,
                          @RequestParam(value = "codigoDescuento", required = false) String codigoDescuento) throws MPException, MPApiException, IOException, UsuarioExistente, UsuarioExistente {

        if (result.hasErrors() || !formularioPagoDTO.correosCoinciden()) {
            System.out.println("Errores de validación: " + result.getAllErrors());
            System.out.println("¿Correos coinciden?: " + formularioPagoDTO.correosCoinciden());
            response.sendRedirect("/formulario?error=true");
            return;
        } //refactorizar


        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);


        Usuario user = verificarYRegistrarUsuario(formularioPagoDTO.getEmail(), formularioPagoDTO.getNombre(), formularioPagoDTO.getApellido(), formularioPagoDTO.getTelefono(), formularioPagoDTO.getDni());


        // Calcular el descuento si hay un código válido
        Double porcentajeDescuento = 0.20;
        boolean descuentoAplicado = codigoDescuento != null && this.servicioCarrito.esCodigoDescuentoValido(codigoDescuento, LocalDateTime.now()); // Valida aquí tu código de descuento real

    // Crea un objeto de preferencia
        PreferenceClient client = new PreferenceClient();

    // Crea un ítem en la preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();
        for (int i = 0; i < idsEntradas.size(); i++) {

            Double precio = precioEntrada.get(i);
            if (descuentoAplicado) {
                precio = precio - (precio * porcentajeDescuento); // Aplica el descuento
            }

            PreferenceItemRequest item =
                    PreferenceItemRequest.builder()

                            .title(nombreEvento + " - " + tipoEntradas.get(i))
                            .description("Tipo Entrada: " + tipoEntradas.get(i))
                            .quantity(cantidades.get(i))
                            .currencyId("ARS")
                            .unitPrice(BigDecimal.valueOf(precio))
                            .build();

            items.add(item);
        }
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name(user.getNombre())
                .surname(user.getApellido())
                .email(user.getEmail())
                .phone(PhoneRequest.builder()
                    .areaCode(user.getTelefono().substring(0, 2))
                    .number(user.getTelefono().substring(2, 8))
                    .build())
                .identification(IdentificationRequest.builder()
                    .type("DNI")
                    .number(user.getDni())
                    .build())
                .build();

        String codigoTransaccion = UUID.randomUUID().toString();

        PreferenceBackUrlsRequest  backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/equipomokito/compraFinalizada?codigoTransaccion=" + codigoTransaccion)
                .failure("http://localhost:8080/equipomokito/compraFinalizada?codigoTransaccion=" + codigoTransaccion)
                .pending("https://www.pending.com")
                .build();

        PreferenceRequest preferenceRequest =
                PreferenceRequest.builder()
                        .items(items)
                        .purpose("wallet_purchase")
                        .backUrls(backUrls)
                        .payer(payer)
                        .build();

        Preference preference = client.create(preferenceRequest);

        guardarDatosCompra (cantidades, idsEntradas, formularioPagoDTO.getEmail(), codigoTransaccion);


        response.sendRedirect(preference.getSandboxInitPoint());
    }

    public void guardarDatosCompra (List<Integer> cantidades, List<Long> idsEntradas, String emailUsuario, String codigoTransaccion) {
        if (cantidades.size() != idsEntradas.size()) {
            throw new IllegalArgumentException("Las listas de cantidades e IDs de entradas deben tener el mismo tamaño");
        }

        DatosCompra datosCompraPendiente = new DatosCompra(codigoTransaccion, emailUsuario);
        for (int i = 0; i < idsEntradas.size(); i++) {
            EntradaCompra entradaCompra = datosCompraPendiente.agregarEntrada(idsEntradas.get(i), cantidades.get(i));
            this.servicioEntradaCompra.guardar(entradaCompra);
        }
        this.servicioDatosCompra.guardar(datosCompraPendiente);
    }

    public Usuario verificarYRegistrarUsuario(String emailUsuario, String nombre, String apellido, String telefono, String dni) throws UsuarioExistente {
        Usuario user = this.servicioLogin.verificarSiExiste(emailUsuario);

        if(user == null){
            String pass = this.servicioRegistro.generarContrasena();
            user = new Usuario(emailUsuario, pass, nombre, apellido, telefono, dni);
            this.servicioRegistro.registrar(user);
            this.servicioEmail.enviarContraseniaAUsuarios(emailUsuario, pass);
        }
        return user;
    }
}