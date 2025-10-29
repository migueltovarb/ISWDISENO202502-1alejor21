package org.example;

public class Coche extends Vehiculo {
    private int puertas;

    public Coche(String matricula, int puertas) {
        super(matricula);
        this.puertas = puertas;
    }

    public int getPuertas() { return puertas; }

    @Override
    public String toString() {
        return "Coche{matricula='" + matricula + "', velocidad=" + velocidad + ", puertas=" + puertas + "}";
    }
}
