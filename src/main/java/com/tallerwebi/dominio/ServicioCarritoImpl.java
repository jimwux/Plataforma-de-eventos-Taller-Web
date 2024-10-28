package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

    private  RepositorioEntrada repositorioEntrada;
    private Map<String, LocalDateTime> codigosDescuento;
    private final Double PORCENTAJE_DESCUENTO = 0.20;

    @Autowired
    public ServicioCarritoImpl(RepositorioEntrada repositorioEntrada) {
        this.repositorioEntrada = repositorioEntrada;
        this.codigosDescuento = new HashMap<>();
    }

    @Override
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

    @Override
    public Double calcularTotalCarrito(List<Carrito> carrito) {
        Double totalCarrito = 0.0;
        for (Carrito itemCarrito : carrito) {
            totalCarrito += itemCarrito.getTotalCarrito();
        }
        return totalCarrito;
    }

    @Override
    public String generarCodigoDescuento() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public void guardarCodigoDescuento(String codigo) {
        codigosDescuento.put(codigo, LocalDateTime.now().plusMinutes(2));
    }

    @Override
    public Boolean esCodigoDescuentoValido(String codigo) {
        LocalDateTime expiracion = codigosDescuento.get(codigo);
        if (expiracion == null || LocalDateTime.now().isAfter(expiracion)) {
            codigosDescuento.remove(codigo);
            return false;
        }
        return true;
    }

    @Override
    public Double calcularTotalCarritoConDescuento(Double totalCarrito) {
        return totalCarrito - ( totalCarrito * PORCENTAJE_DESCUENTO);
    }

}
