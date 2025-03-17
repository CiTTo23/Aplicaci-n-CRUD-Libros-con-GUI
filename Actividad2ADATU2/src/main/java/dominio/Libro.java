/*
    Clase que define como es un libro en nuestra aplicacion de java, con los mismos campos que recuperamos de la BD
 */

package dominio;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anioPublicacion;

    public Libro() {}//constructor vacío para poder llamar a los métodos de la clase

    public Libro(int id) {//nos será util para crear objetos libro solo definiendo su id, para opciones en la que busquemos por id
        this.id = id;
    }

    public Libro(String titulo, String autor, int anioPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
    }

    public Libro(int id, String titulo, String autor, int anioPublicacion) {
        this(titulo, autor, anioPublicacion);
        this.id = id;
    }

    //getters y getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    //redefinimos el método tostring
    @Override
    public String toString() {
        return "------->  id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", anioPublicacion=" + anioPublicacion;
    }
}
