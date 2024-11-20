package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ServicioEntradaUsuarioImpl implements ServicioEntradaUsuario {

    private RepositorioEntradaUsuario repositorioEntradaUsuario;
    private GeneradorCodigoQr generadorCodigoQr;

    @Autowired
    public ServicioEntradaUsuarioImpl(RepositorioEntradaUsuario repositorioEntradaUsuario, GeneradorCodigoQr generadorCodigoQr) {
        this.repositorioEntradaUsuario = repositorioEntradaUsuario;
        this.generadorCodigoQr = generadorCodigoQr;
    }

    @Override
    public void guardar(EntradaUsuario entradaUsuario) {
        this.repositorioEntradaUsuario.guardar(entradaUsuario);
    }

    @Override
    public void guardarEntradasDeTipo(Integer cantidad, Usuario user, Entrada entradaActual, String codigoTransaccion) {
        for (int i = 0; i < cantidad; i++) {
            EntradaUsuario entradaUsuario = new EntradaUsuario(user, entradaActual, codigoTransaccion);

            // Generar QR usando el servicio inyectado
            String qrData = entradaUsuario.getId() +
                    ", Compra: " + codigoTransaccion;

            String qrCodeBase64 = generadorCodigoQr.generarCodigoQRBase64(qrData);
            entradaUsuario.setQrCode(qrCodeBase64);

            this.repositorioEntradaUsuario.guardar(entradaUsuario);
        }
    }


    @Override
    public List<EntradaUsuario> obtenerEntradasDeUsuario(String email) {
        return this.repositorioEntradaUsuario.obtenerEntradaPorUsuario(email);
    }

    @Override
    public List<EntradaUsuario> obtenerEntradasDeUsuarioPorCategoria(String email, String categoria) {
        return this.repositorioEntradaUsuario.obtenerEntradaPorUsuarioYCategoria(email, categoria);

    }

    @Override
    public List<EntradaUsuario> obtenerEntradasDeUnaTransaccion(String codigoTransaccion) {
        return this.repositorioEntradaUsuario.obtenerEntradasDeUnaTransaccion(codigoTransaccion);


    }

}
