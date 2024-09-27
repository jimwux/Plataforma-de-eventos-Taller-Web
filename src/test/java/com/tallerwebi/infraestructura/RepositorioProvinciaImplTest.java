package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Provincia;
import com.tallerwebi.dominio.RepositorioProvincia;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioProvinciaImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioProvincia repositorioProvincia;

    @BeforeEach
    public void init() {
        this.repositorioProvincia = new RepositorioProvinciaImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioProvinciaCuandoGuardoUnaLaPuedoObtenerAPartirDeSuNombreDesdeLaBaseDeDatos () {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.repositorioProvincia.guardar(buenosAires);

        Provincia provinciaObtenida = this.repositorioProvincia.obtenerProvinciaPorNombre("Buenos Aires");
        assertThat(provinciaObtenida, equalTo(buenosAires));
        assertThat("Buenos Aires", equalTo(provinciaObtenida.getNombre()));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioConVariasProvinciasPuedoObtenerLaDeseadaAPartirDeSuNombre () {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.repositorioProvincia.guardar(buenosAires);

        Provincia mendoza = new Provincia();
        mendoza.setNombre("Mendoza");
        this.repositorioProvincia.guardar(mendoza);

        Provincia provinciaObtenida = this.repositorioProvincia.obtenerProvinciaPorNombre("Mendoza");
        assertThat(provinciaObtenida, equalTo(mendoza));
        assertThat("Mendoza", equalTo(provinciaObtenida.getNombre()));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioProvinciaPuedoObtenerTodasLasQueExistenEnMiBaseDeDatos () {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.repositorioProvincia.guardar(buenosAires);

        Provincia mendoza = new Provincia();
        mendoza.setNombre("Mendoza");
        this.repositorioProvincia.guardar(mendoza);

        Provincia tierraDelFuego = new Provincia();
        mendoza.setNombre("Tierra del Fuego");
        this.repositorioProvincia.guardar(tierraDelFuego);

        List<Provincia> provinciasObtenidas = this.repositorioProvincia.obtenerTodasLasProvincias();

        assertThat(provinciasObtenidas.size(), is(3));
        assertThat(provinciasObtenidas, containsInAnyOrder(buenosAires, mendoza, tierraDelFuego));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioConProvinciasPodemosEliminarLasQueTenemosGuardadas () {
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        this.repositorioProvincia.guardar(buenosAires);
        this.repositorioProvincia.eliminarProvincia(buenosAires);

        List<Provincia> provincias = this.repositorioProvincia.obtenerTodasLasProvincias();
        assertThat(provincias, empty());
    }

}
