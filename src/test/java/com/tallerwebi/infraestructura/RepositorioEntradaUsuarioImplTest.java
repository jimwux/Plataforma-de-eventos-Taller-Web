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
import java.util.List;

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

    @Test
    @Transactional
    @Rollback
    public void dadoQuePodemosFiltrarPorCategoriaFiestaQueSeMuestrenLasEntradasUsuarioDeDichoEventoConEsaCategoria(){
        Evento evento1 = new Evento();
        evento1.setCategoria("Fiesta");
        evento1.setNombre("Tropitango");

        Evento evento2 = new Evento();
        evento2.setCategoria("Fiesta");
        evento2.setNombre("Wasabi");

        Evento evento3 = new Evento();
        evento3.setCategoria("Otro");
        evento3.setNombre("Jamaica");

        this.sessionFactory.getCurrentSession().save(evento1);
        this.sessionFactory.getCurrentSession().save(evento2);
        this.sessionFactory.getCurrentSession().save(evento3);

        Entrada entrada1 = new Entrada();
        Entrada entrada2 = new Entrada();
        Entrada entrada3 = new Entrada();
        Entrada entrada4 = new Entrada();

        entrada1.setEvento(evento1);
        entrada1.setNombre("General");
        entrada2.setEvento(evento1);
        entrada1.setNombre("Vip");
        entrada3.setEvento(evento2);
        entrada3.setNombre("Platea");
        entrada4.setEvento(evento3);
        entrada4.setNombre("ULTRAVIP");

        this.sessionFactory.getCurrentSession().save(entrada1);
        this.sessionFactory.getCurrentSession().save(entrada2);
        this.sessionFactory.getCurrentSession().save(entrada3);
        this.sessionFactory.getCurrentSession().save(entrada4);

        Usuario usuario = new Usuario();
        String correo = "solmaca0550@gmail.com";
        usuario.setEmail(correo);

        this.sessionFactory.getCurrentSession().save(usuario);

        EntradaUsuario entradaUsuario = new EntradaUsuario();
        entradaUsuario.setUsuario(usuario);
        entradaUsuario.setEntrada(entrada1);

        EntradaUsuario entradaUsuario1 = new EntradaUsuario();
        entradaUsuario1.setUsuario(usuario);
        entradaUsuario1.setEntrada(entrada1);

        EntradaUsuario entradaUsuario2 = new EntradaUsuario();
        entradaUsuario2.setUsuario(usuario);
        entradaUsuario2.setEntrada(entrada2);

        EntradaUsuario entradaUsuario3 = new EntradaUsuario();
        entradaUsuario3.setUsuario(usuario);
        entradaUsuario3.setEntrada(entrada3);

        EntradaUsuario entradaUsuario4 = new EntradaUsuario();
        entradaUsuario4.setUsuario(usuario);
        entradaUsuario4.setEntrada(entrada3);

        EntradaUsuario entradaUsuario5 = new EntradaUsuario();
        entradaUsuario5.setUsuario(usuario);
        entradaUsuario5.setEntrada(entrada4);

        this.sessionFactory.getCurrentSession().save(entradaUsuario);
        this.sessionFactory.getCurrentSession().save(entradaUsuario1);
        this.sessionFactory.getCurrentSession().save(entradaUsuario2);
        this.sessionFactory.getCurrentSession().save(entradaUsuario3);
        this.sessionFactory.getCurrentSession().save(entradaUsuario4);
        this.sessionFactory.getCurrentSession().save(entradaUsuario5);

       List<EntradaUsuario> entradasUsuario = this.repositorioEntradaUsuario.obtenerEntradaPorUsuarioYCategoria(correo, "Fiesta");

        assertThat(entradasUsuario.size(), equalTo(5));
    }



}
