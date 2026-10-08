package com.example;

public class Main {

    public static void main(String[] args) {
        // O número de nós define a forma
        System.out.println("--- O número de nós define a forma ---");
        for (int quantidade = 1; quantidade <= 10; quantidade++) {
            No no = No.criarForma(quantidade);
            System.out.println(quantidade + " nó(s) -> " + no.forma());
        }
        System.out.println("12 nós -> " + No.criarForma(12).forma());

        // Montar uma forma à mão, nó a nó
        No a = new No("A");
        No b = new No("B");
        No c = new No("C");

        a.ligar(b);
        b.ligar(c);
        System.out.println("\nA -> B -> C (sem fechar): " + a.forma());

        c.ligar(a);
        System.out.println("A -> B -> C -> A (fechado): " + a.forma());
        mostrar(a);
        mostrar(b);
        mostrar(c);

        // Desligar um nó: os vizinhos ficam com null e as formas mudam
        a.desligar();
        System.out.println("\nDepois de desligar o A:");
        System.out.println("  A sozinho: " + a.forma());
        System.out.println("  B e C ligados: " + b.forma());
        mostrar(a);
        mostrar(b);
        mostrar(c);
    }

    private static void mostrar(No n) {
        System.out.println("  " + n + ": anterior=" + texto(n.getAnterior())
                + " | próximo=" + texto(n.getProximo()));
    }

    private static String texto(No n) {
        return n == null ? "null" : n.toString();
    }
}