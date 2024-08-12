package com.example;


import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private char[][] Tablero;
    private final int tam = 3;

    public Tablero() {
        Tablero = new char[tam][tam];
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                Tablero[i][j] = ' ';
            }
        }
    }

    // Method to print the board
    public void impresionTab() {
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                System.out.print(Tablero[i][j]);
                if (j < tam - 1) System.out.print("|");
            }
            System.out.println();
            if (i < tam - 1) System.out.println("-----");
        }
    }

    // Method to make a move
    public boolean hacerMovimiento(int row, int col, char jugador) {
        if (Tablero[row][col] == ' ') {
            Tablero[row][col] = jugador;
            return true;
        }
        return false;
    }

    // Method to move a piece from one position to another
    public boolean moverPieza(int startRow, int startCol, int endRow, int endCol) {
        if (Tablero[startRow][startCol] != ' ' && Tablero[endRow][endCol] == ' ') {
            if (Math.abs(startRow - endRow) + Math.abs(startCol - endCol) == 1) {
                Tablero[endRow][endCol] = Tablero[startRow][startCol];
                Tablero[startRow][startCol] = ' ';
                return true;
            }
        }
        return false;
    }

    // Method to check if a player has won
    public boolean isGanada(char jugador) {
        for (int i = 0; i < tam; i++) {
            if (Tablero[i][0] == jugador && Tablero[i][1] == jugador && Tablero[i][2] == jugador) return true;
            if (Tablero[0][i] == jugador && Tablero[1][i] == jugador && Tablero[2][i] == jugador) return true;
        }
        if (Tablero[0][0] == jugador && Tablero[1][1] == jugador && Tablero[2][2] == jugador) return true;
        if (Tablero[0][2] == jugador && Tablero[1][1] == jugador && Tablero[2][0] == jugador) return true;
        return false;
    }

    // Method to check if the board is full
    public boolean tableroLleno() {
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                if (Tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Method to check if the game is over (win or draw)
    public boolean isGameOver() {
        return isGanada('X') || isGanada('O') || tableroLleno();
    }

    // Getter for the board state
    public char[][] getTablero() {
        return Tablero;
    }

    // Setter for the board state
    public void setTablero(char[][] tablero) {
        this.Tablero = tablero;
    }

    // Method to generate successors for the minimax algorithm
    public List<char[][]> generarSucesores(char jugador) {
        List<char[][]> successors = new ArrayList<>();
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                if (Tablero[i][j] == ' ') {
                    char[][] newTablero = copiarTablero();
                    newTablero[i][j] = jugador;
                    successors.add(newTablero);
                }
            }
        }
        // Add move piece successors
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                if (Tablero[i][j] == jugador) {
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            if (Math.abs(di) + Math.abs(dj) == 1) {
                                int ni = i + di, nj = j + dj;
                                if (ni >= 0 && ni < tam && nj >= 0 && nj < tam && Tablero[ni][nj] == ' ') {
                                    char[][] newTablero = copiarTablero();
                                    newTablero[i][j] = ' ';
                                    newTablero[ni][nj] = jugador;
                                    successors.add(newTablero);
                                }
                            }
                        }
                    }
                }
            }
        }
        return successors;
    }

    // Method to copy the board state
    private char[][] copiarTablero() {
        char[][] newTablero = new char[tam][tam];
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                newTablero[i][j] = Tablero[i][j];
            }
        }
        return newTablero;
    }
}
