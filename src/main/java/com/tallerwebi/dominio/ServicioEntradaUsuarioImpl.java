package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEntradaUsuarioImpl implements ServicioEntradaUsuario {

    private RepositorioEntradaUsuario repositorioEntradaUsuario;

    @Autowired
    public ServicioEntradaUsuarioImpl(RepositorioEntradaUsuario repositorioEntradaUsuario) {
        this.repositorioEntradaUsuario = repositorioEntradaUsuario;
    }

    @Override
    public void guardar(EntradaUsuario entradaUsuario) {
        this.repositorioEntradaUsuario.guardar(entradaUsuario);
    }

    @Override
    public void guardarEntradasDeTipo(Integer cantidad, Usuario user, Entrada entradaActual, String codigoTransaccion) {
        for (int i = 0; i < cantidad; i++) {
            EntradaUsuario entradaUsuario = new EntradaUsuario(user, entradaActual, codigoTransaccion);
            this.repositorioEntradaUsuario.guardar(entradaUsuario);
        }
    }

    @Override
    public List<EntradaUsuario> obtenerEntradasDeUsuario(String email) {
        return this.repositorioEntradaUsuario.obtenerEntradaPorUsuario(email);
    }
}
