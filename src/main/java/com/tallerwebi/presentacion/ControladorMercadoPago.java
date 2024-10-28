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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkout")
public class ControladorMercadoPago {


    @Value("${mercadoPago.accessToken}")
    private String mercadoPagoAccessToken;

    @PostMapping("/create-payment")
    public void crearPago(HttpServletResponse response,
                          @RequestParam("cantidades") List<Integer> cantidades,
                          @RequestParam("idsEntradas") List<Long> idsEntradas,
                          @RequestParam("precioEntrada") List<Double> precioEntrada,
                          @RequestParam("tipoEntrada") List<String> tipoEntradas,
                          @RequestParam("nombreEvento") String nombreEvento,
                          @RequestParam("correo") String emailUsuario) throws MPException, MPApiException, IOException {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

// Crea un objeto de preferencia
        PreferenceClient client = new PreferenceClient();


// Crea un ítem en la preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();
        for (int i = 0; i < idsEntradas.size(); i++) {
            PreferenceItemRequest item =
                    PreferenceItemRequest.builder()

                            .title(nombreEvento + " - " + tipoEntradas.get(i))
                            .description("Tipo Entrada: " + tipoEntradas.get(i))
                            .quantity(cantidades.get(i))
                            .currencyId("ARS")
                            .unitPrice(BigDecimal.valueOf(precioEntrada.get(i)))
                            .build();

            items.add(item);
        }
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name("Lautaro")
                .surname("Rosse")
                .email(emailUsuario)
                .phone(PhoneRequest.builder()
                    .areaCode("11")
                    .number("4444-4444")
                    .build())
                .identification(IdentificationRequest.builder()
                    .type("DNI")
                    .number("19119119100")
                    .build())
                .build();


        PreferenceBackUrlsRequest  backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/equipomokito/compraFinalizada")
                .failure("http://localhost:8080/equipomokito/eventos")
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

        response.sendRedirect(preference.getInitPoint());
    }
}