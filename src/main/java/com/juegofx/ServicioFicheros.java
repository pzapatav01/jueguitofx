package com.juegofx;

import java.io.*;

/**
 * Clase de utilidad que proporciona servicios relacionados con la creación de escenarios
 * para el juego.
 * <p>
 * Esta clase permite generar archivos de texto que representan escenarios utilizando
 * patrones predefinidos de espacios libres, obstáculos y bordes.
 * </p>
 *
 * <p>Convenciones de caracteres usados en el escenario:</p>
 * <ul>
 *   <li><b>'E'</b>: Espacio libre</li>
 *   <li><b>'O'</b>: Obstáculo</li>
 *   <li><b>'B'</b>: Borde</li>
 * </ul>
 * 
 * <p>
 * El archivo generado se guarda en la carpeta {@code escenarios/} con el nombre proporcionado.
 * </p>
 * 
 * @author 
 */
public class ServicioFicheros {

    /**
     * Genera un archivo de escenario con un patrón determinado de obstáculos y espacios.
     * <p>
     * El patrón de obstáculos depende de si la fila es par o impar:
     * </p>
     * <ul>
     *   <li>Filas pares: 15 espacios, 4 obstáculos, 3 espacios, 1 obstáculo, resto espacios</li>
     *   <li>Filas impares: 3 espacios, 1 obstáculo, resto espacios</li>
     * </ul>
     * <p>
     * Además, se coloca un borde ('B') al final de cada fila, y una fila final completa de bordes.
     * </p>
     *
     * @param nombreArchivo Nombre del archivo a crear (por ejemplo: {@code escenario1.txt}).
     * @param filas         Número de filas del escenario.
     * @param columnas      Número de columnas del escenario.
     */
    public static void generarEscenario(String nombreArchivo, int filas, int columnas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("escenarios/" + nombreArchivo))) {

            for (int i = 0; i < filas; i++) {
                StringBuilder fila = new StringBuilder();

                // Definición de patrón según si la fila es par o impar
                if (i % 2 == 0) {
                    // Fila par: patrón complejo
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
                    // Fila impar: patrón más simple
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

                // Añadir borde al final de cada fila
                if (fila.length() > 0) {
                    fila.setCharAt(fila.length() - 1, 'B');
                }

                writer.println(fila.toString());
            }

            // Fila final completa de bordes
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
