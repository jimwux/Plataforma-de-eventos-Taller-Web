package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

    private  RepositorioEntrada repositorioEntrada;

    public ServicioCarritoImpl(RepositorioEntrada repositorioEntrada) {
        this.repositorioEntrada = repositorioEntrada;
    }


    public List<Carrito> obtenerEntradasDelCarrito(List<Long> idsEntradas, List<Integer> cantidades) {
        List<Carrito> entradasCarrito = new ArrayList<>();

        for (int i = 0; i < idsEntradas.size(); i++) {
            Long idEntrada = idsEntradas.get(i);
            int cantidad = cantidades.get(i);

            // Usar el repositorio para obtener la entrada
            Entrada entradaEvento = repositorioEntrada.obtenerEntradaPorId(idEntrada);

            if (entradaEvento != null && cantidad > 0) {
                Double totalPrecio = entradaEvento.getPrecio() * cantidad;
                Carrito entradaCarrito = new Carrito(entradaEvento, cantidad, totalPrecio);
                entradasCarrito.add(entradaCarrito);
            }
        }

        return entradasCarrito;
    }


    public Double calcularTotalCarrito(List<Carrito> carrito) {
        Double totalCarrito = 0.0;
        for (Carrito itemCarrito : carrito) {
            totalCarrito += itemCarrito.getTotalCarrito();
        }
        return totalCarrito;
    }

}
