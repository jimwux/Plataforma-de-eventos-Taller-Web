package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Entrada;
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

import javax.transaction.Transactional;


import static org.hamcrest.MatcherAssert.assertThat;

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



}
