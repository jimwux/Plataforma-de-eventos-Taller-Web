package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.DatosCompra;
import com.tallerwebi.dominio.RepositorioDatosCompra;
import com.tallerwebi.dominio.RepositorioEntrada;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioDatosCompraTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioDatosCompra repositorioDatosCompra;

    @BeforeEach
    public void init() {
        this.repositorioDatosCompra = new RepositorioDatosCompraImpl(sessionFactory);
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueAlRealizarUnaCompraSeSubenSusDatosALaBaseDeDatosPuedoObtenerlosAPartirDeSuCodigoDeTransaccion() {
        DatosCompra datosCompra = new DatosCompra("codigo123", "usuario@example.com");
        repositorioDatosCompra.guardar(datosCompra);

        DatosCompra resultado = repositorioDatosCompra.obtenerCompraPorCodigoTransaccion("codigo123");
        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getCodigoTransaccion(), equalTo("codigo123"));
        assertThat(resultado.getEmailUsuario(), equalTo("usuario@example.com"));
        assertThat(resultado.getEstado(), equalTo("pendiente"));
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueLosDatosDeUnaCompraEstanEnLaBaseDeDatosAlEliminarlosNoPodemosObtenerlos () {
        DatosCompra datosCompra = new DatosCompra("codigo123", "usuario@example.com");
        repositorioDatosCompra.guardar(datosCompra);
        repositorioDatosCompra.eliminarCompraPorCodigoTransaccion("codigo123");

        assertThrows(NoResultException.class, () -> {
            repositorioDatosCompra.obtenerCompraPorCodigoTransaccion("codigo123");
        });
    }


}
