package com.example;

/**
 * Cria a forma certa a partir dos nós: o número de nós decide a forma.
 *
 *   1 nó  (+ raio)  -> Círculo
 *   2 nós           -> Reta
 *   3 nós           -> Triângulo
 *   4 nós           -> Retângulo (se tiver 4 ângulos retos) ou Quadrilátero
 *   5 ou mais nós   -> Polígono (Pentágono, Hexágono, Octógono, ...)
 *
 * Os nós são ligados pela ordem em que são dados e têm de estar livres
 * (não ligados a nenhuma outra forma).
 */
public final class FabricaFormas {

    private FabricaFormas() {
    }

    /** 1 nó = círculo. O nó é o centro e o raio indica o tamanho. */
    public static Forma criar(No centro, double raio) {
        return new Circulo(centro, raio);
    }

    /** Cria a forma correspondente ao número de nós (2 ou mais). */
    public static Forma criar(No... nos) {
        if (nos == null || nos.length == 0) {
            throw new IllegalArgumentException("Preciso de pelo menos 1 nó");
        }
        for (No n : nos) {
            if (n == null) {
                throw new IllegalArgumentException("Os nós não podem ser null");
            }
        }
        if (nos.length == 1) {
            throw new IllegalArgumentException(
                    "Com 1 nó a forma é um círculo: indica também o raio, com criar(no, raio)");
        }
        if (nos.length == 2) {
            return new Reta(nos[0], nos[1]);
        }
        if (nos.length == 3) {
            return new Triangulo(nos[0], nos[1], nos[2]);
        }
        if (nos.length == 4 && Retangulo.formaRetangulo(nos)) {
            return new Retangulo(nos[0], nos[1], nos[2], nos[3]);
        }
        return new Poligono(nomeDoPoligono(nos.length), nos);
    }

    private static String nomeDoPoligono(int nos) {
        return switch (nos) {
            case 4 -> "Quadrilátero";
            case 5 -> "Pentágono";
            case 6 -> "Hexágono";
            case 7 -> "Heptágono";
            case 8 -> "Octógono";
            case 9 -> "Eneágono";
            case 10 -> "Decágono";
            default -> "Polígono de " + nos + " lados";
        };
    }
}