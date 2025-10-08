package examendiagrama;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionVeterinaria gv = new GestionVeterinaria();
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("\n=== Veterinaria ===");
            System.out.println("1) Registrar dueño");
            System.out.println("2) Registrar mascota");
            System.out.println("3) Registrar control veterinario");
            System.out.println("4) Historial de una mascota");
            System.out.println("5) Resumen por dueño");
            System.out.println("6) Listar dueños y mascotas");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine();

            try {
                switch (op) {
                    case "1": {
                        System.out.print("Nombre completo: ");
                        String n = sc.nextLine();
                        System.out.print("Documento: ");
                        String doc = sc.nextLine();
                        System.out.print("Teléfono: ");
                        String tel = sc.nextLine();
                        gv.registrarDueno(new Dueno(n, doc, tel));
                        System.out.println("Dueño registrado.");
                        break;
                    }
                    case "2": { // Registrar mascota
                        System.out.print("Documento del dueño: ");
                        String doc2 = sc.nextLine();

                        // Buscar dueño por documento (debe existir)
                        Dueno d = gv.getDuenos().stream()
                                .filter(x -> x.getDocumento().equalsIgnoreCase(doc2))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Dueño no encontrado"));

                        System.out.print("Nombre de la mascota: ");
                        String nom = sc.nextLine();
                        System.out.print("Especie: ");
                        String esp = sc.nextLine();
                        System.out.print("Edad (años): ");
                        int edad = Integer.parseInt(sc.nextLine());

                        // Crear mascota asociada al dueño encontrado
                        Mascota m = new Mascota(nom, esp, edad, d);

                        // Registrar (este método ya sincroniza dueño<->mascota y evita duplicados)
                        gv.registrarMascota(m, d);

                        System.out.println("Mascota registrada.");
                        break;
                    }
                    case "3": { // Registrar control
                        System.out.print("Documento del dueño: ");
                        String doc3 = sc.nextLine();
                        Dueno d3 = gv.getDuenos().stream()
                                .filter(x -> x.getDocumento().equalsIgnoreCase(doc3))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Dueño no encontrado"));

                        System.out.print("Nombre de la mascota: ");
                        String nom3 = sc.nextLine();
                        Mascota masc = d3.getMascotas().stream()
                                .filter(x -> x.getNombre().equalsIgnoreCase(nom3))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada para ese dueño"));

                        System.out.print("Fecha (YYYY-MM-DD): ");
                        String fecha = sc.nextLine();
                        System.out.print("Descripción del control: ");
                        String desc = sc.nextLine();
                        System.out.print("Costo (>=0): ");
                        double costo = Double.parseDouble(sc.nextLine());

                        gv.registrarControl(new ControlVeterinario(fecha, desc, costo), masc);
                        System.out.println("Control registrado.");
                        break;
                    }
                    case "4": { // Historial
                        System.out.print("Documento del dueño: ");
                        String doc4 = sc.nextLine();
                        Dueno d4 = gv.getDuenos().stream()
                                .filter(x -> x.getDocumento().equalsIgnoreCase(doc4))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Dueño no encontrado"));

                        System.out.print("Nombre de la mascota: ");
                        String nom4 = sc.nextLine();
                        Mascota m4 = d4.getMascotas().stream()
                                .filter(x -> x.getNombre().equalsIgnoreCase(nom4))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

                        List<ControlVeterinario> hs = gv.obtenerHistorial(m4);
                        if (hs.isEmpty()) System.out.println("Sin controles.");
                        else hs.forEach(System.out::println);
                        break;
                    }
                    case "5": { // Resumen por dueño
                        System.out.print("Documento del dueño: ");
                        String doc5 = sc.nextLine();
                        Dueno d5 = gv.getDuenos().stream()
                                .filter(x -> x.getDocumento().equalsIgnoreCase(doc5))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Dueño no encontrado"));

                        System.out.println(gv.obtenerResumenDueno(d5));
                        break;
                    }
                    case "6": { // Listados
                        System.out.println("\nDueños:");
                        gv.getDuenos().forEach(System.out::println);
                        System.out.println("\nMascotas:");
                        gv.getMascotas().forEach(System.out::println);
                        break;
                    }
                    case "0":
                        run = false;
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Chao!");
    }
}