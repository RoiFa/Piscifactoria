
package peces;

import com.google.gson.annotations.JsonAdapter;

import adapters.PezAdapter;
import helpers.*;
import propiedades.*;

/** 
 * Clase padre de todos los peces.
 */
@JsonAdapter(PezAdapter.class)
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

    /** @return El nombre común del pez. */
    public String getNombre() {
        return nombre;
    }

    /** @return El nombre científico del pez. */
    public String getNombreCientifico() {
        return nombreCientifico;
    }

    /** @return La edad actual del pez. */
    public int getEdad() {
        return edad;
    }

    /** @return El sexo del pez (Macho o Hembra). */
    public boolean getSexo() {
        return sexo;
    }

    /** @return Si el pez es macho. */
    public boolean isMale() {
        return sexo;
    }

    /** @return Si el pez es fértil. */
    public boolean isFertil() {
        return fertil;
    }

    /** @return Si el pez está vivo. */
    public boolean isVivo() {
        return vivo;
    }

    /** @return Si el pez está alimentado. */
    public boolean isAlimentado() {
        return alimentado;
    }

    /** @return El precio del pez al comprarlo. */
    public int getCoste() {
        return coste;
    }

    /** @return El precio del pez al venderlo. */
    public int getMonedas() {
        return monedas;
    }

    /** @return El número de huevos que pone el pez. */
    public int getHuevos() {
        return huevos;
    }

    /** @return El ciclo de días hasta volver a ser fértil. */
    public int getCiclo() {
        return ciclo;
    }

    /** @return El número de días que tarda el pez en madurar. */
    public int getMadurez() {
        return madurez;
    }

    /** @return El número de días que tarda el pez en alcanzar su edad óptima. */
    public int getOptimo() {
        return optimo;
    }

    /** @return La clase de piscifactoría en la que se puede criar. */
    public CriaTipo getPiscifactoria() {
        return piscifactoria;
    }

    /** @return El tipo de pez (Base, normal, inversión o riesgo) */
    public PecesTipo getTipo() {
        return tipo;
    }

    /** @param edad La edad a cambiar. */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /** @param fertil Si el pez es fértil o no. */
    public void setFertil(boolean fertil) {
        this.fertil = fertil;
    }

    /** @param vivo Si el pez está vivo o no. */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /** @param alimentado Si el pez está alimentado o no. */
    public void setAlimentado(boolean alimentado) {
        this.alimentado = alimentado;
    }

    /** @param sexo El sexo a cambiar. */
    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    /** @param monedas La nueva cantidad de monedas que cuesta al venderlo. */
    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    /** @return Si el pez es adulto o no. */
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
    public int[] grow(int comidaAnimal, int comidaVegetal, boolean enCria) { 
        if (vivo) {
            int[] comido = comer(comidaAnimal, comidaVegetal, enCria);
            if (enCria) {
                if (this.alimentado) {
                    setEdad(edad+1);
                    if (this.edad == this.madurez) {
                        this.fertil = true;
                    }
                }
            } else {
                setEdad(edad+1);
                if (this.edad == this.madurez) {
                    this.fertil = true;
                }
                if ((!this.alimentado && RNG.RandomBoolean()) || (this.edad < this.madurez && this.edad % 2 == 0 && RNG.RandomInt(100) <= 5)) {
                    setVivo(false);
                }
                return comido;
            }
        }
        return new int[] {0, 0};
    }

    /**
     * Método abstracto que indica cuánta comida y de qué tipo consume cada pez.
     * 
     * @param a La comida animal que hay en el almacén
     * @param v La comida vegetal que hay en el almacén
     * @param enCria    Si el pez esta en un tanque de cria.
     * @return  La cantidad de comida que come de cada tipo.
     */
    protected abstract int[] comer(int a, int v, boolean enCria);

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
