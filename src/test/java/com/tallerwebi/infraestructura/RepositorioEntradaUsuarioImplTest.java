package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioEntradaUsuarioImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEntradaUsuario repositorioEntradaUsuario;

    @BeforeEach
    public void init() {
        this.repositorioEntradaUsuario = new RepositorioEntradaUsuarioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEntradaUsuarioCuandoGuardoUnaEntradaUsuarioEntoncesLaEncuentroEnLaBaseDeDatos() {
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);

        Entrada entrada = new Entrada();
        this.sessionFactory.getCurrentSession().save(entrada);

        String codigoCompra = "codigo123";
        EntradaUsuario entradaUsuario = new EntradaUsuario(usuario, entrada, codigoCompra);

        this.repositorioEntradaUsuario.guardar(entradaUsuario);

        String hql = "FROM EntradaUsuario WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", entradaUsuario.getId());
        EntradaUsuario entradaUsuarioEncontrada = (EntradaUsuario) query.getSingleResult();

        assertThat(entradaUsuarioEncontrada, equalTo(entradaUsuario));

    }

}
