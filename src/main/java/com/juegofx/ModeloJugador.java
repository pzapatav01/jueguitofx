package com.juegofx;

import java.io.*;

public class ModeloJugador implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String email;
    
    // Constructor para nuevo jugador
    public ModeloJugador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    // Constructor vacío para cargar jugador existente
    public ModeloJugador() {
    }
    
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
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEmail() {
        return email;
    }
}