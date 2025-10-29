package org.example;

public abstract class FigurasGeometricas {
    protected int valor1;

    public FigurasGeometricas() {}
    public FigurasGeometricas(int valor1) { this.valor1 = valor1; }

    public int getValor1() { return valor1; }
    public void setValor1(int valor1) { this.valor1 = valor1; }

    public abstract double getArea();
    public abstract double getPerimetro();
}
