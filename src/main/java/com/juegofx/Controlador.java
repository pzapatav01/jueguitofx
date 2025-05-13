package com.juegofx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

/**
 * Clase Controlador que gestiona la lógica de la aplicación y actúa como intermediario
 * entre el modelo y la vista.
 * Se encarga de controlar el flujo del juego: desde el splash inicial, el login o creación
 * de jugador, selección de escenario, hasta la ejecución del juego y su finalización.
 * 
 * Esta clase sigue el patrón MVC (Modelo-Vista-Controlador).
 * 
 * @author Carlos de Tena Muñoz
 * @author Paloma Zapata Vazquez
 * @version 1.0
 * @date 2025-05-13
 */
public class Controlador {
    
    private Stage stage; 
    private Vista vista;
    private ModeloJugador jugador;
    private ModeloEscenario escenario;
    private List<String> escenarios;

    /**
     * Constructor del Controlador.
     * 
     * @param stage El escenario principal de la aplicación proporcionado por JavaFX.
     */
    public Controlador(Stage stage){
        this.stage = stage;
        this.vista = new Vista(this, stage);
        this.escenarios = cargarEscenarios();
    }

    /**
     * Inicia la aplicación mostrando la pantalla de presentación (splash).
     */
    public void iniciar(){
        vista.mostrarPantallaSplash();
    }

    /**
     * Se llama después de que termina la pantalla splash para mostrar
     * la pantalla de inicio de sesión o registro.
     */
    public void despuesDeSplash(){
        vista.mostrarPantallaLogin();
    }

    /**
     * Verifica si el jugador ya existe mediante su nombre. Si existe, se intenta cargar;
     * de lo contrario, se solicita el email para crear uno nuevo.
     * 
     * @param nombre Nombre del jugador.
     */
    public void verificarJugador(String nombre){
        File ficheroJugador = new File("jugadores/" + nombre + ".dat");

        if (ficheroJugador.exists()) {
            jugador = new ModeloJugador();
            boolean cargado = jugador.cargar(nombre);
            
            if (cargado) {
                vista.mostrarMensaje("Jugador cargado correctamente: " + jugador.getNombre());
                seleccionarEscenario();
            } else {
                vista.mostrarError("Error al cargar el jugador");
            }
        
        } else {
            vista.pedirEmailParaNuevoJugador(nombre);
        }
    }

    /**
     * Crea un nuevo jugador con el nombre y email proporcionados, y lo guarda en disco.
     * 
     * @param nombre Nombre del nuevo jugador.
     * @param email Email del nuevo jugador.
     */
    public void crearNuevoJugador(String nombre, String email){
        jugador = new ModeloJugador(nombre, email);
        boolean guardado = jugador.guardar();

        if (guardado) {
            vista.mostrarMensaje("Nuevo jugador creado correctamente.");
            seleccionarEscenario();
        } else {
            vista.mostrarError("Error al crear el jugador");
        }
    }

    /**
     * Muestra la pantalla para seleccionar uno de los escenarios disponibles.
     */
    public void seleccionarEscenario(){
        vista.mostrarSeleccionEscenario(escenarios);
    }

    /**
     * Carga un escenario por nombre y coloca al jugador en el escenario.
     * 
     * @param nombreEscenario Nombre del archivo del escenario a cargar.
     */
    public void cargarEscenario(String nombreEscenario){
        escenario = new ModeloEscenario();
        boolean cargado = escenario.cargar(nombreEscenario);

        if (cargado) {
            escenario.colocarJugador();
            iniciarJuego();
        } else {
            vista.mostrarError("Error al cargar el escenario");
        }
    }

    /**
     * Inicia el juego mostrando la pantalla de juego con el escenario actual.
     */
    public void iniciarJuego(){
        vista.mostrarPantallaJuego(escenario);
    }

    /**
     * Mueve al jugador en la dirección especificada si es posible.
     * 
     * @param direccion Dirección del movimiento (por ejemplo, 'w', 'a', 's', 'd').
     */
    public void moverJugador(char direccion){
        boolean movimientoValido = escenario.moverJugador(direccion);

        if (movimientoValido) {
            vista.actualizarEscenario(escenario);
        } else {
            vista.mostrarMensaje("Menudo porrazo, mira por donde vas 🤕");
        }
    }

    /**
     * Finaliza el juego, guarda el estado del jugador y muestra la pantalla final.
     */
    public void finalizarJuego(){
        jugador.guardar();
        vista.mostrarPantallaFin();
    }

    /**
     * Carga una lista de nombres de archivos de escenarios disponibles.
     * 
     * @return Lista de nombres de archivos de escenarios.
     */
    private List<String> cargarEscenarios(){
        List<String> listaEscenarios = new ArrayList<>();
        listaEscenarios.add("escenario1.txt");
        listaEscenarios.add("escenario2.txt");
        listaEscenarios.add("escenario3.txt");
        return listaEscenarios;
    }
}
