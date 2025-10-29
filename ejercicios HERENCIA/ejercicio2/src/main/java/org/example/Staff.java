package org.example;


public class Staff extends Person {

    // ----------------- Campos nuevos -----------------
    private String school; // p. ej., "School of Computing"
    private double pay;    // salario

    public Staff(String name, String address, String school, double pay) {
        super(name, address);  // inicializa los campos de Person
        this.school = school;  // inicializa campos propios
        this.pay = pay;
    }

    // ----------------- Getters -----------------
    /** Devuelve la escuela/departamento. */
    public String getSchool() {
        return school;
    }

    /** Devuelve el salario. */
    public double getPay() {
        return pay;
    }

    // ----------------- Setters -----------------
    /** Cambia la escuela/departamento. */
    public void setSchool(String school) {
        this.school = school;
    }

    /** Cambia el salario. */
    public void setPay(double pay) {
        this.pay = pay;
    }

    // ----------------- toString -----------------
    /**
     * Representación en texto como el diagrama:
     * "Staff[Person[name=?,address=?],school=?,pay=?]"
     */
    @Override
    public String toString() {
        return "Staff[Person[name=" + getName() + ",address=" + getAddress() + "],"
                + "school=" + school + ",pay=" + pay + "]";
    }
}
