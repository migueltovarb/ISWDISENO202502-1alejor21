package org.example;

/**
 * Subclase que hereda de Circle y agrega la propiedad "height".
 */
public class Cylinder extends Circle {
    // ======== Atributo adicional ========
    private double height = 1.0; // altura por defecto

    // ======== Constructores ========

    // Constructor sin parámetros
    public Cylinder() {
        // Llama implícitamente al constructor vacío de Circle
    }

    // Constructor con radio
    public Cylinder(double radius) {
        super(radius); // llama al constructor del padre con radio
    }

    // Constructor con radio y altura
    public Cylinder(double radius, double height) {
        super(radius);     // inicializa radio con el padre
        this.height = height; // asigna altura
    }

    // Constructor con radio, altura y color
    public Cylinder(double radius, double height, String color) {
        super(radius, color); // usa el constructor padre con radio y color
        this.height = height; // agrega altura
    }

    // ======== Métodos Get y Set ========
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // ======== Método funcional ========
    /**
     * Calcula el volumen del cilindro:
     * Volumen = área de la base * altura
     */
    public double getVolume() {
        // getArea() es heredado de Circle
        return getArea() * height;
    }

    // ======== toString sobrescrito ========
    @Override
    public String toString() {
        // super.toString() trae la info del círculo
        return "Cylinder[" + super.toString() + ",height=" + height + "]";
    }
}
