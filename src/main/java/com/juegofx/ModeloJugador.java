package com.juegofx;

import java.io.*;

/**
 * Clase que representa el modelo de un jugador.
 * <p>
 * Esta clase implementa la interfaz {@link Serializable} para permitir la
 * persistencia del estado del jugador en archivos. Puede crear nuevos jugadores,
 * guardarlos y cargarlos desde archivos en formato binario.
 * </p>
 * 
 * El archivo se almacena en la carpeta "jugadores" con nombre: {@code nombreJugador.dat}.
 * 
 * @author 
 */
public class ModeloJugador implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String email;

    /**
     * Constructor para crear un nuevo jugador.
     * 
     * @param nombre Nombre del jugador.
     * @param email  Dirección de correo del jugador.
     */
    public ModeloJugador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    /**
     * Constructor sin parámetros para usar al cargar un jugador existente.
     */
    public ModeloJugador() {
    }

    /**
     * Guarda el jugador actual en un archivo utilizando serialización.
     * <p>
     * Crea el directorio {@code jugadores} si no existe y guarda el objeto
     * serializado con el nombre del jugador como nombre de archivo.
     * </p>
     * 
     * @return {@code true} si el guardado fue exitoso; {@code false} si hubo un error.
     */
    public boolean guardar() {
        File directorio = new File("jugadores");
        if (!directorio.exists()) {
            directorio.mkdir();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("jugadores/" + nombre + ".dat"))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar jugador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carga los datos de un jugador desde un archivo utilizando deserialización.
     * <p>
     * El archivo debe encontrarse en la carpeta {@code jugadores} y tener el
     * nombre del jugador con extensión {@code .dat}.
     * </p>
     * 
     * @param nombreJugador Nombre del archivo (sin extensión) del jugador a cargar.
     * @return {@code true} si la carga fue exitosa; {@code false} en caso de error.
     */
    public boolean cargar(String nombreJugador) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("jugadores/" + nombreJugador + ".dat"))) {
            ModeloJugador jugadorCargado = (ModeloJugador) ois.readObject();
            this.nombre = jugadorCargado.nombre;
            this.email = jugadorCargado.email;
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar jugador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return Nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el email del jugador.
     * 
     * @return Dirección de correo
    */
    public String getEmail() {
        return email;
    }
}
