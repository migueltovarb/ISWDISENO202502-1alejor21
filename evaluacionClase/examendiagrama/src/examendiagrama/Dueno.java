package examendiagrama;

import java.util.ArrayList;
import java.util.List;

public class Dueno {
    private String nombreCompleto;
    private String documento;
    private String telefono;
    private List<Mascota> mascotas = new ArrayList<>();

    public Dueno() {}

    public Dueno(String nombreCompleto, String documento, String telefono) {
        if (nombreCompleto == null || nombreCompleto.isBlank())
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        if (documento == null || documento.isBlank())
            throw new IllegalArgumentException("El documento es obligatorio");
        if (telefono == null || telefono.isBlank())
            throw new IllegalArgumentException("El teléfono es obligatorio");
        this.nombreCompleto = nombreCompleto.trim();
        this.documento = documento.trim();
        this.telefono = telefono.trim();
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank())
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        this.nombreCompleto = nombreCompleto.trim();
    }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) {
        if (documento == null || documento.isBlank())
            throw new IllegalArgumentException("El documento es obligatorio");
        this.documento = documento.trim();
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isBlank())
            throw new IllegalArgumentException("El teléfono es obligatorio");
        this.telefono = telefono.trim();
    }

    public List<Mascota> getMascotas() { return mascotas; }

    public void agregarMascota(Mascota m) {
        if (m == null) throw new IllegalArgumentException("Mascota requerida");
        if (!mascotas.contains(m)) {
            mascotas.add(m);   
        }
    }

    @Override
    public String toString() {
        return nombreCompleto + " (Doc: " + documento + ", Tel: " + telefono + ", Mascotas: " + mascotas.size() + ")";
    }
}
