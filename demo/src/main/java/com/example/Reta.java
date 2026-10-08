package com.example;

/**
 * Forma de 2 nós: um nó liga ao outro, mas não há volta.
 * O nó inicial não tem anterior (null) e o nó final não tem próximo (null).
 */
public class Reta extends Forma {
    private final No inicio;

    public Reta(double x1, double y1, double x2, double y2) {
        this(new No(x1, y1), new No(x2, y2));
    }

    public Reta(No a, No b) {
        super("Reta", ligar(a, b));
        this.inicio = a;
    }

    private static No ligar(No a, No b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("A reta precisa de 2 nós");
        }
        if (a == b) {
            throw new IllegalArgumentException("A reta precisa de 2 nós diferentes");
        }
        if (a.getProximo() != null || a.getAnterior() != null
                || b.getProximo() != null || b.getAnterior() != null) {
            throw new IllegalArgumentException("Os nós já estão ligados a outras linhas");
        }
        if (Math.hypot(b.getX() - a.getX(), b.getY() - a.getY()) < 1e-9) {
            throw new IllegalArgumentException("Os 2 nós estão na mesma posição");
        }
        a.ligarProximo(b);
        return a;
    }

    /** Uma reta não tem área. */
    @Override
    public double area() {
        return 0;
    }

    /** Comprimento da reta (0 se os nós tiverem sido desligados). */
    @Override
    public double perimetro() {
        return inicio.comprimentoAteProximo();
    }
}