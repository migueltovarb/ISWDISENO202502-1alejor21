package examendiagrama;

public class ControlVeterinario {
    private String fecha;        // formateada YYYY-MM-DD (string simple)
    private String descripcion;  // tipo / detalle del control
    private double costo;        // > 0

    public ControlVeterinario() {}

    public ControlVeterinario(String fecha, String descripcion, double costo) {
        if (fecha == null || fecha.isBlank())
            throw new IllegalArgumentException("La fecha es obligatoria");
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria");
        if (costo < 0) throw new IllegalArgumentException("El costo no puede ser negativo");
        this.fecha = fecha.trim();
        this.descripcion = descripcion.trim();
        this.costo = costo;
    }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) {
        if (fecha == null || fecha.isBlank())
            throw new IllegalArgumentException("La fecha es obligatoria");
        this.fecha = fecha.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria");
        this.descripcion = descripcion.trim();
    }

    public double getCosto() { return costo; }
    public void setCosto(double costo) {
        if (costo < 0) throw new IllegalArgumentException("El costo no puede ser negativo");
        this.costo = costo;
    }

    @Override
    public String toString() {
        return fecha + " | " + descripcion + " | $" + costo;
    }
}
