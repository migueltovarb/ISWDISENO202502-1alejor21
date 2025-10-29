package org.example;

public class Cubo extends Cuadrado {
    public Cubo() {}
    public Cubo(int lado) { super(lado); }

    @Override
    public double getArea() { return 6.0 * valor1 * valor1; }

    @Override
    public double getPerimetro() { return 0.0; }
}
