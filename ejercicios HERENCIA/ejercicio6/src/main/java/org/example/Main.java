package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Direccion d = new Direccion();
        System.out.println("=== Cargar nombre ===");
        d.nuevo_nombre(sc);
        System.out.println("=== Cargar dirección ===");
        d.nueva_direccion(sc);

        System.out.println("\n=== Resultado ===");
        d.mostrar();

        sc.close();
    }
}
