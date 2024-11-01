package es.guillearana.ejerciciof.model;

import java.util.Objects;

/**
 * Clase que representa a una persona con atributos de nombre, apellidos y edad.
 */
public class Persona {
    /** Nombre de la persona. */
    private String nombre;

    /** Apellidos de la persona. */
    private String apellidos;

    /** Edad de la persona. */
    private int edad;

    /**
     * Constructor que inicializa los atributos de la persona.
     *
     * @param nombre    el nombre de la persona
     * @param apellidos los apellidos de la persona
     * @param edad      la edad de la persona
     */
    public Persona(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return el nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param nombre el nuevo nombre de la persona
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos de la persona.
     *
     * @return los apellidos de la persona
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos de la persona.
     *
     * @param apellidos los nuevos apellidos de la persona
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene la edad de la persona.
     *
     * @return la edad de la persona
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad de la persona.
     *
     * @param edad la nueva edad de la persona
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Devuelve una representación en cadena de la persona.
     *
     * @return una cadena que representa la persona con sus atributos
     */
    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", apellidos=" + apellidos + ", edad=" + edad + "]";
    }

    /**
     * Compara esta persona con otro objeto para verificar si son iguales.
     * Dos personas se consideran iguales si tienen el mismo nombre, apellidos y edad.
     *
     * @param o el objeto a comparar
     * @return true si el objeto es igual a esta persona, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persona persona = (Persona) o;
        return edad == persona.edad &&
                Objects.equals(nombre, persona.nombre) &&
                Objects.equals(apellidos, persona.apellidos);
    }
}
