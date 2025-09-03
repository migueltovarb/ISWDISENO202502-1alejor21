import java.util.Scanner;

public class ExamenJava {

    public static final int DIAS_SEMANA = 5;
    public static final int NUM_ESTUDIANTES = 4;
    public static void main(String[] args) {

        Scanner dato = new Scanner(System.in);

        int [] totalAsistenciasEstudiante = new int[NUM_ESTUDIANTES];
        int [] ausenciaPorDia = new int [DIAS_SEMANA];

        int opcion = 0;

        while (opcion != 4) {

            System.out.println("----MENU----");
            System.out.println("1. registrar asistencia");
            System.out.println("2.mirar el total de asistencias");
            System.out.println("3. ver el resumen general de la semana");
            System.out.println("4.salir");
            System.out.println("porfavor escoja una opcion");
            opcion = dato.nextInt();

            switch (opcion) {
                case 1 :
                    for (int i = 0 ; i < NUM_ESTUDIANTES; i++)totalAsistenciasEstudiante[i]= 0;
                    for (int d = 0; d < DIAS_SEMANA; d++)ausenciaPorDia [d] = 0;


                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                        System.out.println("Estudiante " + (i +1) );

                        for (int d = 0; d < DIAS_SEMANA; d++){
                            int valor = 0;
                            do {
                                System.out.println("dia " + (d +1) + "  (1= presente, 0= ausente)");
                                valor = dato.nextInt();
                            }while(valor != 0 && valor != 1);


                            if( valor == 1) {
                                totalAsistenciasEstudiante[i]++;
                            }else {
                                ausenciaPorDia[d]++;
                            }
                        }
                    }
                    System.out.println("registro completado");
                    break;

                case 2:

                    System.out.println("Numero de estudiante (1-" + NUM_ESTUDIANTES + "): ");
                    int id = dato.nextInt()- 1 ;

                    if (id >= 0 && id < NUM_ESTUDIANTES) {
                        System.out.println("Estudiante " + (id + 1) + " asistio " +
                                totalAsistenciasEstudiante[id] + " de " + DIAS_SEMANA + " dias");
                    } else {
                        System.out.println("numero invalido, digite un numero correcto.");
                    }
                    break;

                case 3:
                    int presentes = 0;
                    for (int i = 0; i < NUM_ESTUDIANTES; i++) {
                    System.out.println("Estudiante" + (i+1) + ":" +
                            totalAsistenciasEstudiante[i] + "asistencias");
                    if ( totalAsistenciasEstudiante[i] == DIAS_SEMANA){
                        presentes++;
                    }
                }

                    System.out.println("estudiantes que asistieron todos los dias " + presentes);
                    int mayorAusencias= -1;
                    int mayorDia = -1 ;

                    for ( int d = 0; d < DIAS_SEMANA; d++){
                        if (ausenciaPorDia[d] > mayorAusencias) {
                            mayorAusencias =ausenciaPorDia[d];
                            mayorDia =d;
                        }

                    }

                    System.out.println("dia con mas ausencias " +
                            (mayorDia + 1) + " (" + mayorAusencias + "ausencias" );
                    break;

                case 4:
                    System.out.println("saliendo.....");
                    break;

                default:
                    System.out.println("digite una opcion valida");
                    break;

            }

        }


    }
}
