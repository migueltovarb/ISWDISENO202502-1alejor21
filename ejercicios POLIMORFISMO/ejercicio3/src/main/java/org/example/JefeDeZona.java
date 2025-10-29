package org.example;

import java.util.ArrayList;
import java.util.List;

public class JefeDeZona extends Empleado {
    private String despacho;
    private Secretario secretario;
    private final List<Vendedor> vendedores = new ArrayList<>();
    private Coche coche;

    public JefeDeZona(String nombre, String apellidos, String dni, String direccion, String telefono, int antiguedad, double salario, String despacho, Secretario secretario, Coche coche) {
        super(nombre, apellidos, dni, direccion, telefono, antiguedad, salario);
        this.despacho = despacho;
        this.secretario = secretario;
        this.coche = coche;
    }

    public void cambiarSecretario(Secretario nuevo) { this.secretario = nuevo; }
    public void cambiarCoche(Coche nuevo) { this.coche = nuevo; }
    public void altaVendedor(Vendedor v) { if (!vendedores.contains(v)) vendedores.add(v); }
    public void bajaVendedor(Vendedor v) { vendedores.remove(v); }

    public String getDespacho() { return despacho; }
    public Secretario getSecretario() { return secretario; }
    public List<Vendedor> getVendedores() { return vendedores; }
    public Coche getCoche() { return coche; }

    @Override
    public void incrementarSalario() { this.salario += this.salario * 0.20; }

    @Override
    public String toString() {
        return "JefeDeZona{despacho='" + despacho + "', secretario=" + (secretario == null ? "null" : secretario.getNombre() + " " + secretario.getApellidos()) + ", vendedores=" + vendedores.size() + ", coche=" + coche + ", datos=" + super.toString() + "}";
    }
}
