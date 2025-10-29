package org.example;

public class Main {
    public static void main(String[] args) {
        Estudiante e1 = new Estudiante("Ana", "García", "E-100", "Soltera", "Arquitectura I");
        Profesor p1 = new Profesor("Luis", "Mora", "P-200", "Casado", 2015, 103, "Matemáticas");
        PersonalServicio s1 = new PersonalServicio("María", "Ruiz", "S-300", "Soltera", 2018, 12, "Biblioteca");

        System.out.println("--- Inicial ---");
        System.out.println(e1);
        System.out.println(p1);
        System.out.println(s1);

        e1.cambiarEstadoCivil("Casada");
        e1.matricular("Arquitectura II");
        p1.reasignarDespacho(205);
        p1.cambiarDepartamento("Lenguajes");
        s1.cambiarEstadoCivil("Divorciada");
        s1.reasignarDespacho(20);
        s1.trasladarSeccion("Decanato");

        System.out.println("--- Cambios ---");
        System.out.println(e1);
        System.out.println(p1);
        System.out.println(s1);
    }
}
