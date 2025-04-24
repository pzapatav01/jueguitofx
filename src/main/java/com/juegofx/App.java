package com.juegofx;

import javafx.application.Application;
import javafx.stage.Stage;



public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Instanciamos el controlador principal y le pasamos el Stage
        Controlador controlador = new Controlador(primaryStage);
        // Iniciamos la aplicación
        controlador.iniciar();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}