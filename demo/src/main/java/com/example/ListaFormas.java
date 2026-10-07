package com.example;

/**
 * Lista duplamente ligada de formas (triângulos, retângulos, círculos, octógonos, ...).
 * Cada nó guarda uma forma e apenas a referência ao nó próximo e ao nó anterior;
 * quando não há vizinho nesse lado, a referência é null.
 *
 * A lista só aceita objetos Forma (qualquer outra coisa não compila) e rejeita null.
 */
public class ListaFormas {

    private static class NoForma {
        private final Forma forma;
        private NoForma proximo;
        private NoForma anterior;

        private NoForma(Forma forma) {
            this.forma = forma;
        }
    }

    private NoForma primeiro;
    private NoForma ultimo;
    private NoForma atual;
    private int tamanho;

    /** Acrescenta uma forma no fim da lista. */
    public void adicionar(Forma forma) {
        if (forma == null) {
            throw new IllegalArgumentException("A forma não pode ser null");
        }
        NoForma novo = new NoForma(forma);
        if (primeiro == null) {
            primeiro = novo;
            atual = novo;
        } else {
            ultimo.proximo = novo;
            novo.anterior = ultimo;
        }
        ultimo = novo;
        tamanho++;
    }

    /** Remove a forma da lista. Devolve false se ela não estiver lá. */
    public boolean remover(Forma forma) {
        for (NoForma n = primeiro; n != null; n = n.proximo) {
            if (n.forma == forma) {
                if (n.anterior != null) n.anterior.proximo = n.proximo;
                else primeiro = n.proximo;

                if (n.proximo != null) n.proximo.anterior = n.anterior;
                else ultimo = n.anterior;

                // Se o nó atual foi removido, o cursor passa para um vizinho (ou null se a lista ficar vazia)
                if (atual == n) {
                    atual = (n.proximo != null) ? n.proximo : n.anterior;
                }
                n.proximo = null;
                n.anterior = null;
                tamanho--;
                return true;
            }
        }
        return false;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    /** Forma do nó atual, ou null se a lista estiver vazia. */
    public Forma getAtual() {
        return atual == null ? null : atual.forma;
    }

    /** Forma do nó próximo, ou null se o atual não tiver próximo. */
    public Forma getProximo() {
        return (atual == null || atual.proximo == null) ? null : atual.proximo.forma;
    }

    /** Forma do nó anterior, ou null se o atual não tiver anterior. */
    public Forma getAnterior() {
        return (atual == null || atual.anterior == null) ? null : atual.anterior.forma;
    }

    /** Move o nó atual para o próximo. Devolve false se não houver próximo (null). */
    public boolean avancar() {
        if (atual == null || atual.proximo == null) return false;
        atual = atual.proximo;
        return true;
    }

    /** Move o nó atual para o anterior. Devolve false se não houver anterior (null). */
    public boolean recuar() {
        if (atual == null || atual.anterior == null) return false;
        atual = atual.anterior;
        return true;
    }

    public void irParaInicio() {
        atual = primeiro;
    }

    public double areaTotal() {
        double soma = 0;
        for (NoForma n = primeiro; n != null; n = n.proximo) {
            soma += n.forma.area();
        }
        return soma;
    }

    public void listarInicioFim() {
        int i = 1;
        for (NoForma n = primeiro; n != null; n = n.proximo) {
            System.out.println("  " + i++ + ". " + n.forma);
        }
    }

    public void listarFimInicio() {
        int i = tamanho;
        for (NoForma n = ultimo; n != null; n = n.anterior) {
            System.out.println("  " + i-- + ". " + n.forma);
        }
    }
}