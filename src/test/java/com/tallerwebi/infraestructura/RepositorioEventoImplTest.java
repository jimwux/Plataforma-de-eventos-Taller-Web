package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.RepositorioEvento;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioEventoImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEvento repositorioEvento;

    @BeforeEach
    public void init() {
        this.repositorioEvento = new RepositorioEventoImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoGuardoUnEventoEntoncesLoEncuentroEnLaBaseDeDatos () {
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        this.repositorioEvento.guardar(evento);

        String hql = "FROM Evento WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Creamfields 2024");
        Evento eventoEncontrado = (Evento) query.getSingleResult();

        assertThat(eventoEncontrado, equalTo(evento));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoGuardo3EventosEntoncesEncuentro3EventosEnLaBaseDeDatos () {
        Evento eventoUno = new Evento("Evento uno", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        this.repositorioEvento.guardar(eventoUno);
        Evento eventoDos = new Evento("Evento dos", LocalDate.of(2024, 8, 12), "Parque de la Ciudad");
        this.repositorioEvento.guardar(eventoDos);
        Evento eventoTres = new Evento("Evento tres", LocalDate.of(2024, 5, 21), "Parque de la Ciudad");
        this.repositorioEvento.guardar(eventoTres);

        List<Evento> eventosEncontrados = this.repositorioEvento.obtenerTodosLosEventos();

        int cantidadEsperada = 3;
        assertThat(eventosEncontrados.size(), equalTo(cantidadEsperada));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoActualizoSuLugarLoEncuentroEnLaBaseDeDatos () {
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        this.repositorioEvento.guardar(evento);

        String nuevoLugar = "Hipodromo de Palermo";
        evento.setLugar(nuevoLugar);
        this.repositorioEvento.actualizarLugar(evento);

        String hql = "FROM Evento WHERE lugar = :lugar";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("lugar", "Hipodromo de Palermo");
        Evento eventoEncontrado = (Evento) query.getSingleResult();

        assertThat(eventoEncontrado.getLugar(), equalTo(nuevoLugar));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoActualizoSuNombreLoEncuentroEnLaBaseDeDatos () {
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        this.repositorioEvento.guardar(evento);

        String nuevoNombre = "Lollapalooza 2024";
        evento.setNombre(nuevoNombre);
        this.repositorioEvento.actualizarNombre(evento);

        String hql = "FROM Evento WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", nuevoNombre);
        Evento eventoEncontrado = (Evento) query.getSingleResult();

        assertThat(eventoEncontrado.getNombre(), equalTo(nuevoNombre));
    }


}
