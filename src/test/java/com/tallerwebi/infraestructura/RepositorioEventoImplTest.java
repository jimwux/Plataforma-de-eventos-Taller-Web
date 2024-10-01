package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.RepositorioEvento;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.context.annotation.EnableLoadTimeWeaving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

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
    public void dadoQueExisteUnRepositorioEventoCuandoGuardoUnEventoEntoncesLoEncuentroEnLaBaseDeDatos() {
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
    public void dadoQueExisteUnRepositorioEventoCuandoGuardo3EventosEntoncesEncuentro3EventosEnLaBaseDeDatos() {
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
    public void dadoQueExisteUnRepositorioEventoCuandoActualizoSuLugarLoEncuentroEnLaBaseDeDatos() {
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
    public void dadoQueExisteUnRepositorioEventoCuandoActualizoSuNombreLoEncuentroEnLaBaseDeDatos() {
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

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoEliminoUnEventoEntoncesNoLoEncuentroEnLaBaseDeDatos(){
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        this.repositorioEvento.guardar(evento);

        this.repositorioEvento.eliminarEvento(evento);

        String hql = "FROM Evento WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Creamfields 2024");

        List<Evento> eventos = query.getResultList();
        assertThat(eventos, empty());
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueNoExisteUnEventoCuandoIntentoActualizarEntoncesSeGeneraUnaExcepcion() {
        Evento eventoInexistente = new Evento();

        eventoInexistente.setId(999L);
        eventoInexistente.setLugar("Lugar Inexistente");

        assertThrows(EntityNotFoundException.class, () -> {
            this.repositorioEvento.actualizarLugar(eventoInexistente);
        });
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoBuscoEventosPorFechaEntoncesLosEncuentroEnLaBaseDeDatos() {
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024,11,16), "Parque de la Ciudad");
        Evento evento2 = new Evento("TANGO SHOW",LocalDate.of(2024,11,16), "Parque de los Patos");
        Evento evento3 = new Evento("Fiesta Wasabi",LocalDate.of(2025,2,6), "CABA");

        this.repositorioEvento.guardar(evento);
        this.repositorioEvento.guardar(evento2);
        this.repositorioEvento.guardar(evento3);

    List<Evento> eventosEncontrados = this.repositorioEvento.obtenerLosEventosPorFecha(LocalDate.of(2024, 11,16));
    assertThat(eventosEncontrados.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoConEventosPuedoObtenerAquellosQueCorrespondenAMiBusquedaPorNombre () {
        Evento eventoUno = new Evento("Creamfields 2024", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        this.repositorioEvento.guardar(eventoUno);
        Evento eventoDos = new Evento("Lollapalooza", LocalDate.of(2025, 03, 21), "Hipodromo de San Isidro");
        this.repositorioEvento.guardar(eventoDos);
        Evento eventoTres = new Evento("Cretive Party", LocalDate.of(2025, 05, 15), "Hipodromo de San Isidro");
        this.repositorioEvento.guardar(eventoTres);

        String busqueda = "cre";
        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorNombre(busqueda);

        List<Evento> eventosEsperados = Arrays.asList(eventoUno, eventoTres);
        assertThat(eventosEncontrados.size(), equalTo(2));

        // Verifica que cada evento esperado está en los resultados encontrados
        int iterador = 0;
        for (Evento evento : eventosEsperados) {
            assertThat(eventosEncontrados.get(iterador), equalTo(evento));
            iterador++;
        }
    }


    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoPuedaObtenerUnEventoPorId(){
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        this.repositorioEvento.guardar(evento);
        Long idDelEvento = evento.getId();

        Evento eventoObtenido = this.repositorioEvento.obtenerEventoPorId(idDelEvento);

        assertThat(eventoObtenido.getId(), equalTo(idDelEvento));
        assertThat(eventoObtenido, equalTo(evento));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoBuscoEventosOrdenadosPorFechaEntoncesEstanOrdenadosCorrectamente() {

        seCreanYGuardanTresEventosConDistintasFechas();

        List<Evento> eventosOrdenados = seObtienenLosEventosOrdenadosPorFecha();

        seVerificaElOrdenDeLosMismosComparandoSusFechas(eventosOrdenados);


    }

    private void seVerificaElOrdenDeLosMismosComparandoSusFechas(List<Evento> eventosOrdenados) {
        assertThat(eventosOrdenados.size(), equalTo(3));
        assertThat(eventosOrdenados.get(0).getFecha(), equalTo(LocalDate.of(2024, 11, 16)));
        assertThat(eventosOrdenados.get(1).getFecha(), equalTo(LocalDate.of(2024, 12, 25)));
        assertThat(eventosOrdenados.get(2).getFecha(), equalTo(LocalDate.of(2025, 1, 15)));
    }

    private List<Evento> seObtienenLosEventosOrdenadosPorFecha() {
        return  this.repositorioEvento.obtenerEventosOrdenadosPorFecha();
    }

    private void seCreanYGuardanTresEventosConDistintasFechas() {

        Evento evento1 = new Evento("Fiesta de Verano", LocalDate.of(2025, 1, 15), "Parque de la Ciudad");
        Evento evento2 = new Evento("Festival de Invierno", LocalDate.of(2024, 12, 25), "CABA");
        Evento evento3 = new Evento("Primavera Sound", LocalDate.of(2024, 11, 16), "Parque Central");


        this.repositorioEvento.guardar(evento1);
        this.repositorioEvento.guardar(evento2);
        this.repositorioEvento.guardar(evento3);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoCuandoBuscoEventosDentroDeUnRangoDeFechasEntoncesTengoUnaListaDeEstas() {

        seCreanYGuardanTresEventosConDistintasFechas();

        List<Evento> eventosEnElRango = seObtienenLosEventosDentroDeUnRangoDeFechas();

        seVerificaElOrdenDeLosMismosYQueEstosEstenEnElRangoDeFechasAdecuado(eventosEnElRango);


    }

    private void seVerificaElOrdenDeLosMismosYQueEstosEstenEnElRangoDeFechasAdecuado(List<Evento> eventosEnElRango) {
        assertThat(eventosEnElRango.size(), equalTo(2));
        assertThat(eventosEnElRango.get(0).getFecha(), equalTo(LocalDate.of(2024, 11, 16)));
        assertThat(eventosEnElRango.get(1).getFecha(), equalTo(LocalDate.of(2024, 12, 25)));
    }

    private List<Evento> seObtienenLosEventosDentroDeUnRangoDeFechas() {
        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        LocalDate fechaFin = LocalDate.of(2024, 12, 31);

        return  this.repositorioEvento.obtenerEventosDentroDeUnRangoDeFechas( fechaInicio, fechaFin);
    }


}
