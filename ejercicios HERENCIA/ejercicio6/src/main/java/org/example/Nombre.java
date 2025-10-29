package org.example;

import java.util.Scanner;

public class Nombre {
    protected String nombre;
    protected String primerApellido;
    protected String segundoApellido;

    public Nombre() {}

    public Nombre(String nombre, String primerApellido, String segundoApellido) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }

    public void Leer_nombre(Scanner sc) {
        System.out.print("Nombre: ");
        this.nombre = sc.nextLine();
        System.out.print("Primer apellido: ");
        this.primerApellido = sc.nextLine();
        System.out.print("Segundo apellido: ");
        this.segundoApellido = sc.nextLine();
    }

    public void mostrar() {
        System.out.println("Nombre completo: " + nombre + " " + primerApellido + " " + segundoApellido);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
}
