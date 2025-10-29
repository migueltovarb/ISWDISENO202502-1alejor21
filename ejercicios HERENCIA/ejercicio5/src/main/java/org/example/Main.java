package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Publicacion> catalogo = new ArrayList<>();

        System.out.print("¿Cuántos libros deseas cargar? ");
        int nLibros = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < nLibros; i++) {
            Libro l = new Libro();
            l.leer(sc);
            catalogo.add(l);
        }

        System.out.print("¿Cuántos discos deseas cargar? ");
        int nDiscos = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < nDiscos; i++) {
            Disco d = new Disco();
            d.leer(sc);
            catalogo.add(d);
        }

        System.out.println("\n=== Catálogo ===");
        for (Publicacion p : catalogo) p.mostrar();

        sc.close();
    }
}
