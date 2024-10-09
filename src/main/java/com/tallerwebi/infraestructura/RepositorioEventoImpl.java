package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EventoNoEncontradoException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
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

        if (eventoInexistente == null) {
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
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos en esa fecha");
        }

        return eventosEncontrados;
    }
//TextoDelBuscador

    @Override
    public List<Evento> buscarEventosPorNombre(String busqueda) {
        String busquedaNormalizada = busqueda.toLowerCase();
        String hql = "FROM Evento WHERE nombre LIKE :busqueda";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", busqueda + "%");  // Concatenamos el valor del nombre con "%"
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos relacionados a " + busqueda);
        }

        return eventosEncontrados;

    }

    @Override
    public Evento obtenerEventoPorId(Long id) {
        String hql = "FROM Evento WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        try {
            return (Evento) query.getSingleResult() ; // Retorna el evento si lo encuentra
        } catch (NoResultException e) {
            throw new EventoNoEncontradoException("No se encontró el evento con id: " + id);
        }

    }

    @Override
    public List<Evento> obtenerEventosPorCategoria(String categoria) {
        String hql = "FROM Evento WHERE categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("categoria", categoria);
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos de la categoria: " + categoria);
        }
        return eventosEncontrados;
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
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos en la ciudad: " + nombreCiudad);
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> obtenerEventosDentroDeUnRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        String hql = "FROM Evento WHERE fecha >= :fechaInicio AND fecha <= :fechaFin ORDER BY fecha ASC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos entre las fechas dadas");
        }
        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorProvincia(String nombreProvincia) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreProvincia", nombreProvincia);
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos en la provicia: " + nombreProvincia);
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorCiudadYNombre(String nombreCiudad, String busqueda) {
        String hql = "FROM Evento WHERE ciudad.nombre = :nombreCiudad AND lower(nombre) LIKE lower(:busqueda)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreCiudad", nombreCiudad);
        query.setParameter("busqueda", busqueda.toLowerCase() + "%");  // Convertir búsqueda a minúsculas
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos en la ciudad: " + nombreCiudad + " relacionados a la busqueda: " + busqueda);
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorProvinciaYNombre(String nombreProvincia, String busqueda) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia AND lower(e.nombre) LIKE lower(:busqueda)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreProvincia", nombreProvincia);
        query.setParameter("busqueda", busqueda.toLowerCase() + "%");  // Convertir búsqueda a minúsculas
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos en la provincia: " + nombreProvincia + " relacionados a la busqueda: " + busqueda);
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorNombreYCategoria(String nombre, String categoria) {
        String sentencia = "SELECT e FROM Evento e WHERE lower(e.nombre) LIKE lower(:nombre) AND lower(e.categoria) = lower(:categoria)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia);
        query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
        query.setParameter("categoria", categoria.toLowerCase());
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos de la categoria: " + categoria + " relacionados a la busqueda: " + nombre);
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorProvinciaYCategoria(String nombreProvincia, String categoria) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia AND lower(e.categoria) = lower(:categoria)";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreProvincia", nombreProvincia);
        query.setParameter("categoria", categoria);
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos de la categoria: " + categoria + " en la provincia: " + nombreProvincia );
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorProvinciaCiudadYCategoria(String nombreCiudad, String nombreProvincia, String categoria) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia AND  e.ciudad.nombre = :nombreCiudad AND e.categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombreProvincia", nombreProvincia);
        query.setParameter("nombreCiudad", nombreCiudad);
        query.setParameter("categoria", categoria);
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos de la categoria: " + categoria + " en la ciudad: " + nombreCiudad);
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorNombreCategoriaYProvincia(String nombre, String nombreProvincia, String categoria) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia AND lower(e.nombre) LIKE lower(:nombre) AND e.categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", nombre.toLowerCase() + "%");
        query.setParameter("nombreProvincia", nombreProvincia);
        query.setParameter("categoria", categoria);
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos de la categoria: " + categoria + " en la provincia: " + nombreProvincia + " relacionados a " + nombre);
        }

        return eventosEncontrados;
    }

    @Override
    public List<Evento> buscarEventosPorNombreCategoriaProvinciaYCiudad(String nombre, String nombreProvincia, String nombreCiudad, String categoria) {
        String hql = "SELECT e FROM Evento e WHERE e.ciudad.provincia.nombre = :nombreProvincia AND lower(e.nombre) LIKE lower(:nombre) AND e.ciudad.nombre = :nombreCiudad AND e.categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", nombre.toLowerCase() + "%");
        query.setParameter("nombreCiudad", nombreCiudad);
        query.setParameter("nombreProvincia", nombreProvincia);
        query.setParameter("categoria", categoria);
        List<Evento> eventosEncontrados = query.getResultList();
        if (eventosEncontrados.isEmpty()) {
            throw new EventoNoEncontradoException("No se encontraron eventos de la categoria: " + categoria + " en la ciudad: " + nombreProvincia + " relacionados a " + nombre);
        }

        return eventosEncontrados;
    }


}



