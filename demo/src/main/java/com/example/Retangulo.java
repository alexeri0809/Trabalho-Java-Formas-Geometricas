package com.example;

/** Forma de 4 nós com 4 ângulos retos. */
public class Retangulo extends Poligono {

    public Retangulo(double x, double y, double largura, double altura) {
        super("Retângulo", coordenadasValidas(x, y, largura, altura));
    }

    public Retangulo(No a, No b, No c, No d) {
        super("Retângulo", validarRetangulo(a, b, c, d));
    }

    private static double[] coordenadasValidas(double x, double y, double largura, double altura) {
        if (!(largura > 0) || !(altura > 0)) {
            throw new IllegalArgumentException("Largura e altura têm de ser positivas");
        }
        return new double[] {
            x, y,
            x + largura, y,
            x + largura, y + altura,
            x, y + altura
        };
    }

    private static No[] validarRetangulo(No a, No b, No c, No d) {
        No[] nos = {a, b, c, d};
        if (!formaRetangulo(nos)) {
            throw new IllegalArgumentException("Os 4 nós não formam um retângulo");
        }
        return nos;
    }

    /** true se os 4 nós, por esta ordem, formam um retângulo (4 ângulos retos). */
    static boolean formaRetangulo(No[] nos) {
        if (nos == null || nos.length != 4) return false;
        for (No n : nos) {
            if (n == null) return false;
        }
        for (int i = 0; i < 4; i++) {
            No p = nos[i];
            No q = nos[(i + 1) % 4];
            No r = nos[(i + 2) % 4];
            double v1x = q.getX() - p.getX();
            double v1y = q.getY() - p.getY();
            double v2x = r.getX() - q.getX();
            double v2y = r.getY() - q.getY();
            double produto = v1x * v2x + v1y * v2y;
            double escala = Math.hypot(v1x, v1y) * Math.hypot(v2x, v2y);
            if (escala < 1e-12 || Math.abs(produto) > 1e-9 * escala) return false;
        }
        return true;
    }
}