package org.example;

import java.util.Scanner;

public class Disco extends Publicacion {
    private float duracionMin;
    private int precio; // precio entero, específico de Disco (sombras el float heredado)

    public Disco() {}

    public Disco(String titulo, int precio, float duracionMin) {
        this.titulo = titulo;
        this.precio = precio;
        this.duracionMin = duracionMin;
    }

    @Override
    public void leer(Scanner sc) {
        System.out.println("=== Cargar Disco ===");
        System.out.print("Título: ");
        this.titulo = sc.nextLine();
        System.out.print("Precio (int): ");
        this.precio = Integer.parseInt(sc.nextLine());
        System.out.print("Duración en minutos (float): ");
        this.duracionMin = Float.parseFloat(sc.nextLine());
    }

    @Override
    public void mostrar() {
        System.out.println("Disco{titulo='" + titulo + "', precio=" + precio + ", duracionMin=" + duracionMin + "}");
    }
}
