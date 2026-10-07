package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FormasTest {
    private static final double DELTA = 1e-9;

    @Test
    void trianguloTemAreaEPerimetroCorretos() {
        Triangulo t = new Triangulo(0, 0, 4, 0, 0, 3);
        assertEquals(6, t.area(), DELTA);
        assertEquals(12, t.perimetro(), DELTA);
    }

    @Test
    void retanguloTemAreaEPerimetroCorretos() {
        Retangulo r = new Retangulo(0, 0, 5, 2);
        assertEquals(10, r.area(), DELTA);
        assertEquals(14, r.perimetro(), DELTA);
    }

    @Test
    void circuloTemAreaEPerimetroCorretos() {
        Circulo c = new Circulo(1, 1, 2);
        assertEquals(4 * Math.PI, c.area(), DELTA);
        assertEquals(4 * Math.PI, c.perimetro(), DELTA);
    }

    @Test
    void trianguloFormaUmLoopDeNos() {
        Triangulo t = new Triangulo(0, 0, 4, 0, 0, 3);
        No inicio = t.getAtual();
        assertNotNull(t.getProximo());
        assertNotNull(t.getAnterior());
        assertTrue(t.estaFechado());

        t.avancar();
        t.avancar();
        t.avancar();
        assertSame(inicio, t.getAtual());
    }

    @Test
    void circuloTemNosProximoEAnteriorNull() {
        Circulo c = new Circulo(0, 0, 1);
        assertNotNull(c.getAtual());
        assertNull(c.getProximo());
        assertNull(c.getAnterior());
        assertFalse(c.avancar());
        assertFalse(c.recuar());
    }

    @Test
    void desligarUmNoDeixaOsVizinhosComNull() {
        Retangulo r = new Retangulo(0, 0, 5, 2);
        No atual = r.getAtual();
        No proximo = r.getProximo();
        No anterior = r.getAnterior();

        atual.desligar();

        assertNull(atual.getProximo());
        assertNull(atual.getAnterior());
        assertNull(proximo.getAnterior());
        assertNull(anterior.getProximo());
        assertFalse(r.estaFechado());
        assertEquals(0, r.area(), DELTA);
        assertEquals(0, r.perimetro(), DELTA);
    }

    @Test
    void octogonoEUmaFormaNovaComAreaEPerimetroCorretos() {
        Octogono o = new Octogono(0, 0, 1);
        assertTrue(o.estaFechado());
        assertEquals(2 * Math.sqrt(2), o.area(), DELTA);
        assertEquals(16 * Math.sin(Math.PI / 8), o.perimetro(), DELTA);
    }

    @Test
    void poligonoRegularFuncionaParaQualquerNumeroDeLados() {
        Poligono hexagono = Poligono.regular("Hexágono", 6, 0, 0, 1);
        assertEquals(3 * Math.sqrt(3) / 2, hexagono.area(), DELTA);
        assertEquals(6, hexagono.perimetro(), DELTA);
    }

    @Test
    void poligonoComVerticesAMaoAceitaFormasConcavas() {
        Poligono formaL = new Poligono("Forma em L", 0, 0, 2, 0, 2, 1, 1, 1, 1, 2, 0, 2);
        assertEquals(3, formaL.area(), DELTA);
        assertEquals(8, formaL.perimetro(), DELTA);
    }

    @Test
    void formasInvalidasSaoRejeitadas() {
        assertThrows(IllegalArgumentException.class, () -> new Triangulo(0, 0, 1, 1, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> new Retangulo(0, 0, -1, 2));
        assertThrows(IllegalArgumentException.class, () -> new Circulo(0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Circulo(0, 0, Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new Poligono("Linha", 0, 0, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Poligono(" ", 0, 0, 1, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> Poligono.regular("Dois lados", 2, 0, 0, 1));
    }

    @Test
    void poligonoComLinhasCruzadasERejeitado() {
        // Estrela de 5 pontas: liga os vértices de um pentágono de 2 em 2
        int[] ordem = {0, 2, 4, 1, 3};
        double[] c = new double[10];
        for (int i = 0; i < 5; i++) {
            double angulo = 2 * Math.PI * ordem[i] / 5;
            c[2 * i] = Math.cos(angulo);
            c[2 * i + 1] = Math.sin(angulo);
        }
        assertThrows(IllegalArgumentException.class, () -> new Poligono("Estrela", c));
    }

    @Test
    void noNaoPodeLigarASiProprio() {
        No n = new No(0, 0);
        assertThrows(IllegalArgumentException.class, () -> n.ligarProximo(n));
    }
}