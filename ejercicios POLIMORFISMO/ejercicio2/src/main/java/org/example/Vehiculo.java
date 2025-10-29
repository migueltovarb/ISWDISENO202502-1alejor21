package org.example;

public class Vehiculo {
    protected String matricula;
    protected int velocidad;

    public Vehiculo(String matricula) {
        this.matricula = matricula;
        this.velocidad = 0;
    }

    public void acelerar(int kmh) {
        this.velocidad += kmh;
    }

    public String getMatricula() { return matricula; }
    public int getVelocidad() { return velocidad; }

    @Override
    public String toString() {
        return "Vehiculo{matricula='" + matricula + "', velocidad=" + velocidad + "}";
    }
}
