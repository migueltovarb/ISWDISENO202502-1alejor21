package org.example;

public class Secretario extends Empleado {
    private String despacho;
    private String numeroFax;

    public Secretario(String nombre, String apellidos, String dni, String direccion, String telefono, int antiguedad, double salario, String despacho, String numeroFax, Empleado supervisor) {
        super(nombre, apellidos, dni, direccion, telefono, antiguedad, salario, supervisor);
        this.despacho = despacho;
        this.numeroFax = numeroFax;
    }

    public String getDespacho() { return despacho; }
    public String getNumeroFax() { return numeroFax; }

    @Override
    public void incrementarSalario() { this.salario += this.salario * 0.05; }

    @Override
    public String toString() {
        return "Secretario{despacho='" + despacho + "', fax='" + numeroFax + "', datos=" + super.toString() + "}";
    }
}
