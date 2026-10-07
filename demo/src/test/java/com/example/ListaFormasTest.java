package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ListaFormasTest {
    private static final double DELTA = 1e-9;

    @Test
    void listaVaziaTemTudoNull() {
        ListaFormas l = new ListaFormas();
        assertTrue(l.estaVazia());
        assertEquals(0, l.getTamanho());
        assertNull(l.getAtual());
        assertNull(l.getProximo());
        assertNull(l.getAnterior());
        assertFalse(l.avancar());
        assertFalse(l.recuar());
    }

    @Test
    void adicionarLigaOsNosPelaOrdem() {
        ListaFormas l = new ListaFormas();
        Forma a = new Retangulo(0, 0, 2, 3);
        Forma b = new Triangulo(0, 0, 4, 0, 0, 3);
        Forma c = new Circulo(0, 0, 1);
        l.adicionar(a);
        l.adicionar(b);
        l.adicionar(c);

        assertEquals(3, l.getTamanho());
        assertSame(a, l.getAtual());
        assertNull(l.getAnterior());
        assertSame(b, l.getProximo());

        assertTrue(l.avancar());
        assertSame(a, l.getAnterior());
        assertSame(c, l.getProximo());

        assertTrue(l.avancar());
        assertSame(c, l.getAtual());
        assertNull(l.getProximo());
        assertFalse(l.avancar());

        assertTrue(l.recuar());
        assertSame(b, l.getAtual());
    }

    @Test
    void removerUmNoDoMeioLigaOsVizinhos() {
        ListaFormas l = new ListaFormas();
        Forma a = new Retangulo(0, 0, 2, 3);
        Forma b = new Triangulo(0, 0, 4, 0, 0, 3);
        Forma c = new Circulo(0, 0, 1);
        l.adicionar(a);
        l.adicionar(b);
        l.adicionar(c);

        assertTrue(l.remover(b));
        assertEquals(2, l.getTamanho());
        assertSame(a, l.getAtual());
        assertSame(c, l.getProximo());
        assertTrue(l.avancar());
        assertSame(a, l.getAnterior());
        assertFalse(l.remover(b));
    }

    @Test
    void removerOAtualMoveOCursorParaUmVizinho() {
        ListaFormas l = new ListaFormas();
        Forma a = new Retangulo(0, 0, 2, 3);
        Forma b = new Triangulo(0, 0, 4, 0, 0, 3);
        Forma c = new Circulo(0, 0, 1);
        l.adicionar(a);
        l.adicionar(b);
        l.adicionar(c);

        assertTrue(l.remover(a));
        assertSame(b, l.getAtual());
        assertNull(l.getAnterior());

        assertTrue(l.remover(b));
        assertSame(c, l.getAtual());

        assertTrue(l.remover(c));
        assertNull(l.getAtual());
        assertTrue(l.estaVazia());
    }

    @Test
    void areaTotalSomaAsAreasDasFormas() {
        ListaFormas l = new ListaFormas();
        l.adicionar(new Retangulo(0, 0, 2, 3));
        l.adicionar(new Triangulo(0, 0, 4, 0, 0, 3));
        l.adicionar(new Circulo(0, 0, 1));
        assertEquals(12 + Math.PI, l.areaTotal(), DELTA);
    }

    @Test
    void formaNullERejeitada() {
        ListaFormas l = new ListaFormas();
        assertThrows(IllegalArgumentException.class, () -> l.adicionar(null));
    }
}