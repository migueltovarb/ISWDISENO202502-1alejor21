package org.example;

/**
 * Clase que representa un círculo.
 * Es la superclase (padre) de Cylinder.
 */
public class Circle {
    // ======== Atributos ========
    private double radius = 1.0;   // Radio del círculo
    private String color = "red";  // Color por defecto

    // ======== Constructores ========

    // Constructor sin parámetros
    public Circle() {
        // Usa los valores por defecto ya asignados
    }

    // Constructor con un parámetro (radio)
    public Circle(double radius) {
        // 'this' se usa para diferenciar el atributo del parámetro
        this.radius = radius;
    }

    // Constructor con radio y color
    public Circle(double radius, String color) {
        this.radius = radius;  // asigna radio
        this.color = color;    // asigna color
    }

    // ======== Métodos Get y Set ========
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // ======== Método funcional ========
    /**
     * Calcula el área del círculo: π * r²
     */
    public double getArea() {
        return Math.PI * radius * radius;
    }

    // ======== Método toString ========
    /**
     * Devuelve una descripción legible del círculo.
     * Ejemplo: Circle[radius=2.0,color=blue]
     */
    @Override
    public String toString() {
        return "Circle[radius=" + radius + ",color=" + color + "]";
    }
}
