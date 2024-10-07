package com.tallerwebi.presentacion;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.DetallesCompra;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mercadopago.MercadoPagoConfig;

import java.awt.desktop.PreferencesEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorMercadoPago {

    @Value("TEST-6976362109099701-091119-43201c5d5ca41ead55eb4039177de155-413515486")
    private String mercadoPagoToken;

    @RequestMapping(value = "/equipomokito/mp", method = RequestMethod.POST)
    public String getList (@RequestBody DetallesCompra detallesCompra){
    if (detallesCompra == null) {return "error JSON";}
    String nombreEvento = detallesCompra.getNombreEvento();
    Integer cantidad = detallesCompra.getCantidad();
    String tipoEntrada = detallesCompra.getTipoEntrada();
    Double precioUnidad = detallesCompra.getPrecioUnidad();

    try {
        MercadoPagoConfig.setAccessToken(mercadoPagoToken);
        //------------------------------------------Creacion de preferencia
      //1  Preferencia de venta
        PreferenceItemRequest itemRequest = PreferenceItemRequest
                        .builder()
                        .title(nombreEvento)
                        .categoryId(tipoEntrada)
                        .quantity(cantidad)
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal(precioUnidad))
                        .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

      //2  Preferencia de control de sucesos
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
                .builder().success("https://youtube.com")
                .pending("https://facebook.com")
                .failure("https://spotify.com")
                .build();

      //--------------------------------------------Emsamble de preferencias
      //  Creo una preferencia que contendra todas las preferencias que haya creado
        PreferenceRequest preferenceRequest = PreferenceRequest
                .builder()
                .items(items)
                .backUrls(backUrls)
                .build();

      //  Creo un objeto tipo cliente para comunicarse con MP
        PreferenceClient client = new PreferenceClient();
      //  Creo una nueva preferencia que será igual a la respuesta que nuestro cliente nos creara a partir de la informacion que le enviaremos
        Preference preference = client.create(preferenceRequest);

      //-------------------------------------------Retorno de preferencia
      //Retornamos esa preferencia a nuestroo frontend
        return preference.getId();
        } catch (MPApiException | MPException e) {return e.toString();}
    }
}
