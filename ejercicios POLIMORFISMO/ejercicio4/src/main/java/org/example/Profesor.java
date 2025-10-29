package org.example;

public class Profesor extends Empleado {
    private String departamento;

    public Profesor(String nombre, String apellidos, String identificacion, String estadoCivil, int anioIncorporacion, int despacho, String departamento) {
        super(nombre, apellidos, identificacion, estadoCivil, anioIncorporacion, despacho);
        this.departamento = departamento;
    }

    public void cambiarDepartamento(String nuevo) { this.departamento = nuevo; }

    @Override
    public String toString() {
        return "Profesor{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", id='" + identificacion + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", anioIncorporacion=" + anioIncorporacion +
                ", despacho=" + despacho +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}
