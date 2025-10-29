package org.example;

public class CilindroHueco extends Cilindro {
    private double radioInterno;

    public CilindroHueco() {}
    public CilindroHueco(double radioExterno, double radioInterno, double altura) {
        super(radioExterno, altura);
        this.radioInterno = radioInterno;
    }

    @Override
    public double area() {
        return 2 * Math.PI * radio * altura + 2 * Math.PI * radioInterno * altura
                + 2 * Math.PI * (radio * radio - radioInterno * radioInterno);
    }

    @Override
    public double volumen() { return Math.PI * (radio * radio - radioInterno * radioInterno) * altura; }

    public double getRadioInterno() { return radioInterno; }
    public void setRadioInterno(double radioInterno) { this.radioInterno = radioInterno; }

    @Override
    public String toString() {
        return "CilindroHueco{radioExterno=" + radio + ", radioInterno=" + radioInterno + ", altura=" + altura + "}";
    }
}
