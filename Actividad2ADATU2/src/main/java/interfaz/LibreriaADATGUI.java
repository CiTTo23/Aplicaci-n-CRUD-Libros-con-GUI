/*
    Clase desde la que ejecutamos nuestro programa java, en la que vamos a definir la interfaz gráfica de la misma
 */

package interfaz;

import datos.ILibroDAO;
import datos.LibroDAO;
import dominio.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibreriaADATGUI extends JFrame {
    private ILibroDAO libroDAO;

    public LibreriaADATGUI() {
        libroDAO = new LibroDAO();
        iniciarComponentes();
    }

    private void iniciarComponentes() {//función principal de la interfaz gráfica en la que definimos cómo será
        try {//tema nimbus más atractivo que el por defecto de java
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //definimos el título, tamaño y comportamiento al cerrar
        setTitle("Gestión de Libros");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //colos principal de al app en grisáceo
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel panelPrincipal = new JPanel(new BorderLayout());//panel principal que contendrá a los demás paneles necesarios
        panelPrincipal.setBackground(new Color(240, 240, 240));
        getContentPane().add(panelPrincipal);

        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 10, 10));//panel que vamos a usar para introducir en él los botones
        panelBotones.setBackground(new Color(200, 200, 200));
        panelPrincipal.add(panelBotones, BorderLayout.NORTH);

        JButton listarButton = new JButton("Listar Libros");//creación de los botones con las operaciones crud
        JButton buscarButton = new JButton("Buscar Libro");
        JButton agregarButton = new JButton("Agregar Libro");
        JButton modificarButton = new JButton("Modificar Libro");
        JButton eliminarButton = new JButton("Eliminar Libro");

        //añadimos al panel los botones que hemos creado
        panelBotones.add(listarButton);
        panelBotones.add(buscarButton);
        panelBotones.add(agregarButton);
        panelBotones.add(modificarButton);
        panelBotones.add(eliminarButton);

        JTextArea areaResultados = new JTextArea();//área que motrará los resultados que recuperemos de la BD
        areaResultados.setEditable(false);//lo hacemos ineditable
        areaResultados.setFont(new Font("Arial", Font.PLAIN, 14));//fuente diferente
        areaResultados.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);//lo colocamos en el centro del panel principal

        //definimos las acciones de los botones al pulsarse
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Libro> libros = libroDAO.listarLibros();//guardamos en una lista de libros el resultado que obtenemos del método listar de LibroDAO
                areaResultados.setText("");
                for (Libro libro : libros) {//imprimimos todos los libros
                    areaResultados.append(libro.toString() + "\n");
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = JOptionPane.showInputDialog(null, "Ingrese el ID del libro");//al pulsar el botón, mostramos al usuario un popup con el id del libro que quiere buscar
                if (idString != null && !idString.isEmpty()) {//si el campo de id no esá vacío
                    int id = Integer.parseInt(idString);
                    Libro libro = new Libro(id);//creamos un libro solo con el id obtenido
                    boolean encontrado = libroDAO.buscarLibroPorId(libro);//buscamos el libro con la funcion de buscar por id del LibroDAO
                    if (encontrado) {//si lo hemos encontrado, mostramos el resutlado
                        areaResultados.setText("Libro encontrado: " + libro.toString() + "\n");
                    } else {
                        areaResultados.setText("Libro no encontrado: " + id);
                    }
                }
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(4, 2));//en este caso, tenemos que definir un panel más grande para el popup que contenga los campos de texto necesarios para añadir un libro a la bd
                JTextField tituloField = new JTextField();
                JTextField autorField = new JTextField();
                JTextField anioField = new JTextField();

                panel.add(new JLabel("Título:"));//añadimos los textfields
                panel.add(tituloField);
                panel.add(new JLabel("Autor:"));
                panel.add(autorField);
                panel.add(new JLabel("Año de publicación:"));
                panel.add(anioField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Libro", JOptionPane.OK_CANCEL_OPTION);//mostramos el popup
                if (result == JOptionPane.OK_OPTION) {//si se da al botón de aceptar, añadimos el libor
                    String titulo = tituloField.getText();
                    String autor = autorField.getText();
                    int anio = Integer.parseInt(anioField.getText());

                    Libro libro = new Libro(titulo, autor, anio);//creamos el libro con lo que obtenemos de la BD
                    boolean agregado = libroDAO.agregarLibro(libro);
                    if (agregado) {//si se ha agregado correctamente, lo motramos en el área de resultados
                        areaResultados.setText("Libro agregado: " + libro.toString() + "\n");
                    } else {
                        areaResultados.setText("Error al agregar el libro.");
                    }
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//como en el anterior metodo, el popup será mas grande, este tambien recoge el id para poder saber que ocurrencia de la BD se quiere modificar
                JPanel panel = new JPanel(new GridLayout(4, 2));
                JTextField idField = new JTextField();
                JTextField tituloField = new JTextField();
                JTextField autorField = new JTextField();
                JTextField anioField = new JTextField();

                panel.add(new JLabel("ID:"));//añadimos los campos de texto
                panel.add(idField);
                panel.add(new JLabel("Título:"));
                panel.add(tituloField);
                panel.add(new JLabel("Autor:"));
                panel.add(autorField);
                panel.add(new JLabel("Año de publicación:"));
                panel.add(anioField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Modificar Libro", JOptionPane.OK_CANCEL_OPTION);//mostramos el popup
                if (result == JOptionPane.OK_OPTION) {
                    int id = Integer.parseInt(idField.getText());//creamos un objeto de tipo libro que contenga lo que ha introducido el usuario
                    String titulo = tituloField.getText();
                    String autor = autorField.getText();
                    int anio = Integer.parseInt(anioField.getText());

                    Libro libro = new Libro(id, titulo, autor, anio);
                    boolean modificado = libroDAO.modificarLibro(libro);//modificamos el libro
                    if (modificado) {//mostramos resultado
                        areaResultados.setText("Libro modificado: " + libro.toString() + "\n");
                    } else {
                        areaResultados.setText("Error al modificar el libro.");
                    }
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = JOptionPane.showInputDialog(null, "Ingrese el ID del libro a eliminar:");//mostramos el popup
                if (idString != null && !idString.isEmpty()) {//si hemos recogido un id
                    int id = Integer.parseInt(idString);
                    Libro libro = new Libro(id);//creamos el libro por su id
                    boolean eliminado = libroDAO.eliminarLibro(libro);//pasamos el libro creado a la funcion que elimina un libro
                    if (eliminado) {//mostramos resultado de la operacion
                        areaResultados.setText("Libro eliminado: " + libro.toString() + "\n");
                    } else {
                        areaResultados.setText("Error al eliminar el libro.");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibreriaADATGUI().setVisible(true);//hacemos visible el panel principal para runear la app
            }
        });
    }
}