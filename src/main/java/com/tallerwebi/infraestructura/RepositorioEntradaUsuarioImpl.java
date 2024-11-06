package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EntradaUsuario;
import com.tallerwebi.dominio.RepositorioEntradaUsuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
