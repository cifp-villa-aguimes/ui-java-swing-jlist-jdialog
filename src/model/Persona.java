package model;

public class Persona {
    // Objeto de dominio sencillo para practicar POO con datos reales de la interfaz.
    private final String nombre;
    private final String dni;
    private final String ciclo;
    private final String email;

    public Persona(String nombre, String dni, String ciclo, String email) {
        this.nombre = nombre;
        this.dni = dni;
        this.ciclo = ciclo;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nombre + " - " + ciclo;
    }
}
