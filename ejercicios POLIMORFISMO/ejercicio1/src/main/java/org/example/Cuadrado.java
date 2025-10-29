package org.example;

public class Cuadrado extends FigurasGeometricas {
    public Cuadrado() {}
    public Cuadrado(int lado) { super(lado); }

    @Override
    public double getArea() { return valor1 * valor1; }

    @Override
    public double getPerimetro() { return 4.0 * valor1; }
}
