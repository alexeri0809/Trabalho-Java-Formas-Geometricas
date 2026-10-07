package com.example;

/**
 * Base de todas as formas. Só objetos que sejam uma Forma podem ser usados pelo programa.
 * Para criar uma forma nova basta estender Forma (ou Poligono, se for feita de vértices).
 */
public abstract class Forma {
    private final String nome;
    private No atual;

    protected Forma(String nome, No inicial) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("A forma tem de ter um nome");
        }
        if (inicial == null) {
            throw new IllegalArgumentException("A forma tem de ter pelo menos um nó");
        }
        this.nome = nome;
        this.atual = inicial;
    }

    public String getNome() {
        return nome;
    }

    /** Nó atual (posição do "cursor" na forma). */
    public No getAtual() {
        return atual;
    }

    /** Nó próximo do atual, ou null se o atual não liga a nenhuma linha nesse lado. */
    public No getProximo() {
        return atual == null ? null : atual.getProximo();
    }

    /** Nó antecessor do atual, ou null se o atual não liga a nenhuma linha nesse lado. */
    public No getAnterior() {
        return atual == null ? null : atual.getAnterior();
    }

    /** Move o nó atual para o próximo. Devolve false se não houver próximo (null). */
    public boolean avancar() {
        No p = getProximo();
        if (p == null) return false;
        atual = p;
        return true;
    }

    /** Move o nó atual para o anterior. Devolve false se não houver anterior (null). */
    public boolean recuar() {
        No a = getAnterior();
        if (a == null) return false;
        atual = a;
        return true;
    }

    public abstract double area();

    public abstract double perimetro();

    @Override
    public String toString() {
        return String.format("%s [área=%.2f, perímetro=%.2f]", nome, area(), perimetro());
    }
}