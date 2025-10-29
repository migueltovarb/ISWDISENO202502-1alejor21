package org.example;

public class Main {
    public static void main(String[] args) {
        Coche c = new Coche("ABC-123", 4);
        Camion cam = new Camion("XYZ-999");
        Remolque r = new Remolque(3500);

        System.out.println(c);
        c.acelerar(60);
        System.out.println(c);

        System.out.println(cam);
        cam.acelerar(80);
        System.out.println(cam);

        cam.ponRemolque(r);
        System.out.println(cam);

        cam.acelerar(30);
        System.out.println(cam);

        cam.acelerar(25);
        System.out.println(cam);

        cam.quitaRemolque();
        cam.acelerar(50);
        System.out.println(cam);
    }
}
