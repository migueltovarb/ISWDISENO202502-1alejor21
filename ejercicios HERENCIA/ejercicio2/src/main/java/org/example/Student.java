package org.example;


public class Student extends Person {

    // ----------------- Campos nuevos de la subclase -----------------
    private String program; // p. ej., "Ingeniería de Software"
    private int year;       // p. ej., 1, 2, 3...
    private double fee;     // costo/matrícula


    public Student(String name, String address, String program, int year, double fee) {
        super(name, address);     // super llama al constructor de Person para inicializar lo heredado
        this.program = program;   // inicializamos los campos específicos de Student
        this.year = year;
        this.fee = fee;
    }

    // ----------------- Getters -----------------
    /** Devuelve el programa. */
    public String getProgram() {
        return program;
    }

    /** Devuelve el año. */
    public int getYear() {
        return year;
    }

    /** Devuelve la matrícula. */
    public double getFee() {
        return fee;
    }

    // ----------------- Setters -----------------
    /** Cambia el programa. */
    public void setProgram(String program) {
        this.program = program;
    }

    /** Cambia el año. */
    public void setYear(int year) {
        this.year = year;
    }

    /** Cambia la matrícula. */
    public void setFee(double fee) {
        this.fee = fee;
    }

    // ----------------- toString -----------------
    /**
     * Representación en texto exactamente como el diagrama:
     * "Student[Person[name=?,address=?],program=?,year=?,fee=?]"
     *
     * Nota: usamos los getters heredados getName() y getAddress()
     * para respetar el encapsulamiento.
     */
    @Override
    public String toString() {
        return "Student[Person[name=" + getName() + ",address=" + getAddress() + "],"
                + "program=" + program + ",year=" + year + ",fee=" + fee + "]";
    }
}
