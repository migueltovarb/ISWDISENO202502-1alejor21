package org.example;

public class Estudiante extends Persona {
    private String curso;

    public Estudiante(String nombre, String apellidos, String identificacion, String estadoCivil, String curso) {
        super(nombre, apellidos, identificacion, estadoCivil);
        this.curso = curso;
    }

    public void matricular(String nuevoCurso) { this.curso = nuevoCurso; }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", id='" + identificacion + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
}
