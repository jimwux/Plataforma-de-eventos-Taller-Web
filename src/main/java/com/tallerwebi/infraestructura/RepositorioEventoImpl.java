package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
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
        Evento eventoInexistente = this.sessionFactory.getCurrentSession().get(Evento.class, evento.getId());

        if (eventoInexistente == null){
            throw new EntityNotFoundException("Evento no encontrado)");
        }
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
       public void eliminarEvento(Evento evento) {
           String hql = "DELETE FROM Evento WHERE id = :id";
           Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
           query.setParameter("id", evento.getId());
           query.executeUpdate();
       }

    @Override
    public List<Evento> obtenerLosEventosPorFecha(LocalDate fecha) {
        String hql = "FROM Evento WHERE fecha = :fecha";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql, Evento.class);
        query.setParameter("fecha", fecha);
        return query.getResultList();
    }

    @Override
    public List<Evento> buscarEventosPorNombre(String busqueda) {
        String hql = "FROM Evento WHERE lower(nombre) LIKE :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", busqueda + "%");  // Concatenamos el valor del nombre con "%"
        return query.getResultList();
    }
      
    @Override
    public Evento obtenerEventoPorId(Long id) {
        String hql = "FROM Evento WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Evento) query.getSingleResult();
    }

    @Override
    public List<Evento> obtenerEventosPorCategoria(String categoria) {
        String hql = "FROM Evento WHERE categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("categoria", categoria);
        return query.getResultList();
    }



}
