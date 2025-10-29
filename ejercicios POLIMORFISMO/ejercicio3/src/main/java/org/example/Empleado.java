package org.example;

public class Empleado {
    protected String nombre;
    protected String apellidos;
    protected String dni;
    protected String direccion;
    protected String telefono;
    protected int antiguedad;
    protected double salario;
    protected Empleado supervisor;

    public Empleado(String nombre, String apellidos, String dni, String direccion, String telefono, int antiguedad, double salario) {
        this(nombre, apellidos, dni, direccion, telefono, antiguedad, salario, null);
    }

    public Empleado(String nombre, String apellidos, String dni, String direccion, String telefono, int antiguedad, double salario, Empleado supervisor) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.antiguedad = antiguedad;
        this.salario = salario;
        this.supervisor = supervisor;
    }

    public void cambiarSupervisor(Empleado nuevo) { this.supervisor = nuevo; }
    public void incrementarSalario() { }

    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDni() { return dni; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public int getAntiguedad() { return antiguedad; }
    public double getSalario() { return salario; }
    public Empleado getSupervisor() { return supervisor; }

    @Override
    public String toString() {
        String sup = supervisor == null ? "sin supervisor" : supervisor.nombre + " " + supervisor.apellidos + " (" + supervisor.dni + ")";
        return "Empleado{nombre='" + nombre + "', apellidos='" + apellidos + "', dni='" + dni + "', direccion='" + direccion + "', telefono='" + telefono + "', antiguedad=" + antiguedad + ", salario=" + String.format("%.2f", salario) + ", supervisor=" + sup + "}";
    }
}
