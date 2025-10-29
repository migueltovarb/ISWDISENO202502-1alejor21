package org.example;

import java.util.Scanner;

public class Publicacion {
    protected String titulo;
    protected float precio;

    public Publicacion() {}

    public Publicacion(String titulo, float precio) {
        this.titulo = titulo;
        this.precio = precio;
    }

    public void leer(Scanner sc) {
        System.out.print("Título: ");
        this.titulo = sc.nextLine();
        System.out.print("Precio (float): ");
        this.precio = Float.parseFloat(sc.nextLine());
    }

    public void mostrar() {
        System.out.println("Publicación{titulo='" + titulo + "', precio=" + precio + "}");
    }
}
