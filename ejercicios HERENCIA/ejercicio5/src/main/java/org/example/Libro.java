package org.example;

import java.util.Scanner;

public class Libro extends Publicacion {
    private int paginas;
    private int anio;

    public Libro() {}

    public Libro(String titulo, float precio, int paginas, int anio) {
        super(titulo, precio);
        this.paginas = paginas;
        this.anio = anio;
    }

    @Override
    public void leer(Scanner sc) {
        System.out.println("=== Cargar Libro ===");
        System.out.print("Título: ");
        this.titulo = sc.nextLine();
        System.out.print("Precio (float): ");
        this.precio = Float.parseFloat(sc.nextLine());
        System.out.print("Páginas (int): ");
        this.paginas = Integer.parseInt(sc.nextLine());
        System.out.print("Año (int): ");
        this.anio = Integer.parseInt(sc.nextLine());
    }

    @Override
    public void mostrar() {
        System.out.println("Libro{titulo='" + titulo + "', precio=" + precio + ", paginas=" + paginas + ", anio=" + anio + "}");
    }
}
