package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EntradaCompra;
import com.tallerwebi.dominio.RepositorioEntradaCompra;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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

    @Override
    public void eliminarEntradasCompra(Long usuarioId) {
        Session session = sessionFactory.getCurrentSession();

        String emailUsuario = (String) session.createQuery(
                        "SELECT u.email FROM Usuario u WHERE u.id = :usuarioId")
                .setParameter("usuarioId", usuarioId)
                .uniqueResult();

        if (emailUsuario == null) {
            throw new IllegalArgumentException("No se encontró un usuario con el ID proporcionado: " + usuarioId);
        }

        String hql = "DELETE FROM EntradaCompra e " +
                "WHERE e.datosCompra.id IN (" +
                "SELECT d.id FROM DatosCompra d WHERE d.emailUsuario = :emailUsuario)";
        Query query = session.createQuery(hql);
        query.setParameter("emailUsuario", emailUsuario);
        query.executeUpdate();
    }

}
