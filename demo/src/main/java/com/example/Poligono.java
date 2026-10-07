package com.example;

/**
 * Forma feita de vértices (nós) ligados em loop: o último nó liga ao primeiro.
 * Qualquer polígono (octógono, pentágono, forma em L, ...) pode ser criado
 * diretamente com "new Poligono(nome, x1, y1, x2, y2, ...)", com Poligono.regular(...)
 * ou criando uma classe que estenda Poligono (ver Octogono).
 *
 * Só são aceites polígonos válidos: pelo menos 3 vértices, área maior que zero
 * e linhas que não se cruzam. Se algum nó for desligado depois, o loop deixa de
 * estar fechado: o polígono fica incompleto e a área e o perímetro passam a 0.
 */
public class Poligono extends Forma {
    private final No primeiro;
    private final int numVertices;

    /** coordenadas = x1, y1, x2, y2, ... (por ordem em que os vértices se ligam). */
    public Poligono(String nome, double... coordenadas) {
        super(nome, criarAnel(validar(coordenadas)));
        this.primeiro = getAtual();
        this.numVertices = coordenadas.length / 2;
    }

    /** Polígono regular (todos os lados iguais) com "lados" vértices à volta do centro (cx, cy). */
    public static Poligono regular(String nome, int lados, double cx, double cy, double raio) {
        return new Poligono(nome, coordenadasRegulares(lados, cx, cy, raio));
    }

    /** Coordenadas dos vértices de um polígono regular, para uso das subclasses. */
    protected static double[] coordenadasRegulares(int lados, double cx, double cy, double raio) {
        if (lados < 3) {
            throw new IllegalArgumentException("Um polígono precisa de pelo menos 3 lados");
        }
        if (!(raio > 0) || !Double.isFinite(raio)) {
            throw new IllegalArgumentException("O raio tem de ser positivo");
        }
        double[] c = new double[lados * 2];
        for (int i = 0; i < lados; i++) {
            double angulo = 2 * Math.PI * i / lados;
            c[2 * i] = cx + raio * Math.cos(angulo);
            c[2 * i + 1] = cy + raio * Math.sin(angulo);
        }
        return c;
    }

    private static double[] validar(double[] c) {
        if (c == null || c.length < 6 || c.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Um polígono precisa de pelo menos 3 vértices (pares x, y)");
        }
        for (double v : c) {
            if (!Double.isFinite(v)) {
                throw new IllegalArgumentException("As coordenadas têm de ser números finitos");
            }
        }
        int n = c.length / 2;

        double soma = 0;
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            soma += c[2 * i] * c[2 * j + 1] - c[2 * j] * c[2 * i + 1];
        }
        if (Math.abs(soma) / 2 < 1e-9) {
            throw new IllegalArgumentException("Os pontos não formam um polígono válido (área nula)");
        }
        if (temLinhasCruzadas(c, n)) {
            throw new IllegalArgumentException("As linhas do polígono não se podem cruzar");
        }
        return c;
    }

    private static boolean temLinhasCruzadas(double[] c, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                boolean vizinhas = (j == i + 1) || (i == 0 && j == n - 1);
                if (vizinhas) continue;
                if (cruzam(c, i, (i + 1) % n, j, (j + 1) % n)) return true;
            }
        }
        return false;
    }

    /** true se a linha a-b cruza a linha p-q (índices de vértices). */
    private static boolean cruzam(double[] c, int a, int b, int p, int q) {
        double o1 = orientacao(c, a, b, p);
        double o2 = orientacao(c, a, b, q);
        double o3 = orientacao(c, p, q, a);
        double o4 = orientacao(c, p, q, b);
        return o1 * o2 < 0 && o3 * o4 < 0;
    }

    private static double orientacao(double[] c, int a, int b, int p) {
        return (c[2 * b] - c[2 * a]) * (c[2 * p + 1] - c[2 * a + 1])
                - (c[2 * b + 1] - c[2 * a + 1]) * (c[2 * p] - c[2 * a]);
    }

    private static No criarAnel(double[] c) {
        No primeiro = new No(c[0], c[1]);
        No anterior = primeiro;
        for (int i = 2; i < c.length; i += 2) {
            No novo = new No(c[i], c[i + 1]);
            anterior.ligarProximo(novo);
            anterior = novo;
        }
        anterior.ligarProximo(primeiro); // fecha o loop
        return primeiro;
    }

    /** true se, a partir do primeiro nó, se dá uma volta completa pelos vértices e se volta ao início. */
    public boolean estaFechado() {
        No n = primeiro;
        for (int i = 0; i < numVertices; i++) {
            n = n.getProximo();
            if (n == null) return false;
            if (n == primeiro && i < numVertices - 1) return false; // loop mais curto que o esperado
        }
        return n == primeiro;
    }

    @Override
    public double area() {
        if (!estaFechado()) return 0;
        // Fórmula do cordão (shoelace) percorrendo os nós
        double soma = 0;
        No n = primeiro;
        for (int i = 0; i < numVertices; i++) {
            No p = n.getProximo();
            soma += n.getX() * p.getY() - p.getX() * n.getY();
            n = p;
        }
        return Math.abs(soma) / 2;
    }

    @Override
    public double perimetro() {
        if (!estaFechado()) return 0;
        double soma = 0;
        No n = primeiro;
        for (int i = 0; i < numVertices; i++) {
            soma += n.comprimentoAteProximo();
            n = n.getProximo();
        }
        return soma;
    }

    @Override
    public String toString() {
        return super.toString() + (estaFechado() ? "" : " (incompleto)");
    }
}