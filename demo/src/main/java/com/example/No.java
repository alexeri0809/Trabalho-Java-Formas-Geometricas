package com.example;

/**
 * Nó da lista ligada = vértice (canto) de uma forma.
 * Guarda apenas as coordenadas e as ligações ao nó próximo e ao nó anterior.
 * Se o nó não estiver ligado a nenhuma linha num dos lados, essa referência é null.
 */
public class No {
    private final double x;
    private final double y;
    private No proximo;
    private No anterior;

    public No(double x, double y) {
        if (!Double.isFinite(x) || !Double.isFinite(y)) {
            throw new IllegalArgumentException("As coordenadas têm de ser números finitos");
        }
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public No getProximo() {
        return proximo;
    }

    public No getAnterior() {
        return anterior;
    }

    /** Cria uma linha deste nó até ao nó "outro". */
    public void ligarProximo(No outro) {
        if (outro == null) {
            throw new IllegalArgumentException("O nó a ligar não pode ser null");
        }
        if (outro == this) {
            throw new IllegalArgumentException("Um nó não pode ligar a si próprio");
        }
        // Se já havia ligações nestes lados, quebram-se (ficam null)
        if (this.proximo != null) this.proximo.anterior = null;
        if (outro.anterior != null) outro.anterior.proximo = null;

        this.proximo = outro;
        outro.anterior = this;
    }

    /** Remove as linhas deste nó: as referências dos vizinhos para ele ficam null. */
    public void desligar() {
        if (anterior != null) anterior.proximo = null;
        if (proximo != null) proximo.anterior = null;
        anterior = null;
        proximo = null;
    }

    /** Comprimento da linha até ao nó próximo (0 se não houver linha). */
    public double comprimentoAteProximo() {
        if (proximo == null) return 0;
        return Math.hypot(proximo.x - x, proximo.y - y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}