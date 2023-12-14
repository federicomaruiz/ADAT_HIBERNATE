package models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Libro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "año")
    private Integer año;

    // Eliminamos la columna idautor y utilizamos la relación con Autor
    @ManyToOne
    @JoinColumn(name = "idautor", referencedColumnName = "id", nullable = false)
    private Autor autorByIdautor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Autor getAutorByIdautor() {
        return autorByIdautor;
    }

    public void setAutorByIdautor(Autor autorByIdautor) {
        this.autorByIdautor = autorByIdautor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return id == libro.id && Objects.equals(nombre, libro.nombre) && Objects.equals(año, libro.año);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, año);
    }
}
