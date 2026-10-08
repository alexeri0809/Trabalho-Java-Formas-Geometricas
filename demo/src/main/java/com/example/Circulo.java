package com.example;

/**
 * Forma de 1 nó: o centro. O nó não liga a nenhuma linha,
 * por isso o próximo e o anterior são sempre null.
 */
public class Circulo extends Forma {
    private final double raio;

    public Circulo(double cx, double cy, double raio) {
        this(new No(cx, cy), raio);
    }

    public Circulo(No centro, double raio) {
        super("Círculo", validar(centro, raio));
        this.raio = raio;
    }

    private static No validar(No centro, double raio) {
        if (centro == null) {
            throw new IllegalArgumentException("O círculo precisa de um nó (o centro)");
        }
        if (centro.getProximo() != null || centro.getAnterior() != null) {
            throw new IllegalArgumentException("O nó do círculo não pode estar ligado a outros nós");
        }
        if (!(raio > 0) || !Double.isFinite(raio)) {
            throw new IllegalArgumentException("O raio tem de ser positivo");
        }
        return centro;
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