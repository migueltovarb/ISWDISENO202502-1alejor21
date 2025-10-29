package org.example;

public class Camion extends Vehiculo {
    private Remolque remolque;

    public Camion(String matricula) {
        super(matricula);
        this.remolque = null;
    }

    public void ponRemolque(Remolque r) { this.remolque = r; }
    public void quitaRemolque() { this.remolque = null; }
    public Remolque getRemolque() { return remolque; }

    @Override
    public void acelerar(int kmh) {
        if (remolque != null && velocidad + kmh > 100) {
            System.out.println("El camión con remolque va demasiado rápido");
            return;
        }
        super.acelerar(kmh);
    }

    @Override
    public String toString() {
        String info = "Camion{matricula='" + matricula + "', velocidad=" + velocidad;
        if (remolque != null) info += ", " + remolque.toString();
        info += "}";
        return info;
    }
}
