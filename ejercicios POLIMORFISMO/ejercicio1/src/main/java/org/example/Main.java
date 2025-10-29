package org.example;

public class Main {
    public static void main(String[] args) {
        Circulo cir = new Circulo(5);
        Cuadrado cua = new Cuadrado(4);
        Rectangulo rec = new Rectangulo(3, 7);
        Triangulo tri = new Triangulo(6, 8);
        Cubo cub = new Cubo(3);

        System.out.println("Circulo area=" + cir.getArea() + " perimetro=" + cir.getPerimetro());
        System.out.println("Cuadrado area=" + cua.getArea() + " perimetro=" + cua.getPerimetro());
        System.out.println("Rectangulo area=" + rec.getArea() + " perimetro=" + rec.getPerimetro());
        System.out.println("Triangulo area=" + tri.getArea() + " perimetro=" + tri.getPerimetro());
        System.out.println("Cubo area=" + cub.getArea());
    }
}
