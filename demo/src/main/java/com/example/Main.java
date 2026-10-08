package com.example;

import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {
        // O número de nós decide a forma
        Forma circulo = FabricaFormas.criar(new No(1, 1), 2);                                   // 1 nó  -> círculo (com raio)
        Forma reta = FabricaFormas.criar(new No(0, 0), new No(3, 4));                           // 2 nós -> reta
        Forma triangulo = FabricaFormas.criar(new No(0, 0), new No(4, 0), new No(0, 3));        // 3 nós -> triângulo
        Forma retangulo = FabricaFormas.criar(
                new No(0, 0), new No(5, 0), new No(5, 2), new No(0, 2));                        // 4 nós em retângulo -> retângulo
        Forma quadrilatero = FabricaFormas.criar(
                new No(0, 0), new No(4, 0), new No(3, 2), new No(1, 2));                        // 4 nós quaisquer -> quadrilátero
        Forma pentagono = FabricaFormas.criar(
                new No(2, 0), new No(4, 1.5), new No(3, 4), new No(1, 4), new No(0, 1.5));      // 5 nós -> pentágono

        // Também se podem criar formas com classes próprias (ver Octogono)
        Forma octogono = new Octogono(0, 0, 1);

        // Lista ligada de formas
        ListaFormas lista = new ListaFormas();
        lista.adicionar(circulo);
        lista.adicionar(reta);
        lista.adicionar(triangulo);
        lista.adicionar(retangulo);
        lista.adicionar(quadrilatero);
        lista.adicionar(pentagono);
        lista.adicionar(octogono);

        System.out.println("--- Lista (início -> fim) ---");
        lista.listarInicioFim();
        System.out.println("--- Lista (fim -> início) ---");
        lista.listarFimInicio();
        System.out.printf("Área total: %.2f%n%n", lista.areaTotal());

        // Nó atual / próximo / anterior da lista de formas
        lista.irParaInicio();
        lista.avancar();
        System.out.println("Na lista: anterior=" + nome(lista.getAnterior())
                + " | atual=" + nome(lista.getAtual())
                + " | próximo=" + nome(lista.getProximo()));
        lista.irParaInicio();
        System.out.println("No início: anterior=" + nome(lista.getAnterior())
                + " | atual=" + nome(lista.getAtual())
                + " | próximo=" + nome(lista.getProximo()));

        // Nós (vértices) de cada forma
        System.out.println("\n--- Nós de cada forma ---");
        lista.irParaInicio();
        do {
            Forma f = lista.getAtual();
            System.out.println(f.getNome() + ":");
            mostrarNos(f);
        } while (lista.avancar());

        // Reta: percorrer os 2 nós (as pontas têm null)
        System.out.println("\nA percorrer a reta:");
        System.out.println("  atual=" + texto(reta.getAtual()) + " | anterior=" + texto(reta.getAnterior())
                + " | próximo=" + texto(reta.getProximo()));
        reta.avancar();
        System.out.println("  atual=" + texto(reta.getAtual()) + " | anterior=" + texto(reta.getAnterior())
                + " | próximo=" + texto(reta.getProximo()));

        // Formas inválidas são rejeitadas
        System.out.println();
        tentar("1 nó sem raio", () -> FabricaFormas.criar(new No(0, 0)));
        tentar("3 nós em linha reta", () -> FabricaFormas.criar(new No(0, 0), new No(1, 1), new No(2, 2)));
        tentar("nós já usados noutra forma", () -> FabricaFormas.criar(triangulo.getAtual(), triangulo.getProximo()));

        // Percorrer o retângulo: dá a volta pelos 4 nós e volta ao início
        System.out.println("\nA percorrer o retângulo:");
        for (int i = 0; i < 4; i++) {
            System.out.println("  " + retangulo.getAtual());
            retangulo.avancar();
        }

        // Desligar o nó atual: os vizinhos deixam de ter ligação a ele (ficam null)
        No removido = retangulo.getAtual();
        No antes = retangulo.getAnterior();
        No depois = retangulo.getProximo();
        removido.desligar();

        System.out.println("\nDepois de desligar o nó atual do retângulo:");
        System.out.println("  nó desligado : próximo=" + texto(removido.getProximo())
                + ", anterior=" + texto(removido.getAnterior()));
        System.out.println("  nó que vinha antes : próximo=" + texto(antes.getProximo()));
        System.out.println("  nó que vinha depois: anterior=" + texto(depois.getAnterior()));
        System.out.println("  " + retangulo);

        // Remover uma forma da lista
        lista.remover(circulo);
        System.out.println("\n--- Depois de remover o círculo (" + lista.getTamanho() + " formas) ---");
        lista.listarInicioFim();
    }

    private static void tentar(String descricao, Supplier<Forma> criacao) {
        try {
            Forma f = criacao.get();
            System.out.println("Criada (" + descricao + "): " + f);
        } catch (IllegalArgumentException e) {
            System.out.println("Forma rejeitada (" + descricao + "): " + e.getMessage());
        }
    }

    private static void mostrarNos(Forma f) {
        System.out.println("  anterior=" + texto(f.getAnterior())
                + " | atual=" + texto(f.getAtual())
                + " | próximo=" + texto(f.getProximo()));
    }

    private static String texto(No n) {
        return n == null ? "null" : n.toString();
    }

    private static String nome(Forma f) {
        return f == null ? "null" : f.getNome();
    }
}