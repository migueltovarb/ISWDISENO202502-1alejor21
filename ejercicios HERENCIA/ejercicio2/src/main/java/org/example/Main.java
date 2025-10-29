package org.example;


public class Main {


    public static void main(String[] args) {

        // 1) Creamos una Person (superclase)
        Person p = new Person("Ana Pérez", "Calle 1 #2-3");
        System.out.println(p.toString()); // Imprime: Person[name=Ana Pérez,address=Calle 1 #2-3]

        // 2) Creamos un Student (subclase) -> hereda name/address y añade program/year/fee
        Student s = new Student("Luis Gómez", "Cra 10 #20-30", "Ingeniería de Software", 2, 3500000.0);
        System.out.println(s); // toString de Student incluye los datos heredados y los propios

        // 3) Cambiamos algunos valores con setters para ver el encapsulamiento en acción
        s.setYear(3);                   // ahora es de 3er año
        s.setFee(3600000.0);            // nueva matrícula
        s.setProgram("Computación");    // cambio de programa
        s.setAddress("Av. Central 123"); // setAddress viene de la superclase Person
        System.out.println("Actualizado: " + s);

        // 4) Creamos un Staff (subclase) -> hereda name/address y añade school/pay
        Staff t = new Staff("María Ruiz", "Diagonal 5 #40-50", "School of Computing", 7800000.0);
        System.out.println(t);

        // 5) Modificamos campos del Staff con setters
        t.setSchool("Facultad de Ingeniería");
        t.setPay(8000000.0);
        t.setAddress("Calle Nueva 77"); // método heredado de Person
        System.out.println("Actualizado: " + t);

        // 6) Polimorfismo básico: un arreglo de la superclase puede guardar subclases
        Person[] people = new Person[] { p, s, t };
        for (Person person : people) {
            // Llama al toString adecuado según el TIPO REAL del objeto (dinámico)
            System.out.println("Polimorfismo -> " + person);
        }
    }
}
