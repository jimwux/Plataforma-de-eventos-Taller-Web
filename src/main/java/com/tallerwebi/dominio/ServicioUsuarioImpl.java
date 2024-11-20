package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario{
    private RepositorioUsuario repositorioUsuario;
    private RepositorioEntradaUsuario repositorioEntradaUsuario;
    private RepositorioEntradaCompra repositorioEntradaCompra;
    private RepositorioDatosCompra repositorioDatosCompra;


    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario, RepositorioEntradaUsuario repositorioEntradaUsuario, RepositorioEntradaCompra repositorioEntradaCompra, RepositorioDatosCompra repositorioDatosCompra) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioDatosCompra = repositorioDatosCompra;
        this.repositorioEntradaUsuario = repositorioEntradaUsuario;
        this.repositorioEntradaCompra = repositorioEntradaCompra;
    }

    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuarioMock) {
    }

    public Usuario obtenerUsuarioVistaDTODelRepo(String email){
        return repositorioUsuario.buscar(email);
    }


    @Override
    public void actualizarDatoUsuario(String email, String campo, String nuevoValor) {
        Usuario usuario = this.obtenerUsuarioVistaDTODelRepo(email);
        if (usuario != null) {
            switch (campo.toLowerCase()) {
                case "nombre":
                    usuario.setNombre(nuevoValor);
                    break;
                case "apellido":
                    usuario.setApellido(nuevoValor);
                    break;
                case "email":
                    usuario.setEmail(nuevoValor);
                    break;
                case "telefono":
                    usuario.setTelefono(nuevoValor);
                    break;
                case "dni":
                    usuario.setDni(nuevoValor);
                    break;
                default:
                    throw new IllegalArgumentException ("Dato no válido");
            }
            this.repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public Boolean validarContraseniaActual(String email, String contraseniaActual) {
        Usuario usuario = repositorioUsuario.buscar(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(contraseniaActual, usuario.getPassword());
    }

    @Override
    public void cambiarContrasenia(String email, String nuevaContrasenia ) {
        Usuario usuario = repositorioUsuario.buscar(email);
        if (usuario != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(nuevaContrasenia);
            usuario.setPassword(hashedPassword); // Aplica hash si es necesario
            repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public void eliminarCuentaUsuario(Long usuarioId) {
        repositorioEntradaUsuario.eliminarEntradasUsuario(usuarioId);
        repositorioEntradaCompra.eliminarEntradasCompra(usuarioId);
        repositorioDatosCompra.eliminarDatosCompra(usuarioId);
        repositorioUsuario.eliminarUsuario(usuarioId);
    }



}
