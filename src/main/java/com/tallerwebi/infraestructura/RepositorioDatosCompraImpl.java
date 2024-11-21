package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DatosCompra;
import com.tallerwebi.dominio.RepositorioDatosCompra;
import org.hibernate.Session;
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

    @Override
    public void eliminarDatosCompra(Long usuarioId) {
        Session session = sessionFactory.getCurrentSession();

        // Obtener el email del usuario
        String emailUsuario = (String) session.createQuery(
                        "SELECT u.email FROM Usuario u WHERE u.id = :usuarioId")
                .setParameter("usuarioId", usuarioId)
                .uniqueResult();

        if (emailUsuario == null) {
            throw new IllegalArgumentException("No se encontró un usuario con el ID proporcionado: " + usuarioId);
        }

        // Eliminar registros de la tabla DatosCompra
        String hql = "DELETE FROM DatosCompra d WHERE d.emailUsuario = :emailUsuario";
        Query query = session.createQuery(hql);
        query.setParameter("emailUsuario", emailUsuario);
        query.executeUpdate();
    }




}
