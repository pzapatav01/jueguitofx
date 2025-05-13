package com.juegofx;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación JavaFX.
 * Esta clase actúa como punto de entrada
 * para iniciar la interfaz gráfica de usuario. Se encarga de inicializar y lanzar
 * el controlador principal del juego.
 * 
 * @author Carlos de Tena Muñoz
 * @author Paloma Zapata Vazquez
 * @version 1.0
 */
public class App extends Application {
    
    /**
     * Método de inicio de JavaFX.
     * Este método se ejecuta automáticamente cuando se lanza la aplicación. Crea una
     * instancia del {@code Controlador} principal y le pasa el {@code Stage} primario.
     * Luego, invoca el método {@code iniciar()} del controlador para arrancar la aplicación.
     *
     * @param primaryStage el escenario principal proporcionado por JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        // Instanciamos el controlador principal y le pasamos el Stage
        Controlador controlador = new Controlador(primaryStage);
        
        // Iniciamos la aplicación llamando al método iniciar del controlador
        controlador.iniciar();
    }

    /**
     * Método main estándar en aplicaciones Java.
     * <p>
     * Lanza la aplicación JavaFX mediante el método {@code launch()}, que a su vez
     * llama al método {@code start()}.
     * </p>
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
