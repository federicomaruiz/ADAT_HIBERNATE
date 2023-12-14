package models;

import org.hibernate.Session;

import java.util.List;

public class LibroDAO {

    private final Session session;

    public LibroDAO(Session session) {
        this.session = session;
    }

    public void crearLibro(String nombreLibro, String añoLibro, String nombreAutor) {

        try {
            session.beginTransaction();
            int año = Integer.parseInt(añoLibro);
            Autor autor = obtenerOcrearAutor(nombreAutor);
            Libro libro = new Libro();
            libro.setNombre(nombreLibro);
            libro.setAño(año);
            libro.setAutorByIdautor(autor);
            session.persist(libro);
            session.getTransaction().commit();
            System.out.println("Libro creado");
        } catch (Exception e) {
            System.out.println("Error al crear el libro");
        }

    }

    public void eliminarLibro(int idLibro) {
        Libro libro = session.get(Libro.class, idLibro);

        if (libro != null) {
            session.beginTransaction();
            session.remove(libro);
            session.getTransaction().commit();
            System.out.println("Libro eliminado");
        } else {
            System.out.println("No se encontró el libro con el ID: " + idLibro);
        }
    }

    public List<Libro> obtenerTodosLosLibros() {
        session.beginTransaction();
        List<Libro> libros = session.createQuery("FROM Libro").getResultList();
        session.getTransaction().commit();
        return libros;
    }

    private Autor obtenerOcrearAutor(String nombreAutor) {
        Autor autor = (Autor) session.createQuery("FROM Autor WHERE nombre = :nombre")
                .setParameter("nombre", nombreAutor)
                .uniqueResult();

        if (autor == null) {
            autor = new Autor();
            autor.setNombre(nombreAutor);
            session.persist(autor);
        }

        return autor;
    }

    public void actualizarLibro(int idLibro, String nuevoNombre, int nuevoAño, String nombreAutor) {
        session.beginTransaction();
        Autor autor = obtenerOcrearAutor(nombreAutor);
        Libro libro = obtenerLibroPorId(idLibro);
        if (libro != null) {
            libro.setNombre(nuevoNombre);
            libro.setAño(nuevoAño);
            libro.setAutorByIdautor(autor);
            session.getTransaction().commit();
            System.out.println("Libro actualizado");
        } else {
            System.out.println("No se encontró el libro con el ID: " + idLibro);
        }
    }

    private Libro obtenerLibroPorId(int idLibro) {
        return session.get(Libro.class, idLibro);

    }

    public List<Libro> obtenerLibrosPorAutor(int idAutor) {
        String hql = "FROM Libro WHERE autorByIdautor.id = :idAutor";
        List<Libro> libros = session.createQuery(hql
                        , Libro.class)
                .setParameter("idAutor", idAutor)
                .getResultList();

        return libros;
    }
}
