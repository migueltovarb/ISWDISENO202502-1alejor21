package org.example;

public class Main {
    public static void main(String[] args) {
        Shape sh = new Shape();
        Circle c1 = new Circle();
        Circle c2 = new Circle(2.5, "blue", false);

        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle(3.0, 4.0, "green", true);

        Square sq1 = new Square();
        Square sq2 = new Square(5.0, "yellow", true);

        System.out.println(sh);
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(sq1);
        System.out.println(sq2);

        sq2.setWidth(7.0);
        System.out.println("Square after setWidth(7.0): " + sq2);

        sq2.setLength(4.0);
        System.out.println("Square after setLength(4.0): " + sq2);

        sq2.setSide(6.0);
        System.out.println("Square after setSide(6.0): " + sq2);

        Shape[] shapes = { sh, c2, r2, sq2 };
        for (Shape s : shapes) System.out.println("poly -> " + s);
    }
}
