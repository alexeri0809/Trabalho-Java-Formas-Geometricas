package com.example;

public class Main {

    public static void main(String[] args) {
        // Formas base
        Triangulo triangulo = new Triangulo(0, 0, 4, 0, 0, 3);
        Retangulo retangulo = new Retangulo(0, 0, 5, 2);
        Circulo circulo = new Circulo(1, 1, 2);

        // Formas novas - 3 maneiras de as criar:
        Octogono octogono = new Octogono(0, 0, 1);                                        // 1) classe própria
        Poligono hexagono = Poligono.regular("Hexágono", 6, 0, 0, 1);                     // 2) polígono regular
        Poligono formaL = new Poligono("Forma em L", 0, 0, 2, 0, 2, 1, 1, 1, 1, 2, 0, 2); // 3) vértices à mão

        // Lista ligada de formas: só aceita objetos Forma
        ListaFormas lista = new ListaFormas();
        lista.adicionar(triangulo);
        lista.adicionar(retangulo);
        lista.adicionar(circulo);
        lista.adicionar(octogono);
        lista.adicionar(hexagono);
        lista.adicionar(formaL);

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

        // Formas inválidas são rejeitadas
        try {
            new Poligono("Linha", 0, 0, 1, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("\nForma rejeitada: " + e.getMessage());
        }

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