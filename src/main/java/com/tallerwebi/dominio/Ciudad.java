package com.tallerwebi.dominio;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String nombre;

    @ManyToOne (fetch = FetchType.EAGER)
    private Provincia provincia;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(id, ciudad.id) && Objects.equals(nombre, ciudad.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}

