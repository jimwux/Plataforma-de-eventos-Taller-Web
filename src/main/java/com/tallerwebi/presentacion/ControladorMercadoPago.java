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
import com.tallerwebi.dominio.Carrito;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkout")
public class ControladorMercadoPago {

//    @Autowired
//    private MercadoPagoConfig mercadoPagoConfig;


    @PostMapping("/create-payment")
    public String crearPago(@RequestBody Carrito carrito) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken("APP_USR-1119968299722789-101117-93b5a605f95dc93a7acdbb6162e43da3-1985716471");

// Crea un objeto de preferencia
        PreferenceClient client = new PreferenceClient();


// Crea un ítem en la preferencia
        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title("Hellouda")
                        .quantity(1)
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal("75"))
                        .build();


        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(item);

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name("Lautaro")
                .surname("Rosse")
                .email("user@email.com")
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
                .success("https://www.success.com")
                .failure("https://www.failure.com")
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

        return preference.getInitPoint();
    }
}