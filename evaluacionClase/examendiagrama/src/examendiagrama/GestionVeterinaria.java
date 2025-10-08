package examendiagrama;

import java.util.ArrayList;
import java.util.List;

public class GestionVeterinaria {
    private List<Dueno> duenos = new ArrayList<>();
    private List<Mascota> mascotas = new ArrayList<>();
    private List<ControlVeterinario> controles = new ArrayList<>();

    public GestionVeterinaria() {}

    public void registrarDueno(Dueno d) {
        if (d == null) throw new IllegalArgumentException("Dueño requerido");
        boolean existe = duenos.stream().anyMatch(x -> x.getDocumento().equalsIgnoreCase(d.getDocumento()));
        if (existe) throw new IllegalArgumentException("Ya existe un dueño con documento " + d.getDocumento());
        duenos.add(d);
    }

    public void registrarMascota(Mascota m, Dueno d) {
        if (m == null || d == null) throw new IllegalArgumentException("Mascota y dueño requeridos");

        // Valida duplicado solo aquí (por nombre para el mismo dueño)
        String nombreNuevo = m.getNombre().trim();
        boolean duplicada = d.getMascotas().stream()
                .anyMatch(x -> x.getNombre().trim().equalsIgnoreCase(nombreNuevo));
        if (duplicada) {
            throw new IllegalArgumentException(
                "El dueño ya tiene una mascota llamada '" + m.getNombre() + "'");
        }

        // Enlaza y registra en un solo lugar
        m.setDueno(d);          // solo asigna dueño, sin tocar la lista del dueño
        d.agregarMascota(m);    // solo añade si no está
        if (!mascotas.contains(m)) mascotas.add(m);
        if (!duenos.contains(d)) duenos.add(d);
    }


    public void registrarControl(ControlVeterinario c, Mascota m) {
        if (m == null) throw new IllegalArgumentException("La mascota no puede ser nula");
        if (!mascotas.contains(m)) throw new IllegalArgumentException("No se puede registrar control: la mascota no existe en el sistema");
        if (c == null) throw new IllegalArgumentException("Control requerido");
        m.agregarControl(c);
        controles.add(c);
    }

    public List<ControlVeterinario> obtenerHistorial(Mascota m) {
        if (m == null) throw new IllegalArgumentException("Mascota requerida");
        if (!mascotas.contains(m)) throw new IllegalArgumentException("Mascota no existe en el sistema");
        return m.getControles();
    }

    public String obtenerResumenDueno(Dueno d) {
        if (d == null) throw new IllegalArgumentException("Dueño requerido");
        int totalMascotas = d.getMascotas().size();
        int totalControles = d.getMascotas().stream().mapToInt(x -> x.getControles().size()).sum();
        return "Dueño: " + d.getNombreCompleto() + " | Mascotas: " + totalMascotas + " | Controles totales: " + totalControles;
    }

    // utilidades opcionales para listar
    public List<Dueno> getDuenos() { return duenos; }
    public List<Mascota> getMascotas() { return mascotas; }
    public List<ControlVeterinario> getControles() { return controles; }
}
