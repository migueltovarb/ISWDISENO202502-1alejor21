package org.example;

import java.util.Scanner;

public class Direccion extends Nombre {
    private String calle;
    private String ciudad;
    private String provincia;
    private String codigoPostal;

    public Direccion() {}

    public Direccion(String nombre, String primerApellido, String segundoApellido,
                     String calle, String ciudad, String provincia, String codigoPostal) {
        super(nombre, primerApellido, segundoApellido);
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }

    public void nueva_direccion(Scanner sc) {
        System.out.print("Calle: ");
        this.calle = sc.nextLine();
        System.out.print("Ciudad: ");
        this.ciudad = sc.nextLine();
        System.out.print("Provincia: ");
        this.provincia = sc.nextLine();
        System.out.print("Código postal: ");
        this.codigoPostal = sc.nextLine();
    }

    public void nuevo_nombre(Scanner sc) {
        super.Leer_nombre(sc);
    }

    @Override
    public void mostrar() {
        System.out.println("Nombre completo: " + nombre + " " + primerApellido + " " + segundoApellido);
        System.out.println("Dirección: " + calle + ", " + ciudad + ", " + provincia + ", " + codigoPostal);
    }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
}
