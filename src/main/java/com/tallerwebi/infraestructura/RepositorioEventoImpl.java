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
        evento.setNombre(evento.getNombre().toLowerCase());
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
        String busquedaNormalizada = busqueda.toLowerCase();
        String hql = "FROM Evento WHERE nombre LIKE :busqueda";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("busqueda", busquedaNormalizada + "%");  // Concatenamos el valor del nombre con "%"
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

    @Override
    public List<Evento> obtenerEventosOrdenadosPorFecha() {
        String hql = "FROM Evento ORDER BY fecha ASC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql, Evento.class);
        return query.getResultList();
    }

    @Override
    public List<Evento> buscarEventosPorCiudad(String nombreCiudad) {
        String hql = "FROM Evento WHERE ciudad.nombre = :nombreCiudad";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreCiudad", nombreCiudad);
        return query.getResultList();
    }

    @Override
    public List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        String hql = "FROM Evento WHERE fecha >= :fechaInicio AND fecha <= :fechaFin ORDER BY fecha ASC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql, Evento.class);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
  
    @Override
    public List<Evento> buscarEventosPorProvincia(String nombreProvincia) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreProvincia", nombreProvincia);
        return query.getResultList();
    }

    @Override
    public List<Evento> buscarEventosPorCiudadYNombre(String nombreCiudad, String busqueda) {
        String hql = "FROM Evento WHERE ciudad.nombre = :nombreCiudad AND lower(nombre) LIKE lower(:busqueda)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreCiudad", nombreCiudad);
        query.setParameter("busqueda", busqueda.toLowerCase() + "%");  // Convertir búsqueda a minúsculas
        return query.getResultList();
    }

    @Override
    public List<Evento> buscarEventosPorProvinciaYNombre(String nombreProvincia, String busqueda) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia AND lower(e.nombre) LIKE lower(:busqueda)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreProvincia", nombreProvincia);
        query.setParameter("busqueda", busqueda.toLowerCase() + "%");  // Convertir búsqueda a minúsculas
        return query.getResultList();
    }

}
