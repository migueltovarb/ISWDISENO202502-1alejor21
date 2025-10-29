package org.example;

public class Remolque {
    private int peso;

    public Remolque(int peso) {
        this.peso = peso;
    }

    public int getPeso() { return peso; }

    @Override
    public String toString() {
        return "Remolque{peso=" + peso + "}";
    }
}
