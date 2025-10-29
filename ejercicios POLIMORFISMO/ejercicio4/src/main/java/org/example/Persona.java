package org.example;

public class Persona {
    protected String nombre;
    protected String apellidos;
    protected String identificacion;
    protected String estadoCivil;

    public Persona(String nombre, String apellidos, String identificacion, String estadoCivil) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.estadoCivil = estadoCivil;
    }

    public void cambiarEstadoCivil(String nuevo) { this.estadoCivil = nuevo; }

    @Override
    public String toString() {
        return "Persona{nombre='" + nombre + "', apellidos='" + apellidos + "', id='" + identificacion + "', estadoCivil='" + estadoCivil + "'}";
    }
}
