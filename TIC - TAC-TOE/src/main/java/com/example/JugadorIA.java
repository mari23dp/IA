package com.example;

import java.util.List;

public class JugadorIA extends Jugador {
    private static final int DEPTH = 2;

    public JugadorIA(char symbol) {
        super(symbol);
    }

    public char[][] bestMove(Tablero tablero) {
        return minimax(tablero, DEPTH, true, Integer.MIN_VALUE, Integer.MAX_VALUE).getBoard();
    }

    // Method to get the opponent's symbol
    public char getOpponentSymbol() {
        return (getSymbol() == 'X') ? 'O' : 'X';
    }

    // Minimax algorithm implementation with alpha-beta pruning
    private Move minimax(Tablero board, int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0 || board.isGameOver()) {
            return new Move(evaluateBoard(board, getSymbol()));
        }

        List<char[][]> successors = board.generarSucesores(isMaximizing ? getSymbol() : getOpponentSymbol());
        Move bestMove = isMaximizing ? new Move(Integer.MIN_VALUE) : new Move(Integer.MAX_VALUE);

        for (char[][] successor : successors) {
            board.setTablero(successor);
            Move currentMove;

            if (isMaximizing) {
                currentMove = minimax(board, depth - 1, false, alpha, beta);
                if (currentMove.getScore() > bestMove.getScore()) {
                    bestMove.setScore(currentMove.getScore());
                    bestMove.setBoard(copyBoard(successor));
                }
                alpha = Math.max(alpha, bestMove.getScore());
            } else {
                currentMove = minimax(board, depth - 1, true, alpha, beta);
                if (currentMove.getScore() < bestMove.getScore()) {
                    bestMove.setScore(currentMove.getScore());
                    bestMove.setBoard(copyBoard(successor));
                }
                beta = Math.min(beta, bestMove.getScore());
            }

            // Pruning
            if (beta <= alpha) {
                break;
            }

            // Restore the board state after each move
            board.setTablero(successor);
        }

        return bestMove;
    }

    // Evaluate the board for the AI's symbol
    private int evaluateBoard(Tablero board, char aiSymbol) {
        char opponentSymbol = getOpponentSymbol();

        if (board.isGanada(aiSymbol)) {
            return 10;
        } else if (board.isGanada(opponentSymbol)) {
            return -10;
        } else {
            return 0;
        }
    }

    // Copy the board to avoid side effects during recursion
    private char[][] copyBoard(char[][] board) {
        char[][] newTablero = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                newTablero[i][j] = board[i][j];
            }
        }
        return newTablero;
    }

    // Inner class to represent a move and its associated score
    private static class Move {
        private int score;
        private char[][] board;

        public Move(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public char[][] getBoard() {
            return board;
        }

        public void setBoard(char[][] board) {
            this.board = board;
        }
    }
}
