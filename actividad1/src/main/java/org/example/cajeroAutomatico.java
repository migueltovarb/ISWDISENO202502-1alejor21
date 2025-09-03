package org.example;

import java.util.Scanner;

public class cajeroAutomatico {
    public static void main (String[] args){
        final int SALDO_INICIAL = 1000;
        int saldo = SALDO_INICIAL;
        int opcion = 0;

        Scanner pedirDato = new Scanner(System.in);

        while (opcion != 4) {
            System.out.println("bienvenido al cajero automatico porfavor digite alguna de las 4 siguientes opciones:");
            System.out.println("1.consultar saldo");
            System.out.println("2.depositar monto");
            System.out.println("3.retirar monto");
            System.out.println("4.salir");

            opcion = pedirDato.nextInt();

            if (opcion == 1) {
                System.out.println("su saldo es" + saldo);

            } else if (opcion ==2) {
                System.out.println("digite el monto a depositar:");
               int monto = pedirDato.nextInt();

               if (monto > 0) {
                   saldo += monto;

                   System.out.println("Deposito exitoso, su nuevo saldo es" + saldo);
               } else {
                   System.out.println("Porfavor digite un monto adecuado");
               }

            } else if(opcion == 3) {
                System.out.println("Porfavor digite el monto a reitrar");

                int retiro = pedirDato.nextInt();

                if (retiro > 0 && retiro <= saldo) {
                    saldo -= retiro;
                    System.out.println("Retiro exitoso, su saldo restante es: " + saldo );

                } else {
                    System.out.println("porfavor digite un monto valido que no sea mayor a su saldo, su saldo actual es" + saldo);

                }

            } else if (opcion == 4) {
                System.out.println("Gracias por usar el programa de cajero automatico, vuelva pronto ");

            }else {
                System.out.println("opcion invalida");
            }
        }
    }
}
