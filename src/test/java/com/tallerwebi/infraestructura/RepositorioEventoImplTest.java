package com.tallerwebi.infraestructura;

import com.tallerwebi.config.HibernateConfig;
import com.tallerwebi.dominio.Ciudad;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.Provincia;
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
import static org.mockito.Mockito.when;

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
    public void dadoQueExisteUnRepositorioEventoConEventosPuedoObtenerLosQuePertenecenAUnaCiudad () {
        Ciudad moron = this.creoUnaCiudadYLaGuardo("Morón");
        Ciudad merlo = this.creoUnaCiudadYLaGuardo("Merlo");

        Evento eventoUno = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Parense de Manos", moron);
        Evento eventoDos = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Lollapalooza", merlo);
        Evento eventoTres = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Aventuras Perrunas", moron);

        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorCiudad("Morón");

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
    public void dadoQueExisteUnRepositorioEventoConEventosPuedoObtenerLosQuePertenecenAUnaProvincia () {
        Provincia buenosAires = this.creoUnaProvinciaYLaGuardo("Buenos Aires");
        Provincia cordoba = this.creoUnaProvinciaYLaGuardo("Córdoba");

        Ciudad moron = this.creoUnaCiudadLeAsignoUnaProvinciaYLaGuardo("Morón", buenosAires);
        Ciudad merlo = this.creoUnaCiudadLeAsignoUnaProvinciaYLaGuardo("Merlo", buenosAires);
        Ciudad villaCarlosPaz = this.creoUnaCiudadLeAsignoUnaProvinciaYLaGuardo("Villa Carlos Paz", cordoba);

        Evento eventoUno = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Parense de Manos", moron);
        Evento eventoDos = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Parense de Manos", villaCarlosPaz);
        Evento eventoTres = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Aventuras Perrunas", merlo);

        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorProvincia("Buenos Aires");

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
// test de casos negativos // tener en cuenta si se saca el lower (probar ingresando con todos los casos)
    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoConEventosPuedoObtenerLosQuePertenecenAUnaCiudadYLoQueLlegoPorBusqueda () {
        Ciudad moron = this.creoUnaCiudadYLaGuardo("Morón");
        Ciudad merlo = this.creoUnaCiudadYLaGuardo("Merlo");

        Evento eventoUno = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Parense de Manos", moron);
        Evento eventoDos = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Aventuras Perrunas", merlo);
        Evento eventoTres = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Aventuras Perrunas", moron);

        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorCiudadYNombre("Morón", "Aventuras Perrunas");

        assertThat(eventosEncontrados, hasSize(1));
        assertThat(eventosEncontrados.get(0), equalTo(eventoTres));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoConEventosPuedoObtenerLosQuePertenecenAUnaProvinciaYTienenUnNombreParticular () {
        Provincia buenosAires = this.creoUnaProvinciaYLaGuardo("Buenos Aires");
        Provincia cordoba = this.creoUnaProvinciaYLaGuardo("Córdoba");

        Ciudad moron = this.creoUnaCiudadLeAsignoUnaProvinciaYLaGuardo("Morón", buenosAires);
        Ciudad merlo = this.creoUnaCiudadLeAsignoUnaProvinciaYLaGuardo("Merlo", buenosAires);
        Ciudad villaCarlosPaz = this.creoUnaCiudadLeAsignoUnaProvinciaYLaGuardo("Villa Carlos Paz", cordoba);

        Evento eventoUno = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Parense de Manos", moron);
        Evento eventoDos = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Parense de Manos", villaCarlosPaz);
        Evento eventoTres = this.creoUnEventoLeAsignoUnaCiudadYLoGuardo("Aventuras Perrunas", merlo);

        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorProvinciaYNombre("Buenos Aires", "Parense de Manos");

        assertThat(eventosEncontrados, hasSize(1));
        assertThat(eventosEncontrados.get(0), equalTo(eventoUno));
    }

    private Provincia creoUnaProvinciaYLaGuardo (String nombre) {
        Provincia provincia = new Provincia();
        provincia.setNombre(nombre);
        this.sessionFactory.getCurrentSession().save(provincia);
        return provincia;
    }

    private Ciudad creoUnaCiudadYLaGuardo (String nombre) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(nombre);
        this.sessionFactory.getCurrentSession().save(ciudad);
        return ciudad;
    }

    private Ciudad creoUnaCiudadLeAsignoUnaProvinciaYLaGuardo (String nombre, Provincia provincia) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(nombre);
        ciudad.setProvincia(provincia);
        this.sessionFactory.getCurrentSession().save(ciudad);
        return ciudad;
    }

    private Evento creoUnEventoLeAsignoUnaCiudadYLoGuardo (String nombre, Ciudad ciudad) {
        Evento evento = new Evento();
        evento.setNombre(nombre);
        evento.setCiudad(ciudad);
        this.repositorioEvento.guardar(evento);
        return evento;
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEventoPuedaObtenerEventosPorSuCategoria(){
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024, 11, 16), "Parque de la Ciudad");
        evento.setCategoria("concierto");
        this.repositorioEvento.guardar(evento);
        Evento eventoDos = new Evento("Lollapalooza", LocalDate.of(2025, 03, 21), "Hipodromo de San Isidro");
        eventoDos.setCategoria("otro");
        this.repositorioEvento.guardar(eventoDos);
        Evento eventoTres = new Evento("Cretive Party", LocalDate.of(2025, 05, 15), "Hipodromo de San Isidro");
        eventoTres.setCategoria("concierto");
        this.repositorioEvento.guardar(eventoTres);

        List<Evento> eventos = this.repositorioEvento.obtenerEventosPorCategoria("concierto");

        List<Evento> esperados = new ArrayList<>();
        esperados.add(evento);
        esperados.add(eventoTres);


        assertThat(eventos.size(), equalTo(2));
        assertThat(eventos, equalTo(esperados));
        assertThat(eventos, containsInAnyOrder(evento, eventoTres));
    }


    @Test
    @Transactional
    @Rollback
    public void dadoQueSePuedeFiltrarPorBusquedaDeNombreDelEventoYPorCategoriaQueAlFiltrarPorAmbosCasosDevuelvaLoPropio(){
        Evento evento = new Evento();
        evento.setNombre("Fiesta de Verano");
        evento.setCategoria("fiesta");

        this.repositorioEvento.guardar(evento);

        Evento evento2 = new Evento();
        evento2.setNombre("Festival de Invierno");
        evento2.setCategoria("fiesta");

       this.repositorioEvento.guardar(evento2);

        List<Evento> obtenidos = this.repositorioEvento.buscarEventosPorNombreYCategoria("F", "fiesta");

        assertThat(obtenidos.size(), equalTo(2));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenEventosObtenerAquellosQueSeanDeLaProvinciaDeBuenosAiresYTenganLaCategoriaFiesta() {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.sessionFactory.getCurrentSession().save(buenosAires);

        Provincia cordoba = new Provincia();
        cordoba.setNombre("Cordoba");
        this.sessionFactory.getCurrentSession().save(cordoba);

        Ciudad laMatanza = new Ciudad();
        laMatanza.setNombre("La Matanza");
        laMatanza.setProvincia(buenosAires);
        this.sessionFactory.getCurrentSession().save(laMatanza);

        Ciudad laPaz = new Ciudad();
        laPaz.setNombre("La Paz");
        laPaz.setProvincia(cordoba);
        this.sessionFactory.getCurrentSession().save(laPaz);

        Evento evento = new Evento();
        evento.setNombre("Tropitango");
        evento.setCiudad(laMatanza);
        evento.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(evento);

        Evento eventoDos = new Evento();
        eventoDos.setNombre("Barnie");
        eventoDos.setCiudad(laMatanza);
        eventoDos.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(eventoDos);

        Evento eventoTres = new Evento();
        eventoTres.setNombre("Panam");
        eventoTres.setCiudad(laPaz);
        eventoTres.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(eventoTres);

        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorProvinciaYCategoria("Buenos Aires", "fiesta");

        assertThat(eventosEncontrados.size(), equalTo(2));
        assertThat(eventosEncontrados.get(0), equalTo(evento));
        assertThat(eventosEncontrados.get(1), equalTo(eventoDos));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenEventosObtenerAquellosQueSeanDeProvinciaDeCordobaDeLaCiudadDeLaPazYTenganLaCategoriaFiesta() {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.sessionFactory.getCurrentSession().save(buenosAires);

        Provincia cordoba = new Provincia();
        cordoba.setNombre("Cordoba");
        this.sessionFactory.getCurrentSession().save(cordoba);

        Ciudad laMatanza = new Ciudad();
        laMatanza.setNombre("La Matanza");
        laMatanza.setProvincia(buenosAires);
        this.sessionFactory.getCurrentSession().save(laMatanza);

        Ciudad laPaz = new Ciudad();
        laPaz.setNombre("La Paz");
        laPaz.setProvincia(cordoba);
        this.sessionFactory.getCurrentSession().save(laPaz);

        Evento evento = new Evento();
        evento.setNombre("Tropitango");
        evento.setCiudad(laMatanza);
        evento.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(evento);

        Evento eventoDos = new Evento();
        eventoDos.setNombre("Barnie");
        eventoDos.setCiudad(laMatanza);
        eventoDos.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(eventoDos);

        Evento eventoTres = new Evento();
        eventoTres.setNombre("Panam");
        eventoTres.setCiudad(laPaz);
        eventoTres.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(eventoTres);

        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorProvinciaCiudadYCategoria("La Paz","Cordoba", "fiesta");

        assertThat(eventosEncontrados.size(), equalTo(1));
        assertThat(eventosEncontrados.get(0), equalTo(eventoTres));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenEventosQueSePuedanBuscarPorNombreEnElBuscadorProvinciaYCategoria() {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.sessionFactory.getCurrentSession().save(buenosAires);

        Provincia cordoba = new Provincia();
        cordoba.setNombre("Cordoba");
        this.sessionFactory.getCurrentSession().save(cordoba);

        Ciudad laMatanza = new Ciudad();
        laMatanza.setNombre("La Matanza");
        laMatanza.setProvincia(buenosAires);
        this.sessionFactory.getCurrentSession().save(laMatanza);

        Ciudad laPaz = new Ciudad();
        laPaz.setNombre("La Paz");
        laPaz.setProvincia(cordoba);
        this.sessionFactory.getCurrentSession().save(laPaz);

        Evento evento = new Evento();
        evento.setNombre("Tropitango");
        evento.setCiudad(laMatanza);
        evento.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(evento);

        Evento eventoCinco = new Evento();
        eventoCinco.setNombre("Tropi");
        eventoCinco.setCiudad(laPaz);
        eventoCinco.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(eventoCinco);

        Evento eventoDos = new Evento();
        eventoDos.setNombre("Barnie");
        eventoDos.setCiudad(laMatanza);
        eventoDos.setCategoria("familiar");
        this.sessionFactory.getCurrentSession().save(eventoDos);

        Evento eventoTres = new Evento();
        eventoTres.setNombre("Panam");
        eventoTres.setCiudad(laPaz);
        eventoTres.setCategoria("familiar");
        this.sessionFactory.getCurrentSession().save(eventoTres);

        Evento eventoCuatro = new Evento();
        eventoCuatro.setNombre("Piñon Fijo");
        eventoCuatro.setCiudad(laMatanza);
        eventoCuatro.setCategoria("familiar");
        this.sessionFactory.getCurrentSession().save(eventoCuatro);


        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorNombreCategoriaYProvincia("Tro", "Buenos Aires", "fiesta");

        assertThat(eventosEncontrados.size(), equalTo(1));
        assertThat(eventosEncontrados.get(0), equalTo(evento));
    }



    @Test
    @Transactional
    @Rollback
    public void dadoQueSePuedeFiltrarALosEventosQueSeObtenganAquellosQueCoincidanConElNombreConLaProvinciaLaCiudadYLaCategoria() {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.sessionFactory.getCurrentSession().save(buenosAires);

        Provincia cordoba = new Provincia();
        cordoba.setNombre("Cordoba");
        this.sessionFactory.getCurrentSession().save(cordoba);

        Ciudad laMatanza = new Ciudad();
        laMatanza.setNombre("La Matanza");
        laMatanza.setProvincia(buenosAires);
        this.sessionFactory.getCurrentSession().save(laMatanza);

        Ciudad laPaz = new Ciudad();
        laPaz.setNombre("La Paz");
        laPaz.setProvincia(cordoba);
        this.sessionFactory.getCurrentSession().save(laPaz);

        Evento evento = new Evento();
        evento.setNombre("Tropitango");
        evento.setCiudad(laMatanza);
        evento.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(evento);

        Evento eventoCinco = new Evento();
        eventoCinco.setNombre("Tropi");
        eventoCinco.setCiudad(laPaz);
        eventoCinco.setCategoria("fiesta");
        this.sessionFactory.getCurrentSession().save(eventoCinco);

        Evento eventoDos = new Evento();
        eventoDos.setNombre("Barnie");
        eventoDos.setCiudad(laMatanza);
        eventoDos.setCategoria("familiar");
        this.sessionFactory.getCurrentSession().save(eventoDos);

        Evento eventoTres = new Evento();
        eventoTres.setNombre("Panam");
        eventoTres.setCiudad(laPaz);
        eventoTres.setCategoria("familiar");
        this.sessionFactory.getCurrentSession().save(eventoTres);

        Evento eventoCuatro = new Evento();
        eventoCuatro.setNombre("Piñon Fijo");
        eventoCuatro.setCiudad(laMatanza);
        eventoCuatro.setCategoria("familiar");
        this.sessionFactory.getCurrentSession().save(eventoCuatro);


        List<Evento> eventosEncontrados = this.repositorioEvento.buscarEventosPorNombreCategoriaProvinciaYCiudad("Tro", "Buenos Aires", "La Matanza","fiesta");

        assertThat(eventosEncontrados.size(), equalTo(1));
        assertThat(eventosEncontrados.get(0), equalTo(evento));
    }











}
