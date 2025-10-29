package org.example;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("Misu");
        Dog dog1 = new Dog("Rex");
        Dog dog2 = new Dog("Kaiser");

        System.out.println(cat);
        System.out.println(dog1);

        cat.greets();
        dog1.greets();
        dog1.greets(dog2);

        Animal[] zoo = { cat, dog1, dog2 };
        for (Animal a : zoo) System.out.println(a);
    }
}
