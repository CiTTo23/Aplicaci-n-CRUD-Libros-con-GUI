/*
    Clase que aplica el patrón de DAO a los libros, definiendo sus operaciones CRUD
 */

package datos;

import conexion.Conexion;
import dominio.Libro;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO implements ILibroDAO {

    @Override
    public List<Libro> listarLibros() {//listar libros
        List<Libro> libros = new ArrayList<>();//creamos lista en la que guardaremos los libros que obtengamos de la BD
        Connection con = Conexion.getConnection();

        try {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );//hacemos el statement utilizando resultset
            ResultSet rs = stmt.executeQuery("SELECT * FROM libros");//ejecutamos la sentencia sql de select, guardando el resultado en el resultset

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion")
                );//creamos el objeto de tipo libros que vamos a añadir a la lista, pasandole los campos que recuperamos de la BD
                libros.add(libro);
            }

            rs.close();
            stmt.close();//cerramos los recursos
        } catch (Exception e) {
            System.out.println("Error al listar los libros: " + e.getMessage());
        } finally {
            try {//cerramos la conexion
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return libros;//retornamos la lista de libros obtenidos
    }

    @Override
    public boolean buscarLibroPorId(Libro libro) {
        //metodo que busca un libro por su id
        Connection con = Conexion.getConnection();

        try {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " + libro.getId());//ejecutamos la sentencia creada pasandole el id del libro que obtenemos por parámetro

            if (rs.next()) {//recuperamos el resultado y actualizamos el objeto libro que recibimos
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                return true;
            }

            rs.close();//cerramos recursos
            stmt.close();
        } catch (Exception e) {//genstion de errores
            System.out.println("Error al buscar el libro por ID: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarLibro(Libro libro) {
        //metodo que agrega un libro a la BD
        Connection con = Conexion.getConnection();

        try {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stmt.executeQuery("SELECT * FROM libros");//ejecutamos la sentencia con resultset de select

            rs.moveToInsertRow();//movemos la linea de insercion
            rs.updateString("titulo", libro.getTitulo());//cogemos los campos del objeto libro que recibimos y los pasamos al resultset para añadirlo a la BD
            rs.updateString("autor", libro.getAutor());
            rs.updateInt("anio_publicacion", libro.getAnioPublicacion());
            rs.insertRow();//insertamos a la bd

            rs.close();//cerramos los recursos
            stmt.close();
            return true;
        } catch (Exception e) {//control de errores
            System.out.println("Error al agregar el libro: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean modificarLibro(Libro libro) {
        //metodo que modifica un libro en la bd
        Connection con = Conexion.getConnection();

        try {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " + libro.getId());//recuperamos el libro a modificar

            if (rs.next()) {
                rs.updateString("titulo", libro.getTitulo());//lo actualizamos con los parámetros recibidos del libro
                rs.updateString("autor", libro.getAutor());
                rs.updateInt("anio_publicacion", libro.getAnioPublicacion());
                rs.updateRow();
                return true;
            }

            rs.close();//cerramos los recursos
            stmt.close();
        } catch (Exception e) {//gestion de errores
            System.out.println("Error al modificar el libro: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarLibro(Libro libro) {
        //Metodo que elimina iun libro de la BD
        Connection con = Conexion.getConnection();

        try {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " + libro.getId());//ejecutamos la sentencia sql , pasandole el id para ver que libro se ha de eliminar

            if (rs.next()) {
                rs.deleteRow();//borramos la ocurrencia obtenida en el resultset con deletero
                return true;
            }

            rs.close();//cerramos recursos
            stmt.close();
        } catch (Exception e) {//gestion de errores
            System.out.println("Error al eliminar el libro: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }
}
