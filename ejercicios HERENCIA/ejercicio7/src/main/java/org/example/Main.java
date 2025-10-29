package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Circulo c = new Circulo();
        c.leerRadio(sc);

        System.out.print("Altura del cilindro: ");
        double h = Double.parseDouble(sc.nextLine());
        Cilindro cil = new Cilindro(c.getRadio(), h);

        System.out.print("Radio interno del cilindro hueco: ");
        double ri = Double.parseDouble(sc.nextLine());
        CilindroHueco ch = new CilindroHueco(c.getRadio(), ri, h);

        System.out.println("\n=== Resultados ===");
        System.out.println(c + " | Longitud circunferencia = " + c.circunferencia());
        System.out.println("Área círculo = " + c.area());

        System.out.println(cil + " | Área = " + cil.area() + " | Volumen = " + cil.volumen());
        System.out.println(ch + " | Área = " + ch.area() + " | Volumen = " + ch.volumen());

        sc.close();
    }
}
