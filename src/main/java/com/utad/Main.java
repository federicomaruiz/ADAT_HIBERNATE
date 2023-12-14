package com.utad;


import models.Autor;
import models.AutorDAO;
import models.Libro;
import models.LibroDAO;
import org.hibernate.Session;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Session session = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            session = HibernateUtil.getSession();

            AutorDAO autorDAO = new AutorDAO(session);
            LibroDAO libroDAO = new LibroDAO(session);

            char opcion;
            do {
                mostrarMenu();
                opcion = scanner.next().charAt(0);

                switch (opcion) {
                    case '0':
                        crearLibro(libroDAO);
                        break;
                    case '1':
                        eliminarLibro(libroDAO);
                        break;
                    case '2':
                        actualizarLibro(libroDAO); // Nueva opción para actualizar libro
                        break;
                    case '3':
                        mostrarTodosLosLibros(libroDAO);
                        break;
                    case '4':
                        crearAutor(autorDAO);
                        break;
                    case '5':
                        System.out.println("Tenga en cuenta que si elimina un Autor, también eliminará los libros referenciados a él");
                        eliminarAutor(autorDAO);
                        break;
                    case '6':
                        actualizarAutor(autorDAO);
                        break;
                    case '7':
                        mostrarTodosLosAutores(autorDAO);
                        break;
                    case '8':
                        verLibrosPorAutor(libroDAO);
                        break;
                    case '9':
                        encontrarAutoresPorAños(autorDAO);
                        break;
                    case 'x':
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige una opción del menú.");
                }
            } while (opcion != 'x');

        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession(session);
            scanner.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println("");
        System.out.println("### Menú ###");
        System.out.println("0. Crear Libro");
        System.out.println("1. Eliminar Libro");
        System.out.println("2. Actualizar Libro");
        System.out.println("3. Mostrar Todos los Libros");
        System.out.println("4. Crear Autor");
        System.out.println("5. Eliminar Autor");
        System.out.println("6. Actualizar Autor");
        System.out.println("7. Mostrar Todos los Autores");
        System.out.println("8. Ver Libros por Autor");
        System.out.println("9. Encontrar Autores por Años");
        System.out.println("x. Salir");
        System.out.print("Elige una opción: ");
    }


    private static void crearLibro(LibroDAO libroDAO) {
        scanner.nextLine();
        System.out.print("Ingrese el nombre del libro: ");
        String nombreLibro = scanner.nextLine();
        System.out.print("Ingrese el año del libro: ");
        String añoLibro = scanner.nextLine();
        System.out.print("Ingrese el nombre del autor: ");
        String nombreAutor = scanner.nextLine();
        libroDAO.crearLibro(nombreLibro,añoLibro,nombreAutor);
    }

    private static void eliminarLibro(LibroDAO libroDAO) {
        try {
            System.out.print("Ingrese el ID del libro a eliminar: ");
            int idLibro = scanner.nextInt();
            libroDAO.eliminarLibro(idLibro);
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpiar el búfer del scanner
        }
    }

    private static void mostrarTodosLosLibros(LibroDAO libroDAO) {
        List<Libro> libros = libroDAO.obtenerTodosLosLibros();
        System.out.println("### Todos los Libros ###");
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.getId() +
                    ", Nombre: " + libro.getNombre() +
                    ", Año: " + libro.getAño() +
                    ", Autor: " + libro.getAutorByIdautor().getNombre());
        }
        System.out.println("------");
    }

    private static void crearAutor(AutorDAO autorDAO) {
        scanner.nextLine();
        System.out.print("Ingrese el nombre del autor: ");
        String nombreAutor = scanner.nextLine();
        autorDAO.crearAutor(nombreAutor);
    }

    private static void eliminarAutor(AutorDAO autorDAO) {
        try {
            System.out.print("Ingrese el ID del autor a eliminar: ");
            int idAutor = scanner.nextInt();
            autorDAO.eliminarAutor(idAutor);
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido para el ID.");
            scanner.nextLine();
        }
    }

    private static void mostrarTodosLosAutores(AutorDAO autorDAO) {
        List<Autor> autores = autorDAO.obtenerTodosLosAutores();
        System.out.println("### Todos los Autores ###");
        for (Autor autor : autores) {
            System.out.println("ID: " + autor.getId() +
                    ", Nombre: " + autor.getNombre());
        }
    }

    private static void verLibrosPorAutor(LibroDAO libroDAO) {
        try {
            System.out.print("Ingrese el ID del autor: ");
            int idAutor = scanner.nextInt();
            List<Libro> libros = libroDAO.obtenerLibrosPorAutor(idAutor);
            System.out.println("Libros del Autor con ID " + idAutor + ":");
            for (Libro libro : libros) {
                System.out.println(libro.getId() + " - " + libro.getNombre() + " - " + libro.getAño());
            }
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido para el ID del autor.");
            scanner.nextLine(); // Limpiar el búfer del scanner
        }
    }
    private static void encontrarAutoresPorAños(AutorDAO autorDAO) {
        try {
            System.out.print("Ingrese el año de inicio: ");
            int añoInicio = scanner.nextInt();
            System.out.print("Ingrese el año de fin: ");
            int añoFin = scanner.nextInt();
            List<Autor> autores = autorDAO.obtenerAutoresPorAños(añoInicio, añoFin);
            System.out.println("Autores que han publicado libros entre " + añoInicio + " y " + añoFin + ":");
            for (Autor autor : autores) {
                System.out.println(autor.getId() + " - " + autor.getNombre());
            }
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese números válidos para los años.");
            scanner.nextLine(); // Limpiar el búfer del scanner
        }
    }
    private static void actualizarLibro(LibroDAO libroDAO) {
        try {
            System.out.print("Ingrese el ID del libro a actualizar: ");
            int idLibro = scanner.nextInt();
            scanner.nextLine(); // Limpiar el búfer del scanner
            System.out.print("Ingrese el nuevo nombre del libro: ");
            String nuevoNombre = scanner.nextLine();
            System.out.print("Ingrese el nombre del autor: ");
            String nombreAutor = scanner.nextLine();
            System.out.print("Ingrese el nuevo año del libro: ");
            int nuevoAño = scanner.nextInt();
            libroDAO.actualizarLibro(idLibro, nuevoNombre, nuevoAño, nombreAutor);
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese valores válidos.");
            scanner.nextLine(); // Limpiar el búfer del scanner
        }
    }

    private static void actualizarAutor(AutorDAO autorDAO) {
        try {
            System.out.print("Ingrese el ID del autor a actualizar: ");
            int idAutor = scanner.nextInt();
            scanner.nextLine(); // Limpiar el búfer del scanner
            System.out.print("Ingrese el nuevo nombre del autor: ");
            String nuevoNombre = scanner.nextLine();
            autorDAO.actualizarAutor(idAutor, nuevoNombre);
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese valores válidos.");
            scanner.nextLine(); // Limpiar el búfer del scanner
        }
    }
}

