package com.example;

/** Forma de 3 nós. */
public class Triangulo extends Poligono {

    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        super("Triângulo", x1, y1, x2, y2, x3, y3);
    }

    public Triangulo(No a, No b, No c) {
        super("Triângulo", a, b, c);
    }
}