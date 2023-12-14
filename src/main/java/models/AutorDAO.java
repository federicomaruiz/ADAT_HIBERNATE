package models;


import org.hibernate.Session;

import java.util.List;

public class AutorDAO {

    private final Session session;

    public AutorDAO(Session session) {
        this.session = session;
    }

    public void crearAutor(String nombre) {
        session.beginTransaction();

        Autor autor = (Autor) session.createQuery("FROM Autor WHERE nombre = :nombre")
                .setParameter("nombre", nombre)
                .uniqueResult();

        if (autor == null) {
            autor = new Autor();
            autor.setNombre(nombre);
            session.persist(autor);
            System.out.println("Autor creado");
        } else {
            System.out.println("El autor ya existe");
        }

        session.getTransaction().commit();
    }

    public void eliminarAutor(int idAutor) {
        Autor autor = session.get(Autor.class, idAutor);

        if (autor != null) {
            session.beginTransaction();
            session.remove(autor);
            session.getTransaction().commit();

            System.out.println("Autor eliminado");
        } else {
            System.out.println("No se encontró el autor con el ID: " + idAutor);
        }
    }

    public List<Autor> obtenerTodosLosAutores() {
        session.beginTransaction();
        List<Autor> autores = session.createQuery("FROM Autor").getResultList();
        session.getTransaction().commit();
        return autores;
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

    public void actualizarAutor(int idAutor, String nuevoNombre) {
        Autor autor = obtenerAutorPorId(idAutor);
        if (autor != null) {
            session.beginTransaction();
            autor.setNombre(nuevoNombre);
            session.getTransaction().commit();
            System.out.println("Autor actualizado");
        } else {
            System.out.println("No se encontró el autor con el ID: " + idAutor);
        }
    }

    private Autor obtenerAutorPorId(int idAutor) {
        return session.get(Autor.class, idAutor);
    }

    public List<Autor> obtenerAutoresPorAños(int añoInicio, int añoFin) {
        String hql = "FROM Autor a WHERE EXISTS " +
                "(SELECT 1 FROM Libro l WHERE l.autorByIdautor = a AND l.año BETWEEN :inicio AND :fin)";
        List<Autor> autores = session.createQuery(hql
                        , Autor.class)
                .setParameter("inicio", añoInicio)
                .setParameter("fin", añoFin)
                .getResultList();

        return autores;
    }


}
