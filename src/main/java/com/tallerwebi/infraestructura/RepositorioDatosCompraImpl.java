package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DatosCompra;
import com.tallerwebi.dominio.RepositorioDatosCompra;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class RepositorioDatosCompraImpl implements RepositorioDatosCompra {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioDatosCompraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(DatosCompra datosCompra) {
        this.sessionFactory.getCurrentSession().save(datosCompra);
    }

    @Override
    public DatosCompra obtenerCompraPorCodigoTransaccion(String codigoTransaccion) {
        String hql = "FROM DatosCompra WHERE codigoTransaccion = :codigoTransaccion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("codigoTransaccion", codigoTransaccion);
        return (DatosCompra) query.getSingleResult();
    }

    @Override
    public void eliminarCompraPorCodigoTransaccion(String codigoTransaccion) {
        String hql = "DELETE FROM DatosCompra WHERE codigoTransaccion = :codigoTransaccion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("codigoTransaccion", codigoTransaccion);
        query.executeUpdate();
    }

}
