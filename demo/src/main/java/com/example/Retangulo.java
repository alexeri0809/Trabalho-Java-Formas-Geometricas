package com.example;

public class Retangulo extends Poligono {

    public Retangulo(double x, double y, double largura, double altura) {
        super("Retângulo", coordenadasValidas(x, y, largura, altura));
    }

    private static double[] coordenadasValidas(double x, double y, double largura, double altura) {
        if (!(largura > 0) || !(altura > 0)) {
            throw new IllegalArgumentException("Largura e altura têm de ser positivas");
        }
        return new double[] {
            x, y,
            x + largura, y,
            x + largura, y + altura,
            x, y + altura
        };
    }
}