package com.juegofx;

import java.io.*;

public class ServicioFicheros {
    // Este servicio se utilizaría para crear los escenarios según las especificaciones
    // 15 espacios, 4 obstáculos, 3 espacios, 1 obstáculo, etc.
    
    public static void generarEscenario(String nombreArchivo, int filas, int columnas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("escenarios/" + nombreArchivo))) {
            
            for (int i = 0; i < filas; i++) {
                StringBuilder fila = new StringBuilder();
                
                // Ejemplo de patrón para una fila
                if (i % 2 == 0) {
                    // 15 espacios, 4 obstáculos, 3 espacios, 1 obstáculo, resto espacios
                    for (int j = 0; j < columnas; j++) {
                        if (j < 15) {
                            fila.append('E');
                        } else if (j < 19) {
                            fila.append('O');
                        } else if (j < 22) {
                            fila.append('E');
                        } else if (j < 23) {
                            fila.append('O');
                        } else {
                            fila.append('E');
                        }
                    }
                } else {
                    // 3 espacios, 1 obstáculo, resto espacios
                    for (int j = 0; j < columnas; j++) {
                        if (j < 3) {
                            fila.append('E');
                        } else if (j < 4) {
                            fila.append('O');
                        } else {
                            fila.append('E');
                        }
                    }
                }
                
                // Agregar bordes (última columna)
                if (fila.length() > 0) {
                    fila.setCharAt(fila.length() - 1, 'B');
                }
                
                writer.println(fila.toString());
            }
            
            // Agregar borde en la última fila
            StringBuilder bordeFinal = new StringBuilder();
            for (int j = 0; j < columnas; j++) {
                bordeFinal.append('B');
            }
            writer.println(bordeFinal.toString());
            
        } catch (IOException e) {
            System.err.println("Error al generar escenario: " + e.getMessage());
        }
    }
}
