package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import javax.persistence.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Usuario buscarUsuario(String email, String password) {
        final Session session = sessionFactory.getCurrentSession();
        Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();

        if (usuario != null && new BCryptPasswordEncoder().matches(password, usuario.getPassword())) {
            return usuario;
        }

        return null;
    }

    @Override
    @Transactional
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }


    @Override
    @Transactional
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }


    @Override
    @Transactional
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }


    @Override
    public void eliminarUsuario(Long usuarioId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Usuario u WHERE u.id = :usuarioId";

        Query query = session.createQuery(hql);
        query.setParameter("usuarioId", usuarioId);
        query.executeUpdate();
    }




}
