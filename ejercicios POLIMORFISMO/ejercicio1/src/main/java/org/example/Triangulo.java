package org.example;

public class Triangulo extends FigurasGeometricas {
    protected int valor2;

    public Triangulo() {}
    public Triangulo(int base, int altura) { super(base); this.valor2 = altura; }

    public int getValor2() { return valor2; }
    public void setValor2(int valor2) { this.valor2 = valor2; }

    @Override
    public double getArea() { return (valor1 * valor2) / 2.0; }

    @Override
    public double getPerimetro() { return valor1 + valor2 + Math.hypot(valor1, valor2); }
}
