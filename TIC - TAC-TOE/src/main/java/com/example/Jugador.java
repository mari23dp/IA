package com.example;


public class Jugador {
    protected char simbolo;

    public Jugador(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSymbol() {
        return simbolo;
    }

    public void hacerMovimiento(Tablero tablero, int fila, int col) {
        tablero.hacerMovimiento(fila, col, simbolo);
    }
}
