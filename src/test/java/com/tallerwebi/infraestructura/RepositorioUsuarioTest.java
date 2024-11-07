package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    public void init(){
       this.repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueSeRegistraUnUsuarioEsteSeGuardaEnLaBaseDeDatos(){

        // preparacion
        Usuario usuario = new Usuario("a@gmail.com", "12345", "brian", "hidalgo", "1123895568", "23895568");

        // ejecucion
        repositorioUsuario.guardar(usuario);

        // validacion
        Usuario usuarioGuardado = repositorioUsuario.buscar(usuario.getEmail());
        assertThat(usuarioGuardado,is(notNullValue()));
        assertThat(usuarioGuardado.getNombre(), equalTo("brian"));
        assertThat(usuarioGuardado.getEmail(), equalTo("a@gmail.com"));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueQuieroModificarLosDatosDelUsuarioEsteSeModifica(){
        // preparacion
        Usuario usuario = new Usuario("a@gmail.com", "12345", "brian", "hidalgo", "1123895568", "23895568");

        // ejecucion
        usuario.setEmail("b@gmail.com");
        usuario.setPassword("54321");

        // validacion
        assertThat(usuario.getEmail(), equalTo("b@gmail.com"));
        assertThat(usuario.getPassword(), equalTo("54321"));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueHayUnUsuarioGuardadoEnLaBaseDeDatosQuieroBuscarloParaLoguearlo(){
        // preparacion
        Usuario usuario = new Usuario("a@gmail.com", "12345", "brian", "hidalgo", "1123895568", "23895568");

        // ejecucion
        repositorioUsuario.guardar(usuario);
        Usuario usuarioBuscado = repositorioUsuario.buscarUsuario("a@gmail.com", "12345");

        // validacion
        assertThat(usuario,is(notNullValue()));
        assertThat(usuario.getEmail(),equalTo("a@gmail.com"));
        assertThat(usuario.getPassword(),equalTo("12345"));
    }


}
