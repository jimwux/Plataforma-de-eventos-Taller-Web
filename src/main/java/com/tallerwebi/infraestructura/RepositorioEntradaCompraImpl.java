package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EntradaCompra;
import com.tallerwebi.dominio.RepositorioEntradaCompra;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEntradaCompraImpl implements RepositorioEntradaCompra {

    SessionFactory sessionFactory;

    @Autowired
    public RepositorioEntradaCompraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(EntradaCompra entradaCompra) {
        this.sessionFactory.getCurrentSession().save(entradaCompra);
    }
}
