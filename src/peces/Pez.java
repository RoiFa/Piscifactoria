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
    /** El sexo del pez (True = Macho; False = Hembra) */
    private boolean sexo;
    /** Si el pez es fértil */
    protected boolean fertil;
    /** Si el pez está vivo */
    protected boolean vivo;
    /** Si el pez está alimentado */
    protected boolean alimentado;
    /** El coste del pez al comprarlo */
    private int coste;
    /** El valor del pezvenderlo */
    protected int monedas;
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

    
    /**
     * El constructor básico de un pez genérico.
     * 
     * @param fish La información del pez
     * @param sexo El sexo del pez
     */
    protected Pez(PecesDatos fish, boolean sexo) {
        this.nombre = fish.getNombre();
        this.nombreCientifico = fish.getCientifico();
        this.edad = 0;
        this.sexo = sexo;
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
        this.coste = fish.getCoste();
        this.monedas = fish.getMonedas();
        this.huevos = fish.getHuevos();
        this.ciclo = fish.getCiclo();
        this.madurez = fish.getMadurez();
        this.optimo = fish.getOptimo();
        this.piscifactoria = fish.getPiscifactoria();
        this.tipo = fish.getTipo();
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

    public void setMonedas(int monedas) {
        this.monedas = monedas;
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
     * Método que se encarga de la lógica de hacer crecer al pez.
     * 
     * @param comidaAnimal  La comida animal que hay en el almacén
     * @param comidaVegetal La comida vegetal que hay en el almacén
     * @return La cantidad de comida que come de cada tipo
     */
    public int[] grow(int comidaAnimal, int comidaVegetal) { 
        if (vivo) {
            int[] comido = comer(comidaAnimal, comidaVegetal);
            setEdad(edad+1);
            if (this.edad == this.madurez) {
                this.fertil = true;
            }
            if ((!this.alimentado && RNG.RandomBoolean()) || (this.edad < this.madurez && this.edad % 2 == 0 && RNG.RandomInt(100) <= 5)) {
                setVivo(false);
            }
            return comido;
        }
        return new int[] {0, 0};
    }

    /**
     * Método abstracto que indica cuánta comida y de qué tipo consume cada pez.
     * 
     * @param a  La comida animal que hay en el almacén
     * @param v La comida vegetal que hay en el almacén
     * @return La cantidad de comida que come de cada tipo.
     */
    protected abstract int[] comer(int a, int v);

    /**
     * Método abstracto que devuelve un nuevo pez de su misma clase con sexo aleatorio.
     * 
     * @return  Un nuevo pez de la misma clase.
     */
    public abstract Pez reprod();

    /**
     * Método abstracto que devuelve un nuevo pez de su misma clase con sexo definido.
     * 
     * @param sexo
     * @return
     */
    public abstract Pez reprod(boolean sexo);
    
    /**
     * Reinicia los valores del pez
     */
    public void reset() {
        this.edad = 0;
        this.fertil = false;
        this.vivo = true;
        this.alimentado = false;
    }

    
    /**
     * Devuelve información relevante del pez
     */
    @Override
    public String toString(){
        return (
            "---------- " + this.nombre + " ----------" + "\n" +
            "Nombre científico: " + this.nombreCientifico + "\n" +
            "Tipo: " + this.tipo + "\n" +
            "Coste: " + this.coste + " monedas\n" +
            "Valor: " + this.monedas + " monedas\n" +
            "Huevos: " + this.huevos + " huevos\n" + 
            "Ciclo: " + this.ciclo + " días\n" +
            "Madurez: " + this.madurez + " días\n" +
            "Óptimo: " + this.optimo + " días"
        );
    }
}
