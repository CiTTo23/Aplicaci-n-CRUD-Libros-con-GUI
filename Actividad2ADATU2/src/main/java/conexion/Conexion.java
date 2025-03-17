/*
    Clase que genera la conexion a la base de datos previamente creada en mysql
 */

package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConnection() {
        Connection conexion = null;
        String baseDatos = "libreria"; //ponemos la base de datos libreria
        String url = "jdbc:mysql://localhost:3306/" + baseDatos;
        String usuario = "root";
        String password = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);//hacemos la conexion a la BD pasando los parametros de url usuario y contraseña
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    //metodo main para probar en consola si se etablece la conexion a la BD
    public static void main(String[] args) {
        Connection conexion = Conexion.getConnection();
        if (conexion != null) {
            System.out.println("Se ha accedido a la base de datos!");
        } else {
            System.out.println("No se ha podido acceder a la base de datos!");
        }
    }
}
