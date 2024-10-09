package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Entrada;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.RepositorioEntrada;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioEntradaImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEntrada repositorioEntrada;

    @BeforeEach
    public void init() {
        this.repositorioEntrada = new RepositorioEntradaImpl(sessionFactory);
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueExisteUnaEntradaGeneralEnLaBaseDeDatosObtenerlaPorSuNombre(){
        Entrada entrada = new Entrada();
        entrada.setNombre("General");
        this.sessionFactory.getCurrentSession().save(entrada);

        Entrada entradaConseguida = this.repositorioEntrada.obtenerEntradaPorNombre("General");

        assertThat(entradaConseguida.getNombre(), equalToIgnoringCase(entrada.getNombre()));
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueExistenEntradasParaCadaEventoQueAlBuscarUnEventoNosDevuelvaLasEntradasDelMismo(){
        Evento evento = new Evento("Creamfields 2024", LocalDate.of(2024,11,16), "Parque de la Ciudad");
        Evento evento2 = new Evento("TANGO SHOW",LocalDate.of(2024,11,16), "Parque de los Patos");
        Evento evento3 = new Evento("Fiesta Wasabi",LocalDate.of(2025,2,6), "CABA");

       this.sessionFactory.getCurrentSession().save(evento);
        this.sessionFactory.getCurrentSession().save(evento2);
        this.sessionFactory.getCurrentSession().save(evento3);

        Entrada entrada = new Entrada();
       entrada.setNombre("General");
       entrada.setEvento(evento);
       this.sessionFactory.getCurrentSession().save(entrada);

       Entrada entradaDos = new Entrada();
       entradaDos.setNombre("Vip");
       entradaDos.setEvento(evento);
       this.sessionFactory.getCurrentSession().save(entradaDos);

        Entrada entradaTres = new Entrada();
        entradaTres.setNombre("Campo");
        entradaTres.setEvento(evento2);
        this.sessionFactory.getCurrentSession().save(entradaTres);

       List<Entrada> entradasObtenidas = this.repositorioEntrada.obtenerEntradasDeUnEvento(evento.getId());

       assertThat(entradasObtenidas.size(), equalTo(2));
       assertThat(entradasObtenidas.get(0).getNombre(), equalToIgnoringCase(entrada.getNombre()));
       assertThat(entradasObtenidas.get(1).getNombre(), equalToIgnoringCase(entradaDos.getNombre()));
    }





}
