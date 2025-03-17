/*
    Interfaz que implementará la clase de libroDAO, define los métodos que ha de tener libroDAO
 */
package datos;

import dominio.Libro;
import java.util.List;

public interface ILibroDAO {
    List<Libro> listarLibros();
    boolean buscarLibroPorId(Libro libro);//seguimos un estándar pasandole a todos los métodos objetos de tipo Libro
    boolean agregarLibro(Libro libro);
    boolean modificarLibro(Libro libro);
    boolean eliminarLibro(Libro libro);
}