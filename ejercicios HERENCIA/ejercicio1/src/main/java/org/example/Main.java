package org.example;

/**
 * Clase principal para probar Circle y Cylinder.
 */
public class Main {
    public static void main(String[] args) {

        // 1️⃣ Crear un círculo por defecto
        Circle c1 = new Circle();
        System.out.println("=== CÍRCULO 1 ===");
        System.out.println(c1.toString());
        System.out.println("Área: " + c1.getArea());
        System.out.println();

        // 2️⃣ Crear un círculo personalizado
        Circle c2 = new Circle(2.5, "blue");
        System.out.println("=== CÍRCULO 2 ===");
        System.out.println(c2.toString());
        System.out.println("Área: " + c2.getArea());
        System.out.println();

        // 3️⃣ Crear un cilindro por defecto
        Cylinder cy1 = new Cylinder();
        System.out.println("=== CILINDRO 1 ===");
        System.out.println(cy1.toString());
        System.out.println("Área base: " + cy1.getArea());
        System.out.println("Volumen: " + cy1.getVolume());
        System.out.println();

        // 4️⃣ Crear cilindro con radio y altura
        Cylinder cy2 = new Cylinder(3.0, 5.0);
        System.out.println("=== CILINDRO 2 ===");
        System.out.println(cy2.toString());
        System.out.println("Área base: " + cy2.getArea());
        System.out.println("Volumen: " + cy2.getVolume());
        System.out.println();

        // 5️⃣ Crear cilindro con todos los datos
        Cylinder cy3 = new Cylinder(2.0, 10.0, "green");
        System.out.println("=== CILINDRO 3 ===");
        System.out.println(cy3.toString());
        System.out.println("Área base: " + cy3.getArea());
        System.out.println("Volumen: " + cy3.getVolume());
    }
}
