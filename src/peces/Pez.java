package peces;

import helpers.*;
import propiedades.*;

/** 
 * Clase padre de todos los peces.
 */
public abstract class Pez {

    /** Nombre del pez */
    private String nombre;
    /** Nombre científico del pez */
    private String nombreCientifico;
    /** La edad en días del pez */
    protected int edad;
    /** El sexo deñ pez (True = Macho; False = Hembra) */
    private boolean sexo;
    /** Si el pez es fértil */
    protected boolean fertil;
    /** Si el pez está vivo */
    protected boolean vivo;
    /** Si el pez está alimentado */
    protected boolean alimentado;
    /** El coste del pez al comprarlo */
    private int coste;
    /** El dinero que recibes al vender al pez */
    private int monedas;
    /** La cantidad de huevos que pone el pez */
    private int huevos;
    /** El número de días hasta que vuelva a ser fértil */
    private int ciclo;
    /** El número de días que tarda en ser fértil por primera vez */
    private int madurez;
    /** La edad óptima para vender al pez. */
    private int optimo;
    /** La clase de piscifactoría en la que puede criar este pez (Mar, Rio o ambos) */
    private CriaTipo piscifactoria;
    /** El tipo de pez (Base, Normal, Inversion o Riesgo) */
    private PecesTipo tipo;
    /** Las propiedades del pez */
    private PecesProps[] propiedades;

    
    /**
     * El constructor básico de un pez genérico de sexo fijo.
     * 
     * @param nombre    El nombre común del pez
     * @param nombreCientifico  El nombre científico del pez
     * @param sexo  El sexo del pez
     * @param coste El coste del pez
     * @param monedas   Las monedas que vale el pez
     * @param huevos    La cantidad de huevos que pone el pez
     * @param ciclo El número de días hasta qe vuelva a ser fértil
     * @param madurez   El número de días que tarda en ser fértil por primera vez
     * @param optimo    La edad óptima para vender al pez
     */
    public Pez(String nombre, String nombreCientifico, boolean sexo, int coste, int monedas, int huevos, int ciclo, int madurez, int optimo, CriaTipo piscifactoria, PecesTipo tipo, PecesProps[] propiedades) {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.edad = 0;
        this.sexo = sexo;
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
        this.coste = coste;
        this.monedas = monedas;
        this.ciclo = ciclo;
        this.madurez = madurez;
        this.optimo = optimo;
        this.piscifactoria = piscifactoria;
        this.tipo = tipo;
        this.propiedades = propiedades;
        
    }

    /**
     * El constructor básico de un pez genérico de sexo aleatorio.
     * 
     * @param nombre    El nombre común del pez
     * @param nombreCientifico  El nombre científico del pez
     * @param coste El coste del pez
     * @param monedas   Las monedas que vale el pez
     * @param huevos    La cantidad de huevos que pone el pez
     * @param ciclo El número de días hasta qe vuelva a ser fértil
     * @param madurez   El número de días que tarda en ser fértil por primera vez
     * @param optimo    La edad óptima para vender al pez
     */
    public Pez(String nombre, String nombreCientifico, int coste, int monedas, int huevos, int ciclo, int madurez, int optimo, CriaTipo piscifactoria, PecesTipo tipo, PecesProps[] propiedades) {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.edad = 0;
        this.sexo = RNG.RandomBoolean();
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
        this.coste = coste;
        this.monedas = monedas;
        this.ciclo = ciclo;
        this.madurez = madurez;
        this.optimo = optimo;
        this.piscifactoria = piscifactoria;
        this.tipo = tipo;
        this.propiedades = propiedades;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo ? "Macho" : "Hembra";
    }

    public boolean isMale() {
        return sexo;
    }

    public boolean isFertil() {
        return fertil;
    }

    public boolean isVivo() {
        return vivo;
    }

    public boolean isAlimentado() {
        return alimentado;
    }

    public int getCoste() {
        return coste;
    }

    public int getMonedas() {
        return monedas;
    }

    public int getHuevos() {
        return huevos;
    }

    public int getCiclo() {
        return ciclo;
    }

    public int getMadurez() {
        return madurez;
    }

    public int getOptimo() {
        return optimo;
    }

    public CriaTipo getPiscifactoria() {
        return piscifactoria;
    }

    public PecesTipo getTipo() {
        return tipo;
    }

    public PecesProps[] getPropiedades() {
        return propiedades;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFertil(boolean fertil) {
        this.fertil = fertil;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public void setAlimentado(boolean alimentado) {
        this.alimentado = alimentado;
    }

    /**
     * Método que devuelve un boolean dependiendo de si el pez es adulto o no
     */
    public boolean isAdulto() {
        return (this.edad >= this.madurez);
    }

    /**
     * Método que muestra el estado actual del pez mostrando información como nombre, edad, sexo, etc.
     */
    public void showStatus() {
        System.out.println(
            "---------- " + this.nombre + " ----------" + "\n" +
            "Edad: " + this.edad + " días.\n" +
            "Sexo: " + (this.sexo ? "M" : "F") + "\n" +
            "Vivo: " + (this.vivo ? "Si" : "No") + "\n" +
            "Alimentado: " + (this.alimentado ? "si" : "No") + "\n" +
            "Adulto: " + (this.isAdulto() ? "Si" : "No") + "\n" + 
            "Fértil: " + (this.fertil ? "Si" : "No")
        );
    }

    /**
     * Método que se encarga de toda la lógica de hacer crecer al pez.
     */
    public void grow() {
        if (vivo) {
            setEdad(edad++);
            if (!this.alimentado && RNG.RandomBoolean()) {
                setVivo(false);
            }
            if (this.edad >= this.madurez) {
                //TODO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            }
        }
    }

    /**
     * Mérodo que se encarga de reestableces todos los valores modificados a su estado original.
     */
    public void reset() {
        this.edad = 0;
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
    }

}
