package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ciudad;
import com.tallerwebi.dominio.Provincia;
import com.tallerwebi.dominio.RepositorioCiudad;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioCiudadImpl implements RepositorioCiudad {

    private SessionFactory sessionFactory;

    public RepositorioCiudadImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Ciudad ciudad) {
        this.sessionFactory.getCurrentSession().save(ciudad);
    }

    @Override
    public Ciudad obtenerCiudadPorNombre(String nombreCiudad) {
        String hql = "FROM Ciudad WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", nombreCiudad);
        return (Ciudad) query.getSingleResult();
    }

    @Override
    public List<Ciudad> obtenerTodasLasCiudades() {
        String hql = "FROM Ciudad";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public void eliminarCiudad(Ciudad ciudad) {
        String hql = "DELETE FROM Ciudad WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", ciudad.getId());
        query.executeUpdate();
    }

    @Override
    public List<Ciudad> obtenerCiudadesPorProvincia(String nombreProvincia) {
        String hql = "SELECT c FROM Ciudad c JOIN Provincia p ON c.provincia.id = p.id WHERE p.nombre = :nombreProvincia";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreProvincia", nombreProvincia);
        return query.getResultList();
    }
}
