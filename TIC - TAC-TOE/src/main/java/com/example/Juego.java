package com.example;

import java.util.Scanner;

public class Juego {
    private Tablero board;
    private Jugador playerX;
    private Jugador playerO;
    private Jugador currentPlayer;

    public Juego() {
        board = new Tablero();
        playerX = new Jugador('X');
        playerO = new JugadorIA('O'); // Use the correct AI class
        currentPlayer = playerX;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!board.isGameOver()) {
            board.impresionTab();
            if (currentPlayer instanceof JugadorIA) {
                System.out.println("IA (O) está pensando...");
                char[][] bestMove = ((JugadorIA) currentPlayer).bestMove(board);
                board.setTablero(bestMove);
            } else {
                boolean validMove = false;
                while (!validMove) {
                    System.out.println("Jugador " + currentPlayer.getSymbol() + ", ingresa tu movimiento (fila y columna) o el movimiento de pieza (fila inicio, col inicio, fila fin, col fin):");
                    String input = scanner.nextLine();
                    String[] parts = input.split(" ");

                    if (parts.length == 2) { // Movimiento normal
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);
                        validMove = board.hacerMovimiento(row, col, currentPlayer.getSymbol());
                    } else if (parts.length == 4) { // Movimiento de pieza
                        int startRow = Integer.parseInt(parts[0]);
                        int startCol = Integer.parseInt(parts[1]);
                        int endRow = Integer.parseInt(parts[2]);
                        int endCol = Integer.parseInt(parts[3]);
                        validMove = board.moverPieza(startRow, startCol, endRow, endCol);
                    }

                    if (!validMove) {
                        System.out.println("Movimiento inválido. Intenta de nuevo.");
                    }
                }
            }

            if (board.isGanada(currentPlayer.getSymbol())) {
                board.impresionTab();
                System.out.println("Jugador " + currentPlayer.getSymbol() + " gana!");
                return;
            }

            switchPlayer();
        }

        board.impresionTab();
        System.out.println("¡Es un empate!");
    }

    public static void main(String[] args) {
        Juego game = new Juego();
        game.play();
    }
}
