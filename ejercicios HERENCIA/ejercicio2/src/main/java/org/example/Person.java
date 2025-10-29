package org.example;


public class Person {

    // ----------------- Campos privados -----------------
    private String name;     // nombre de la persona
    private String address;  // dirección de la persona

    // ----------------- Constructor -----------------
    /**
     * Crea una persona con nombre y dirección.
     * @param name    Nombre de la persona.
     * @param address Dirección de la persona.
     */
    public Person(String name, String address) {
        // Asignamos los parámetros a los campos del objeto (this.name es el campo; name es el parámetro)
        this.name = name;
        this.address = address;
    }

    // ----------------- Getters -----------------
    /**
     * Devuelve el nombre (lectura controlada del campo privado).
     */
    public String getName() {
        return name; // retornamos el valor actual del campo
    }

    /**
     * Devuelve la dirección.
     */
    public String getAddress() {
        return address;
    }

    // ----------------- Setter -----------------
    /**
     * Cambia la dirección.
     * Se deja solo el setter de address (como en el diagrama).
     * @param address Nueva dirección.
     */
    public void setAddress(String address) {
        // Actualizamos el campo para reflejar el nuevo estado
        this.address = address;
    }

    // ----------------- toString -----------------
    /**
     * Representación en texto siguiendo el formato del diagrama:
     * "Person[name=?,address=?]"
     */
    @Override
    public String toString() {
        // Construimos el string exactamente como pide el enunciado
        return "Person[name=" + name + ",address=" + address + "]";
    }
}
