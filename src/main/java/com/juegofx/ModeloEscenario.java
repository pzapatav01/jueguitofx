package com.juegofx;

import java.io.*;

public class ModeloEscenario {
    private char[][] matriz;
    private int filJugador, colJugador;
    
    public ModeloEscenario() {
    }
    
    public boolean cargar(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader("escenarios/" + nombreArchivo))) {
            String linea;
            int numFilas = 0;
            int numColumnas = 0;
            
            // Primero determinamos el tamaño de la matriz
            while ((linea = br.readLine()) != null) {
                numFilas++;
                if (linea.length() > numColumnas) {
                    numColumnas = linea.length();
                }
            }
            
            // Creamos la matriz con el tamaño adecuado
            matriz = new char[numFilas][numColumnas];
            
            // Volvemos a leer el archivo para llenar la matriz
            br.close();
            BufferedReader br2 = new BufferedReader(new FileReader("escenarios/" + nombreArchivo));
            
            int fila = 0;
            while ((linea = br2.readLine()) != null) {
                for (int col = 0; col < linea.length(); col++) {
                    matriz[fila][col] = linea.charAt(col);
                }
                fila++;
            }
            
            br2.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error al cargar escenario: " + e.getMessage());
            return false;
        }
    }
    
    public void colocarJugador() {
        // Buscar la primera posición libre (E)
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == 'E') {
                    matriz[i][j] = 'J';  // J marca la posición del jugador
                    filJugador = i;
                    colJugador = j;
                    return;
                }
            }
        }
    }
    
    public boolean moverJugador(char direccion) {
        int nuevaFil = filJugador;
        int nuevaCol = colJugador;
        
        switch (Character.toUpperCase(direccion)) {
            case 'W': nuevaFil--; break;
            case 'A': nuevaCol--; break;
            case 'S': nuevaFil++; break;
            case 'D': nuevaCol++; break;
        }
        
        // Verificar si la nueva posición es válida
        if (nuevaFil >= 0 && nuevaFil < matriz.length && 
            nuevaCol >= 0 && nuevaCol < matriz[0].length && 
            matriz[nuevaFil][nuevaCol] == 'E') {
            
            // Actualizar la matriz
            matriz[filJugador][colJugador] = 'E';  // Dejamos un espacio
            matriz[nuevaFil][nuevaCol] = 'J';      // Ponemos al jugador
            
            // Actualizar posición del jugador
            filJugador = nuevaFil;
            colJugador = nuevaCol;
            
            return true;
        }
        
        return false;
    }
    
    public char[][] getMatriz() {
        return matriz;
    }
}
