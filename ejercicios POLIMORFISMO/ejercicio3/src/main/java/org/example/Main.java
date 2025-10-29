package org.example;

public class Main {
    public static void main(String[] args) {
        Coche c1 = new Coche("AAA-111", "Toyota", "Yaris");
        Coche c2 = new Coche("BBB-222", "Kia", "Rio");
        Coche c3 = new Coche("CCC-333", "Mazda", "3");

        Secretario sec = new Secretario("Luisa", "Pardo", "11A", "Calle 1", "3001112222", 3, 2800000, "D-101", "555-1000", null);
        JefeDeZona jefe = new JefeDeZona("Carlos", "Mora", "22B", "Cra 5", "3003334444", 8, 7000000, "J-201", sec, c1);
        Vendedor v1 = new Vendedor("Ana", "Ruiz", "33C", "Av 10", "3005556666", 2, 3500000, c2, "3210000000", "Norte", 0.03, jefe);
        Vendedor v2 = new Vendedor("Pedro", "Ramos", "44D", "Av 20", "3007778888", 1, 3000000, c3, "3221111111", "Sur", 0.025, jefe);

        jefe.altaVendedor(v1);
        jefe.altaVendedor(v2);
        v1.altaCliente("Cliente Uno");
        v1.altaCliente("Cliente Dos");
        v2.altaCliente("Cliente Tres");

        System.out.println("--- Estado inicial ---");
        System.out.println(sec);
        System.out.println(jefe);
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("--- Incrementos anuales ---");
        sec.incrementarSalario();
        v1.incrementarSalario();
        jefe.incrementarSalario();
        System.out.println(sec);
        System.out.println(jefe);
        System.out.println(v1);

        System.out.println("--- Cambios operativos ---");
        v2.cambiarCoche(new Coche("DDD-444", "Renault", "Logan"));
        jefe.cambiarSecretario(null);
        jefe.cambiarCoche(new Coche("EEE-555", "Hyundai", "HB20"));
        v2.bajaCliente("Cliente Tres");
        System.out.println(jefe);
        System.out.println(v2);
    }
}
