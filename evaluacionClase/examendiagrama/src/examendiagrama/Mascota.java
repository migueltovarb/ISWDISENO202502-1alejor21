package examendiagrama;

import java.util.ArrayList;
import java.util.List;

public class Mascota {
    private String nombre;
    private String especie;
    private int edad;
    private Dueno dueno;
    private List<ControlVeterinario> controles = new ArrayList<>();

    public Mascota() {}

    public Mascota(String nombre, String especie, int edad, Dueno dueno) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        if (especie == null || especie.isBlank())
            throw new IllegalArgumentException("La especie es obligatoria");
        if (edad < 0)
            throw new IllegalArgumentException("La edad no puede ser negativa");
        if (dueno == null)
            throw new IllegalArgumentException("La mascota debe tener dueño");
        this.nombre = nombre.trim();
        this.especie = especie.trim();
        this.edad = edad;
        this.dueno = dueno;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        this.nombre = nombre.trim();
    }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) {
        if (especie == null || especie.isBlank())
            throw new IllegalArgumentException("La especie es obligatoria");
        this.especie = especie.trim();
    }

    public int getEdad() { return edad; }
    public void setEdad(int edad) {
        if (edad < 0) throw new IllegalArgumentException("La edad no puede ser negativa");
        this.edad = edad;
    }

    public Dueno getDueno() { return dueno; }
    public void setDueno(Dueno dueno) {
        if (dueno == null) throw new IllegalArgumentException("La mascota debe tener dueño");
        this.dueno = dueno;     // NO tocar la lista del dueño aquí para evitar dobles inserciones
    }

    public List<ControlVeterinario> getControles() { return controles; }
    public void setControles(List<ControlVeterinario> controles) { this.controles = controles; }
    public void agregarControl(ControlVeterinario c) { controles.add(c); }

    @Override
    public String toString() {
        return nombre + " (" + especie + ", " + edad + " años) - Dueño: " + dueno.getNombreCompleto();
    }
}
