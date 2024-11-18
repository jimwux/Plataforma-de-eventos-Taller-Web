package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.UsuarioVistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario{

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public Usuario obtenerUsuarioVistaDTODelRepo(String email){
        return repositorioUsuario.buscar(email);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        this.repositorioUsuario.modificar(usuario);
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

}
