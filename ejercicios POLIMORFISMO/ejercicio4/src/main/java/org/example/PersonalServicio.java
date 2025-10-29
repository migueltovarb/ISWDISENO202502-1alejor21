package org.example;

public class PersonalServicio extends Empleado {
    private String seccion;

    public PersonalServicio(String nombre, String apellidos, String identificacion, String estadoCivil, int anioIncorporacion, int despacho, String seccion) {
        super(nombre, apellidos, identificacion, estadoCivil, anioIncorporacion, despacho);
        this.seccion = seccion;
    }

    public void trasladarSeccion(String nueva) { this.seccion = nueva; }

    @Override
    public String toString() {
        return "PersonalServicio{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", id='" + identificacion + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", anioIncorporacion=" + anioIncorporacion +
                ", despacho=" + despacho +
                ", seccion='" + seccion + '\'' +
                '}';
    }
}
