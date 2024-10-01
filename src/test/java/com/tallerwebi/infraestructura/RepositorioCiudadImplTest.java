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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioCiudadImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCiudad repositorioCiudad;

    @BeforeEach
    public void init() {
        this.repositorioCiudad = new RepositorioCiudadImpl(sessionFactory) {
        };
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioCiudadaCuandoGuardoUnaSeAñadeALaBaseDeDatos () {
        Ciudad moron = new Ciudad();
        moron.setNombre("Morón");
        this.repositorioCiudad.guardar(moron);

        String hql = "FROM Ciudad WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Morón");
        Ciudad ciudadObtenida = (Ciudad) query.getSingleResult();

        assertThat(ciudadObtenida, equalTo(moron));
        assertThat("Morón", equalTo(ciudadObtenida.getNombre()));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioConVariasCiudadesPuedoObtenerLaDeseadaAPartirDeSuNombre () {
        Ciudad moron = new Ciudad();
        moron.setNombre("Morón");
        this.repositorioCiudad.guardar(moron);

        Ciudad merlo = new Ciudad();
        merlo.setNombre("Merlo");
        this.repositorioCiudad.guardar(merlo);

        Ciudad ciudadObtenida = this.repositorioCiudad.obtenerCiudadPorNombre("Merlo");
        assertThat(ciudadObtenida, equalTo(merlo));
        assertThat("Merlo", equalTo(ciudadObtenida.getNombre()));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioCiudadPuedoObtenerTodasLasQueExistenEnMiBaseDeDatos () {
        Ciudad moron = new Ciudad();
        moron.setNombre("Morón");
        this.repositorioCiudad.guardar(moron);

        Ciudad merlo = new Ciudad();
        merlo.setNombre("Merlo");
        this.repositorioCiudad.guardar(merlo);

        List<Ciudad> ciudadesObtenidas = this.repositorioCiudad.obtenerTodasLasCiudades();

        assertThat(ciudadesObtenidas.size(), is(2));
        assertThat(ciudadesObtenidas, containsInAnyOrder(moron, merlo));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioConCiudadesPodemosEliminarAlgunaDeLasQueTenemosGuardadas () {
        Ciudad moron = new Ciudad();
        moron.setNombre("Morón");
        this.repositorioCiudad.guardar(moron);
        this.repositorioCiudad.eliminarCiudad(moron);

        List<Ciudad> ciudades = this.repositorioCiudad.obtenerTodasLasCiudades();
        assertThat(ciudades, empty());
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaCiudadEnBsAsYUnaEnTDFDebeDevolverUnaSolaCiudadCuandoBuscoCiudadesEnBsAs () {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.sessionFactory.getCurrentSession().save(buenosAires);

        Ciudad moron = new Ciudad();
        moron.setNombre("Morón");
        moron.setProvincia(buenosAires);
        this.repositorioCiudad.guardar(moron);

        Provincia tierraDelFuego = new Provincia();
        tierraDelFuego.setNombre("Tierra del Fuego");
        this.sessionFactory.getCurrentSession().save(tierraDelFuego);

        Ciudad ushuaia = new Ciudad();
        ushuaia.setNombre("Ushuaia");
        ushuaia.setProvincia(tierraDelFuego);
        this.repositorioCiudad.guardar(ushuaia);

        List<Ciudad> ciudadesEsperadas = this.repositorioCiudad.obtenerCiudadesPorProvincia("Buenos Aires");
        assertThat(ciudadesEsperadas.size(), is(1));
        assertThat(ciudadesEsperadas.get(0).getNombre(), equalTo("Morón"));
        assertThat(ciudadesEsperadas.get(0).getProvincia().getNombre(), equalTo("Buenos Aires"));
    }

}
