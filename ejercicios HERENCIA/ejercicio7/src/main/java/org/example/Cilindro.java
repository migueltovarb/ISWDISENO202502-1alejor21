package org.example;

public class Cilindro extends Circulo {
    protected double altura;

    public Cilindro() {}
    public Cilindro(double radio, double altura) {
        super(radio);
        this.altura = altura;
    }

    public double area() { return 2 * Math.PI * radio * altura + 2 * Math.PI * radio * radio; }
    public double volumen() { return Math.PI * radio * radio * altura; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    @Override
    public String toString() { return "Cilindro{radio=" + radio + ", altura=" + altura + "}"; }
}
