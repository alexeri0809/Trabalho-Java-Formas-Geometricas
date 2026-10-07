package com.example;

/**
 * Exemplo de como criar uma forma nova: basta estender Poligono e indicar os vértices.
 * Aqui é um octógono regular (8 lados iguais) à volta do centro (cx, cy).
 */
public class Octogono extends Poligono {

    public Octogono(double cx, double cy, double raio) {
        super("Octógono", coordenadasRegulares(8, cx, cy, raio));
    }
}