package com.example;

/**
 * O nó é que define a forma.
 *
 * Cada nó guarda apenas o nó próximo e o nó anterior (null quando não liga a nenhuma linha).
 * A forma depende de quantos nós estão ligados:
 *
 *   1 nó                    -> Círculo
 *   2 nós                   -> Reta
 *   3 nós fechados em loop  -> Triângulo
 *   4 nós fechados em loop  -> Retângulo
 *   5 ou mais, em loop      -> Pentágono, Hexágono, Octógono, ...
 */
public class No {
    private final String nome;
    private No proximo;
    private No anterior;

    public No() {
        this("nó");
    }

    /** O nome serve só para mostrar o nó no ecrã. */
    public No(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nó tem de ter um nome");
        }
        this.nome = nome;
    }

    public No getProximo() {
        return proximo;
    }

    public No getAnterior() {
        return anterior;
    }

    /** Liga este nó ao nó "outro" (cria uma linha deste nó até ao outro). */
    public void ligar(No outro) {
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

    /**
     * Forma de chamar o nó: cria uma forma com esse número de nós e devolve o primeiro.
     * 1 nó fica sozinho, 2 nós ligam-se numa reta e 3 ou mais fecham em loop.
     */
    public static No criarForma(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalArgumentException("É preciso pelo menos 1 nó");
        }
        No[] nos = new No[quantidade];
        for (int i = 0; i < quantidade; i++) {
            nos[i] = new No("nó " + (i + 1));
        }
        for (int i = 0; i < quantidade - 1; i++) {
            nos[i].ligar(nos[i + 1]);
        }
        if (quantidade >= 3) {
            nos[quantidade - 1].ligar(nos[0]); // fecha o loop
        }
        return nos[0];
    }

    /** Quantos nós estão ligados a este (contando com ele). */
    public int contarNos() {
        No primeiro = inicio();
        int total = 1;
        No n = primeiro.proximo;
        while (n != null && n != primeiro) {
            total++;
            n = n.proximo;
        }
        return total;
    }

    /** true se, a partir deste nó, se volta a ele andando sempre para o próximo (loop). */
    public boolean estaFechado() {
        No n = this;
        while (n.proximo != null) {
            n = n.proximo;
            if (n == this) return true;
        }
        return false;
    }

    /** A forma definida pelos nós ligados a este. */
    public String forma() {
        int n = contarNos();
        if (n == 1) return "Círculo";
        if (n == 2) return "Reta";
        if (!estaFechado()) return "Forma incompleta (" + n + " nós sem fechar)";
        return switch (n) {
            case 3 -> "Triângulo";
            case 4 -> "Retângulo";
            case 5 -> "Pentágono";
            case 6 -> "Hexágono";
            case 7 -> "Heptágono";
            case 8 -> "Octógono";
            case 9 -> "Eneágono";
            case 10 -> "Decágono";
            default -> "Polígono de " + n + " lados";
        };
    }

    /** Primeiro nó da cadeia (andando para trás), ou um nó qualquer se for um loop. */
    private No inicio() {
        No n = this;
        while (n.anterior != null && n.anterior != this) {
            n = n.anterior;
        }
        return n;
    }

    @Override
    public String toString() {
        return nome;
    }
}