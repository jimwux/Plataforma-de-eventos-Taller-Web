package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Entrada;
import com.tallerwebi.dominio.RepositorioEntrada;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioEntradaImpl implements RepositorioEntrada {


    SessionFactory sessionFactory;

    @Autowired
    public RepositorioEntradaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Entrada> obtenerTodasLasEntradas() {
        String sentencia_sql = "FROM Entrada";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia_sql);
        return query.getResultList();
    }

    @Override
    public Entrada obtenerEntradaPorId(Long id) {
        String sentencia_sql = "SELECT e FROM Entrada e WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia_sql);
        query.setParameter("id", id);
        return (Entrada) query.getSingleResult();
    }

    @Override
    public Entrada obtenerEntradaPorNombre(String nombre) {
        String sentencia = "FROM Entrada WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia);
        query.setParameter("nombre", nombre);
        return (Entrada) query.getSingleResult();
    }

    @Override
    public List<Entrada> obtenerEntradasDeUnEvento(Long id){
        String sentencia_sql = "SELECT e FROM Entrada e JOIN Evento ev ON e.evento.id = ev.id WHERE ev.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia_sql);
        query.setParameter("id", id);
        return query.getResultList();
    }


}
