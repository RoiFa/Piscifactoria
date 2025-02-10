package dto;

public class ClienteDTO {

    /** El nombre del cliente */
    private String nombre;
    /** El NIF del cliente */
    private String nif;
    /** El teléfono del cliente */
    private int telefono;

    /**
     * Constructor para el DTO de clientes
     * @param nombre el nombre del cliente
     * @param nif el NIF del cliente
     * @param telefono el teléfono del cliente
     */
    public ClienteDTO(String nombre, String nif, int telefono) {
        this.nombre = nombre;
        this.nif = nif;
        this.telefono = telefono;
    }

    /**
     * @return el nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return el NIF del cliente
     */
    public String getNif() {
        return nif;
    }

    /**
     * @return el teléfono del cliente
     */
    public int getTelefono() {
        return telefono;
    }

    
}