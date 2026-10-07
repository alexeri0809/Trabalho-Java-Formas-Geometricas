package com.example;

/**
 * O círculo não tem cantos nem linhas: tem um único nó (o centro)
 * que não liga a nenhuma linha, por isso o próximo e o anterior são null.
 */
public class Circulo extends Forma {
    private final double raio;

    public Circulo(double cx, double cy, double raio) {
        super("Círculo", criarCentro(cx, cy, raio));
        this.raio = raio;
    }

    private static No criarCentro(double cx, double cy, double raio) {
        if (!(raio > 0) || !Double.isFinite(raio)) {
            throw new IllegalArgumentException("O raio tem de ser positivo");
        }
        return new No(cx, cy);
    }

    @Override
    public double area() {
        return Math.PI * raio * raio;
    }

    @Override
    public double perimetro() {
        return 2 * Math.PI * raio;
    }
}