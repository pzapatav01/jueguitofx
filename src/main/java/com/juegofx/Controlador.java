package com.juegofx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

public class Controlador {
    private Stage stage; 
    private Vista vista;
    private ModeloJugador jugador;
    private ModeloEscenario escenario;
    private List<String>escenarios;
    
    public Controlador(Stage stage){
        this.stage = stage;
        this.vista = new Vista(this, stage);
        this.escenarios = cargarEscenarios();
    }

    public void iniciar(){
        vista.mostrarPantallaSplash();
    }

    public void despuesDeSplash(){
        vista.mostrarPantallaLogin();
    }

    public void verificarJugador(String nombre){
        File ficheroJugador = new File("jugadores/" + nombre + ".dat" );

        if(ficheroJugador.exists()){
            jugador = new ModeloJugador();
            boolean cargado = jugador.cargar(nombre);
            
            if(cargado){
                vista.mostrarMensaje("Jugador cargado correctamente: " + jugador.getNombre());
                seleccionarEscenario();
            }else{
                vista.mostrarError("Error al cargar el jugador");
            }
        
        }else{
            vista.pedirEmailParaNuevoJugador(nombre);
        }
    }

    public void crearNuevoJugador(String nombre, String email){
        jugador = new ModeloJugador(nombre, email);
        boolean guardado = jugador.guardar();

        if(guardado){
            vista.mostrarMensaje("Nuevo jugador creado correctamente.");
            seleccionarEscenario();
        }else{
            vista.mostrarError("Error al crear el jugador)");
        }
    }

    public void seleccionarEscenario(){
        vista.mostrarSeleccionEscenario(escenarios);
    }

    public void cargarEscenario(String nombreEscenario){
        escenario = new ModeloEscenario();
        boolean cargado = escenario.cargar(nombreEscenario);

        if(cargado){
            escenario.colocarJugador();
            iniciarJuego();
        }else{
            vista.mostrarError("Error al cargar el escenario");
        }
    }

    public void iniciarJuego(){
        vista.mostrarPantallaJuego(escenario);
    }

    public void moverJugador(char direccion){
        boolean movimientoValido = escenario.moverJugador(direccion);
        if(movimientoValido){
            vista.actualizarEscenario(escenario);
        }else{
            vista.mostrarMensaje("Menudo porrazo, mira por donde vas 🤕");
        }
    }

    public void finalizarJuego(){
        jugador.guardar();
        vista.mostrarPantallaFin();
    }

    private List<String> cargarEscenarios(){
        List<String> listaEscenarios = new ArrayList<>();
        listaEscenarios.add("escenario1.txt");
        listaEscenarios.add("escenario2.txt");
        listaEscenarios.add("escenario3.txt");
        return listaEscenarios;
    }
}
