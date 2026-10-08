package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NoTest {

    @Test
    void umNoSozinhoEUmCirculo() {
        No n = new No();
        assertEquals("Círculo", n.forma());
        assertEquals(1, n.contarNos());
        assertNull(n.getProximo());
        assertNull(n.getAnterior());
    }

    @Test
    void doisNosLigadosSaoUmaReta() {
        No a = new No("A");
        No b = new No("B");
        a.ligar(b);

        assertEquals("Reta", a.forma());
        assertEquals("Reta", b.forma());
        assertFalse(a.estaFechado());
        assertNull(a.getAnterior());
        assertSame(b, a.getProximo());
        assertSame(a, b.getAnterior());
        assertNull(b.getProximo());
    }

    @Test
    void tresNosFechadosEmLoopSaoUmTriangulo() {
        No a = No.criarForma(3);
        assertEquals("Triângulo", a.forma());
        assertEquals(3, a.contarNos());
        assertTrue(a.estaFechado());
    }

    @Test
    void oNumeroDeNosDefineAForma() {
        String[] esperadas = {
            "Círculo", "Reta", "Triângulo", "Retângulo", "Pentágono",
            "Hexágono", "Heptágono", "Octógono", "Eneágono", "Decágono"
        };
        for (int i = 1; i <= 10; i++) {
            assertEquals(esperadas[i - 1], No.criarForma(i).forma());
        }
        assertEquals("Polígono de 12 lados", No.criarForma(12).forma());
    }

    @Test
    void todosOsNosDaFormaDaoOMesmoResultado() {
        No n = No.criarForma(5);
        for (int i = 0; i < 5; i++) {
            assertEquals("Pentágono", n.forma());
            assertEquals(5, n.contarNos());
            n = n.getProximo();
        }
    }

    @Test
    void tresNosSemFecharSaoUmaFormaIncompleta() {
        No a = new No("A");
        No b = new No("B");
        No c = new No("C");
        a.ligar(b);
        b.ligar(c);

        assertEquals(3, a.contarNos());
        assertEquals(3, c.contarNos());
        assertFalse(a.estaFechado());
        assertTrue(a.forma().startsWith("Forma incompleta"));
    }

    @Test
    void desligarUmNoDeixaOsVizinhosComNull() {
        No a = No.criarForma(3);
        No b = a.getProximo();
        No c = a.getAnterior();

        a.desligar();

        assertNull(a.getProximo());
        assertNull(a.getAnterior());
        assertNull(b.getAnterior());
        assertNull(c.getProximo());
        assertEquals("Círculo", a.forma());
        assertEquals("Reta", b.forma());
    }

    @Test
    void entradasInvalidasSaoRejeitadas() {
        No n = new No("A");
        assertThrows(IllegalArgumentException.class, () -> n.ligar(null));
        assertThrows(IllegalArgumentException.class, () -> n.ligar(n));
        assertThrows(IllegalArgumentException.class, () -> No.criarForma(0));
        assertThrows(IllegalArgumentException.class, () -> new No(" "));
    }
}