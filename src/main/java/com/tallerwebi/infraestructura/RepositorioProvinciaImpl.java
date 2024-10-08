package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Provincia;
import com.tallerwebi.dominio.RepositorioProvincia;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioProvinciaImpl implements RepositorioProvincia {

    private SessionFactory sessionFactory;

    public RepositorioProvinciaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Provincia provincia) {
        this.sessionFactory.getCurrentSession().save(provincia);
    }

    @Override
    public Provincia obtenerProvinciaPorNombre(String nombreProvincia) {
        String hql = "FROM Provincia WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", nombreProvincia);
        return (Provincia) query.getSingleResult();
    }

    @Override
    public List<Provincia> obtenerTodasLasProvincias() {
        String hql = "FROM Provincia";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public void eliminarProvincia(Provincia provincia) {
        String hql = "DELETE FROM Provincia WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", provincia.getId());
        query.executeUpdate();
    }

}
