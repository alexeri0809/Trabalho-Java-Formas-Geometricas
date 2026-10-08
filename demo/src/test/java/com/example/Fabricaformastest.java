package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class FabricaFormasTest {
    private static final double DELTA = 1e-9;

    @Test
    void umNoComRaioEUmCirculo() {
        Forma f = FabricaFormas.criar(new No(1, 1), 2);
        assertTrue(f instanceof Circulo);
        assertNull(f.getProximo());
        assertNull(f.getAnterior());
        assertEquals(4 * Math.PI, f.area(), DELTA);
        assertEquals(4 * Math.PI, f.perimetro(), DELTA);
    }

    @Test
    void doisNosFormamUmaReta() {
        No a = new No(0, 0);
        No b = new No(3, 4);
        Forma f = FabricaFormas.criar(a, b);

        assertTrue(f instanceof Reta);
        assertSame(a, f.getAtual());
        assertNull(f.getAnterior());
        assertSame(b, f.getProximo());
        assertEquals(0, f.area(), DELTA);
        assertEquals(5, f.perimetro(), DELTA);

        assertTrue(f.avancar());
        assertSame(b, f.getAtual());
        assertSame(a, f.getAnterior());
        assertNull(f.getProximo());
        assertFalse(f.avancar());
    }

    @Test
    void tresNosFormamUmTriangulo() {
        No a = new No(0, 0);
        No b = new No(4, 0);
        No c = new No(0, 3);
        Forma f = FabricaFormas.criar(a, b, c);

        assertTrue(f instanceof Triangulo);
        assertSame(a, f.getAtual());
        assertSame(b, f.getProximo());
        assertSame(c, f.getAnterior());
        assertEquals(6, f.area(), DELTA);
        assertEquals(12, f.perimetro(), DELTA);
    }

    @Test
    void quatroNosEmRetanguloFormamUmRetangulo() {
        Forma f = FabricaFormas.criar(new No(0, 0), new No(5, 0), new No(5, 2), new No(0, 2));
        assertTrue(f instanceof Retangulo);
        assertEquals(10, f.area(), DELTA);
        assertEquals(14, f.perimetro(), DELTA);
    }

    @Test
    void quatroNosQuaisquerFormamUmQuadrilatero() {
        Forma f = FabricaFormas.criar(new No(0, 0), new No(4, 0), new No(3, 2), new No(1, 2));
        assertFalse(f instanceof Retangulo);
        assertEquals("Quadrilátero", f.getNome());
        assertEquals(6, f.area(), DELTA);
    }

    @Test
    void maisNosFormamPoligonosComONomeCerto() {
        Forma pentagono = FabricaFormas.criar(
                new No(2, 0), new No(4, 1.5), new No(3, 4), new No(1, 4), new No(0, 1.5));
        assertEquals("Pentágono", pentagono.getNome());
        assertTrue(pentagono.area() > 0);

        No[] nos = new No[8];
        for (int i = 0; i < 8; i++) {
            double angulo = 2 * Math.PI * i / 8;
            nos[i] = new No(Math.cos(angulo), Math.sin(angulo));
        }
        Forma octogono = FabricaFormas.criar(nos);
        assertEquals("Octógono", octogono.getNome());
        assertEquals(2 * Math.sqrt(2), octogono.area(), DELTA);
    }

    @Test
    void umNoSemRaioERejeitado() {
        assertThrows(IllegalArgumentException.class, () -> FabricaFormas.criar(new No(0, 0)));
    }

    @Test
    void nosInvalidosSaoRejeitados() {
        assertThrows(IllegalArgumentException.class, () -> FabricaFormas.criar((No[]) null));
        assertThrows(IllegalArgumentException.class,
                () -> FabricaFormas.criar(new No[] {new No(0, 0), null}));
        assertThrows(IllegalArgumentException.class,
                () -> FabricaFormas.criar(new No(1, 1), new No(1, 1)));
        assertThrows(IllegalArgumentException.class,
                () -> FabricaFormas.criar(new No(0, 0), new No(1, 1), new No(2, 2)));
    }

    @Test
    void nosJaUsadosEmOutraFormaSaoRejeitados() {
        No a = new No(0, 0);
        No b = new No(4, 0);
        No c = new No(0, 3);
        FabricaFormas.criar(a, b, c);
        assertThrows(IllegalArgumentException.class, () -> FabricaFormas.criar(a, b));
    }

    @Test
    void omesmoNoNaoPodeSerUsadoDuasVezes() {
        No a = new No(0, 0);
        No b = new No(4, 0);
        assertThrows(IllegalArgumentException.class, () -> FabricaFormas.criar(a, a, b));
    }
}