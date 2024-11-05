package com.tallerwebi.dominio;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface ServicioGeneradorQr {
    public byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException;
}
