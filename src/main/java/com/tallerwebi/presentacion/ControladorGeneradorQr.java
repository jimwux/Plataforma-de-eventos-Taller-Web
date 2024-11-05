package com.tallerwebi.presentacion;

import com.google.zxing.WriterException;
import com.tallerwebi.dominio.ServicioGeneradorQr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ControladorGeneradorQr {

    private ServicioGeneradorQr servicioGeneradorQr;

    @Autowired
    public ControladorGeneradorQr(ServicioGeneradorQr servicioGeneradorQr) {
        this.servicioGeneradorQr = servicioGeneradorQr;
    }

    @GetMapping("/generate-qr")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam("text") String text) {
        try {
            byte[] qrImage = servicioGeneradorQr.generateQRCodeImage(text, 200, 200);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=qr.png")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        //generate-qr?text=hola
        // se usa una etencion asi en la url para convertir lo que sea texto en la url, sino no anda
    }

}