package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

  /*  @Override
    @Transactional
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }*/

@Override
@Transactional
public void guardar(@org.jetbrains.annotations.NotNull Usuario usuario) {
    String sql = "INSERT INTO Usuario (email, password, nombre, apellido, telefono, dni) VALUES (:email, :password, :nombre, :apellido, :telefono, :dni)";

    Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
    query.setParameter("email", usuario.getEmail());
    query.setParameter("password", usuario.getPassword());
    query.setParameter("nombre", usuario.getNombre());
    query.setParameter("apellido", usuario.getApellido());
    query.setParameter("telefono", usuario.getTelefono());
    query.setParameter("dni", usuario.getDni());

    query.executeUpdate();
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

}
