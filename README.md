# Aplicacion-CRUD-Libros-con-GUI
Proyecto que desarrolla un menú CRUD implementando una simple interfaz gráfica para gestionar libros de una supuesta librería.  Utiliza preparedStatement para tirar consultas en una Base de Datos local en mysqk.
David Martínez lópez:
Este es un proyecto desarrollado en java que gestiona una base de datos de una libreria que contiene libros.
Para ello se ha utilizado una estructura en maven y en el pom se han añadido las dependecias necesarias.

También necesitaremos la previa creación de la base de datos en el gestor de myaql, en el cual se ejecutan las siguientes sentencias:
    CREATE DATABASE libreria;
    use libreria;
    CREATE TABLE libros (
        id INT PRIMARY KEY AUTO_INCREMENT,
        titulo VARCHAR(100),
        autor VARCHAR(100),
        anio_publicacion INT
    );

La estructura del proyecto se basa en las siguientes clases:
    Conexion:  Es la clase que genera la conexion a la base de datos, basicamente en ella establecemos la url de la BD a acceder, usuario y password
    ILibroDAO: Es la interfaz que define el comportamiento de LibroDAO, que implementará los métodos que se ven en esta interfaz.
    LibroDAO:Clase que contiene los métodos necesarios para gestionar la informacion que recuperamos de la base de datos de los libros.
        En esta clase se utiliza resultset para hacer todas las operaciones en la base de datos. Se utilizan sus métodos para conseguir este objetivo,
        como por ejemplo updateString(). Perparamos la sentencia y luego almacenamos el resultado de ejecutar la misma en el resultset,
        a través del cual podemos hacer las consultas con métodos.
    Libro:Clase que define cómo es un producto, en este caso tendrá los mismos campos que contiene la BD. (id, titulo, autor y año de publicacion)
    LibreriaADATGUI:Es la interfaz gráfica de nuestro proyecto que se basa en un panel principal que contiene 5 botones y un área de resultados para mostrar los libros


Para ejecutar el proyecto hemos de ejecutar la clase de LibreriaADATGUI, gracias a la cual veremos la interfaz gráfica del proyecto. En ella veremos 5 botones para gestionar los
libros de forma intuitiva y un área de resultados que no es editable en la que mostraremos los resultados obtenidos de la BD.
