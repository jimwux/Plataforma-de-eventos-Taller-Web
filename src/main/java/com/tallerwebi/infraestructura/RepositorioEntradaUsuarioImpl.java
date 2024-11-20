package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.Entrada;
import com.tallerwebi.dominio.EntradaUsuario;
import com.tallerwebi.dominio.RepositorioEntradaUsuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.Query;
import java.util.List;


@Repository
public class RepositorioEntradaUsuarioImpl implements RepositorioEntradaUsuario {

    SessionFactory sessionFactory;

    @Autowired
    public RepositorioEntradaUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(EntradaUsuario entradaUsuario) {
        this.sessionFactory.getCurrentSession().save(entradaUsuario);
    }

    @Override
    public List<EntradaUsuario> obtenerEntradaPorUsuario(String email) {
        String sentencia_sql = "SELECT e FROM EntradaUsuario e WHERE e.usuario.email = :email ORDER BY e.fecha DESC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia_sql);
        query.setParameter("email", email);
        List<EntradaUsuario> entradasUsuario = query.getResultList();
        return entradasUsuario;
    }

    @Override
    public List<EntradaUsuario> obtenerEntradaPorUsuarioYCategoria(String email, String categoria) {
        String sentencia_sql = "SELECT e FROM EntradaUsuario e WHERE e.usuario.email = :email AND e.entrada.evento.categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia_sql);
        query.setParameter("email", email);
        query.setParameter("categoria", categoria);

      @Override 
    public List<EntradaUsuario> obtenerEntradasDeUnaTransaccion(String codigoTransaccion) {
        String sentencia_sql = "SELECT e FROM EntradaUsuario e WHERE e.compraId = :codigoTransaccion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(sentencia_sql);
        query.setParameter("codigoTransaccion", codigoTransaccion);
        List<EntradaUsuario> entradasUsuario = query.getResultList();
        return entradasUsuario;
    }

}
