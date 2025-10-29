package org.example;

public class Rectangulo extends FigurasGeometricas {
    private int valor2;

    public Rectangulo() {}
    public Rectangulo(int base, int altura) { super(base); this.valor2 = altura; }

    public int getValor2() { return valor2; }
    public void setValor2(int valor2) { this.valor2 = valor2; }

    @Override
    public double getArea() { return valor1 * valor2; }

    @Override
    public double getPerimetro() { return 2.0 * (valor1 + valor2); }
}
