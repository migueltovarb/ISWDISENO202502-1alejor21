package org.example;

public class Empleado extends Persona {
    protected int anioIncorporacion;
    protected int despacho;

    public Empleado(String nombre, String apellidos, String identificacion, String estadoCivil, int anioIncorporacion, int despacho) {
        super(nombre, apellidos, identificacion, estadoCivil);
        this.anioIncorporacion = anioIncorporacion;
        this.despacho = despacho;
    }

    public void reasignarDespacho(int nuevo) { this.despacho = nuevo; }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", id='" + identificacion + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", anioIncorporacion=" + anioIncorporacion +
                ", despacho=" + despacho +
                '}';
    }
}
