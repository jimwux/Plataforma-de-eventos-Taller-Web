package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEntradaImpl implements ServicioEntrada {

    private RepositorioEntrada repositorioEntrada;

    @Autowired
    public ServicioEntradaImpl(RepositorioEntrada repositorioEntrada) {
        this.repositorioEntrada = repositorioEntrada;
    }

    @Override
    public List<Entrada> obtenerTodasLasEntradas() {
        return repositorioEntrada.obtenerTodasLasEntradas();
    }

    @Override
    public Entrada obtenerEntradaPorId(Long id) {
        return repositorioEntrada.obtenerEntradaPorId(id);
    }

    @Override
    public List<Entrada> obtenerEntradasDeUnEvento(Long id) {
        return this.repositorioEntrada.obtenerEntradasDeUnEvento(id);
    }

    @Override
    public Boolean validarStockEntradas(List<Long> idsEntradas, List<Integer> cantidades) {
        for (int i = 0; i < idsEntradas.size(); i++) {
            Long idEntrada = idsEntradas.get(i);
            int cantidadSolicitada = cantidades.get(i);
            if (cantidadSolicitada != 0) {
                Entrada entrada = obtenerEntradaPorId(idEntrada);
                if (entrada == null || cantidadSolicitada > entrada.getStock() || cantidadSolicitada > 4) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean reducirStock(Long idEntrada, Integer cantidad) {
        int filasAfectadas = this.repositorioEntrada.reducirStock(idEntrada, cantidad);
        return filasAfectadas > 0;  // Si el número de filas afectadas es mayor que 0, la actualización fue exitosa
    }


}
