package com.juegofx;

import java.util.List;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Vista {
    private Controlador controlador;
    private Stage stage;
    private Scene sceneSplash, sceneLogin, sceneSeleccionEscenario, sceneJuego, sceneFin;
    private GridPane gridJuego;

    public Vista(Controlador controlador, Stage stage){
        this.controlador = controlador;
        this.stage = stage;
        this.stage.setTitle("Juego de Laberinto");

        crearPantallaSplash();
        crearPantallaLogin();
    }

    private void crearPantallaSplash(){
        BorderPane root = new BorderPane();
        Label labelTitulo = new Label("JUEGO DE LABERINTO");
        labelTitulo.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        Label labelSubtitulo = new Label("Desarrollado con JavaFX");
        labelSubtitulo.setStyle("-fx-font-size: 18px;");

        VBox contenedor = new VBox(20);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.getChildren().addAll(labelTitulo, labelSubtitulo);

        root.setCenter(contenedor);
        root.setStyle("-fx-background-color: lightblue;");

        sceneSplash = new Scene(root, 600, 400);
    }

    private void crearPantallaLogin() {
        BorderPane root = new BorderPane();
        
        Label labelTitulo = new Label("Inicio de Sesión");
        labelTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label labelNombre = new Label("Introduce tu nombre:");
        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre de usuario");
        
        Button botonEntrar = new Button("Entrar");
        botonEntrar.setOnAction(e -> controlador.verificarJugador(campoNombre.getText()));
        
        VBox contenedor = new VBox(15);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.getChildren().addAll(labelTitulo, labelNombre, campoNombre, botonEntrar);
        
        root.setCenter(contenedor);
        root.setStyle("-fx-background-color: #f0f0f0;");
        
        sceneLogin = new Scene(root, 600, 400);
    }
    
    public void mostrarPantallaSplash() {
        stage.setScene(sceneSplash);
        stage.show();
        
        // Transición automática tras 3 segundos
        PauseTransition espera = new PauseTransition(Duration.seconds(3));
        espera.setOnFinished(e -> controlador.despuesDeSplash());
        espera.play();
    }
    
    public void mostrarPantallaLogin() {
        stage.setScene(sceneLogin);
    }
    
    public void pedirEmailParaNuevoJugador(String nombre) {
        BorderPane root = new BorderPane();
        
        Label labelInfo = new Label("Usuario nuevo. Por favor, introduce tu email:");
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Email");
        
        Button botonCrear = new Button("Crear usuario");
        botonCrear.setOnAction(e -> controlador.crearNuevoJugador(nombre, campoEmail.getText()));
        
        VBox contenedor = new VBox(15);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.getChildren().addAll(labelInfo, campoEmail, botonCrear);
        
        root.setCenter(contenedor);
        
        stage.setScene(new Scene(root, 600, 400));
    }
    
    public void mostrarSeleccionEscenario(List<String> escenarios) {
        BorderPane root = new BorderPane();
        
        Label labelTitulo = new Label("Selecciona un escenario");
        labelTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        VBox contenedor = new VBox(15);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.getChildren().add(labelTitulo);
        
        for (String escenario : escenarios) {
            Button botonEscenario = new Button(escenario);
            botonEscenario.setPrefWidth(200);
            botonEscenario.setOnAction(e -> controlador.cargarEscenario(escenario));
            contenedor.getChildren().add(botonEscenario);
        }
        
        root.setCenter(contenedor);
        
        sceneSeleccionEscenario = new Scene(root, 600, 400);
        stage.setScene(sceneSeleccionEscenario);
    }
    
    public void mostrarPantallaJuego(ModeloEscenario escenario) {
        BorderPane root = new BorderPane();
        
        // Crear grid para representar el escenario
        gridJuego = new GridPane();
        gridJuego.setAlignment(Pos.CENTER);
        gridJuego.setHgap(2);
        gridJuego.setVgap(2);
        
        // Dibujar el escenario
        actualizarEscenario(escenario);
        
        // Controles de movimiento
        HBox controles = new HBox(10);
        controles.setAlignment(Pos.CENTER);
        
        Button btnW = new Button("W");
        Button btnA = new Button("A");
        Button btnS = new Button("S");
        Button btnD = new Button("D");
        
        btnW.setOnAction(e -> controlador.moverJugador('W'));
        btnA.setOnAction(e -> controlador.moverJugador('A'));
        btnS.setOnAction(e -> controlador.moverJugador('S'));
        btnD.setOnAction(e -> controlador.moverJugador('D'));
        
        controles.getChildren().addAll(btnW, btnA, btnS, btnD);
        
        Button btnSalir = new Button("Finalizar juego");
        btnSalir.setOnAction(e -> controlador.finalizarJuego());
        
        VBox panelInferior = new VBox(15);
        panelInferior.setAlignment(Pos.CENTER);
        panelInferior.getChildren().addAll(controles, btnSalir);
        
        root.setCenter(gridJuego);
        root.setBottom(panelInferior);
        
        sceneJuego = new Scene(root, 600, 500);
        
        // Configurar eventos de teclado para movimiento
        sceneJuego.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W: controlador.moverJugador('W'); break;
                case A: controlador.moverJugador('A'); break;
                case S: controlador.moverJugador('S'); break;
                case D: controlador.moverJugador('D'); break;
            }
        });
        
        stage.setScene(sceneJuego);
    }
    
    public void actualizarEscenario(ModeloEscenario escenario) {
        gridJuego.getChildren().clear();
        
        char[][] matriz = escenario.getMatriz();
        
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                StackPane celda = new StackPane();
                celda.setPrefSize(30, 30);
                
                switch (matriz[i][j]) {
                    case 'O': // Obstáculo
                        celda.setStyle("-fx-background-color: black;");
                        break;
                    case 'E': // Espacio
                        celda.setStyle("-fx-background-color: white;");
                        break;
                    case 'J': // Jugador
                        celda.setStyle("-fx-background-color: blue;");
                        break;
                    case 'B': // Borde
                        celda.setStyle("-fx-background-color: gray;");
                        break;
                    default:
                        celda.setStyle("-fx-background-color: white;");
                }
                
                gridJuego.add(celda, j, i);
            }
        }
    }
    
    public void mostrarPantallaFin() {
        BorderPane root = new BorderPane();
        
        Label labelFin = new Label("¡Fin del juego!");
        labelFin.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Button botonReiniciar = new Button("Volver a jugar");
        botonReiniciar.setOnAction(e -> mostrarPantallaLogin());
        
        Button botonSalir = new Button("Salir");
        botonSalir.setOnAction(e -> stage.close());
        
        HBox botones = new HBox(20);
        botones.setAlignment(Pos.CENTER);
        botones.getChildren().addAll(botonReiniciar, botonSalir);
        
        VBox contenedor = new VBox(30);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.getChildren().addAll(labelFin, botones);
        
        root.setCenter(contenedor);
        
        sceneFin = new Scene(root, 600, 400);
        stage.setScene(sceneFin);
    }
    
    public void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

