package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioEventoImpl implements RepositorioEvento {

    SessionFactory sessionFactory;

    @Autowired
    public RepositorioEventoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Evento> obtenerTodosLosEventos() {
        String hql = "FROM Evento";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public void guardar(Evento evento) {
        this.sessionFactory.getCurrentSession().save(evento);
    }

    @Override
    public void actualizarLugar(Evento evento) {
        String hql = "UPDATE Evento SET lugar = :lugar WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("lugar", evento.getLugar());
        query.setParameter("id", evento.getId());
        query.executeUpdate();
    }

    @Override
    public void actualizarNombre(Evento evento) {
        String hql = "UPDATE Evento SET nombre = :nombre WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", evento.getNombre());
        query.setParameter("id", evento.getId());
        query.executeUpdate();
    }

    @Override
    public Evento obtenerEventoPorId(Long id) {
        String hql = "FROM Evento WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Evento) query.getSingleResult();
    }
}
