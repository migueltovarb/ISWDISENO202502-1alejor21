package org.example;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Empleado {
    private Coche coche;
    private String movil;
    private String areaVenta;
    private final List<String> clientes = new ArrayList<>();
    private double porcentajeComision;

    public Vendedor(String nombre, String apellidos, String dni, String direccion, String telefono, int antiguedad, double salario, Coche coche, String movil, String areaVenta, double porcentajeComision, Empleado supervisor) {
        super(nombre, apellidos, dni, direccion, telefono, antiguedad, salario, supervisor);
        this.coche = coche;
        this.movil = movil;
        this.areaVenta = areaVenta;
        this.porcentajeComision = porcentajeComision;
    }

    public void altaCliente(String cliente) { if (!clientes.contains(cliente)) clientes.add(cliente); }
    public void bajaCliente(String cliente) { clientes.remove(cliente); }
    public void cambiarCoche(Coche nuevo) { this.coche = nuevo; }

    public List<String> getClientes() { return clientes; }
    public Coche getCoche() { return coche; }
    public String getMovil() { return movil; }
    public String getAreaVenta() { return areaVenta; }
    public double getPorcentajeComision() { return porcentajeComision; }

    @Override
    public void incrementarSalario() { this.salario += this.salario * 0.10; }

    @Override
    public String toString() {
        return "Vendedor{coche=" + coche + ", movil='" + movil + "', areaVenta='" + areaVenta + "', clientes=" + clientes + ", comision=" + porcentajeComision + ", datos=" + super.toString() + "}";
    }
}
