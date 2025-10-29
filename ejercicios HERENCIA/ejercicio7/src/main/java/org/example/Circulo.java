package org.example;

import java.util.Scanner;

public class Circulo {
    protected double radio;

    public Circulo() {}
    public Circulo(double radio) { this.radio = radio; }

    public void leerRadio(Scanner sc) {
        System.out.print("Radio del círculo: ");
        this.radio = Double.parseDouble(sc.nextLine());
    }

    public double area() { return Math.PI * radio * radio; }
    public double circunferencia() { return 2 * Math.PI * radio; }

    public double getRadio() { return radio; }
    public void setRadio(double radio) { this.radio = radio; }

    @Override
    public String toString() { return "Circulo{radio=" + radio + "}"; }
}
