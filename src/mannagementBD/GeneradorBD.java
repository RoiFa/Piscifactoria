package mannagementBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import helpers.ErrorWriter;
import helpers.RNG;
import main.Simulador;
import propiedades.AlmacenPropiedades;

public class GeneradorBD {

    /**
     * Método encargado de crear las tablas de la base de datos si no existen
     */
    public static void generarTablas(){

        Statement stm = null;

        try{
            stm = Simulador.conn.createStatement();

            stm.execute("CREATE TABLE IF NOT EXISTS cliente ( "+
                        "id INTEGER AUTO_INCREMENT PRIMARY KEY,"+
                        "nombre VARCHAR(10),"+
                        "nif VARCHAR(9) UNIQUE,"+
                        "telefono INTEGER );");

            stm.execute("CREATE TABLE IF NOT EXISTS pez ( "+
                        "id INTEGER AUTO_INCREMENT PRIMARY KEY,"+
                        "nombre VARCHAR(30),"+
                        "cientifico VARCHAR(30) );");

            stm.execute("CREATE TABLE IF NOT EXISTS pedido ( "+
                        "id INTEGER AUTO_INCREMENT PRIMARY KEY,"+
                        "cliente_id INTEGER,"+
                        "pez_id INTEGER,"+
                        "cantidad INTEGER,"+
                        "enviados INTEGER,"+
                        "CONSTRAINT fk_cliente_pedido FOREIGN KEY (cliente_id) REFERENCES cliente (id),"+
                        "CONSTRAINT fk_pez_pedido FOREIGN KEY (pez_id) REFERENCES pez (id) );");

        } catch(SQLException e){
            ErrorWriter.writeInErrorLog("Error en la creación de las tablas");
        } finally{
            if(stm!=null){
                try{
                    stm.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar la sentencia de creación de tablas");
                }
            }
        }
    }

    /**
     * Añade 10 clientes fijos a la base de datos 
     */
    public static void anadirClientes(){

        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            ps = Simulador.conn.prepareStatement("INSERT INTO cliente (nombre,nif,telefono) VALUES (?,?,?)");
            st = Simulador.conn.createStatement();
            rs = st.executeQuery("SELECT count(*) FROM cliente");
            rs.next();
            int filas = rs.getInt(1);
            if(filas<10){
                ArrayList<ClienteDTO> comprobar = new ArrayList<>();
                if(filas>0){
                    rs = st.executeQuery("SELECT nombre, nif, telefono FROM cliente");
                    while(rs.next()){
                        comprobar.add(new ClienteDTO(rs.getString(1),rs.getString(2),rs.getInt(3)));
                    }
                }
                ClienteDTO[] clientes = {new ClienteDTO("María", "54365787N", 684236454),new ClienteDTO("Manuel", "97098423K", 609465431), 
                                        new ClienteDTO("Simón", "65068837L", 650959898),new ClienteDTO("Cristian", "05160467J", 698843243), 
                                        new ClienteDTO("Roi", "76300694P", 643290715),new ClienteDTO("Paula", "53095874V", 603492774),
                                        new ClienteDTO("Javi", "36567096B", 640937481),new ClienteDTO("Lucía", "43780475I", 694728579),
                                        new ClienteDTO("Adrián", "80578932C", 630984923),new ClienteDTO("Marta", "28695865M", 651157435)};
                for(int i = 0;i<clientes.length;i++){
                    if(comprobar.isEmpty() || !comprobar.contains(clientes[i])){
                        ps.setString(1, clientes[i].getNombre());
                        ps.setString(2, clientes[i].getNif());
                        ps.setInt(3, clientes[i].getTelefono());
                        ps.addBatch();
                    }
                }

                ps.executeBatch();
            }
        } catch(SQLException e){
            ErrorWriter.writeInErrorLog("Error al insertar los clientes en la tabla");
        } finally{
            if(rs!=null){
                try{
                    rs.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar resultado al insertar clientes");
                }
            }
            if(st!=null){
                try{
                    st.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar sentencia al insertar clientes");
                }
            }
            if(ps!=null){
                try{
                    ps.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar la sentencia de inserción de clientes");
                }
            }
        }
    }

    /**
     * Inserta los datos de los peces en la base de datos
     */
    public static void insertarPeces(){

        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            ps = Simulador.conn.prepareStatement("INSERT INTO pez (nombre,cientifico) VALUES (?,?)");
            st = Simulador.conn.createStatement();
            rs = st.executeQuery("SELECT count(*) FROM pez");
            rs.next();
            int filas = rs.getInt(1);
            if(filas<12){
                ArrayList<String> comprobar = new ArrayList<>();
                if(filas>0){
                    rs = st.executeQuery("SELECT nombre FROM pez");
                    while (rs.next()) {
                        comprobar.add(rs.getString("nombre"));
                    }
                }
                String[] nomPeces = {AlmacenPropiedades.ABADEJO.getNombre(),AlmacenPropiedades.ARENQUE_ATLANTICO.getNombre(),AlmacenPropiedades.BAGRE_CANAL.getNombre(),
                                    AlmacenPropiedades.BESUGO.getNombre(),AlmacenPropiedades.CARPA.getNombre(),AlmacenPropiedades.COBIA.getNombre(),
                                    AlmacenPropiedades.DORADA.getNombre(),AlmacenPropiedades.KOI.getNombre(),AlmacenPropiedades.PEJERREY.getNombre(),
                                    AlmacenPropiedades.RODABALLO.getNombre(),AlmacenPropiedades.SALMON_CHINOOK.getNombre(),AlmacenPropiedades.TILAPIA_NILO.getNombre()};

                String[] nomCien = {AlmacenPropiedades.ABADEJO.getCientifico(),AlmacenPropiedades.ARENQUE_ATLANTICO.getCientifico(),AlmacenPropiedades.BAGRE_CANAL.getCientifico(),
                                    AlmacenPropiedades.BESUGO.getCientifico(),AlmacenPropiedades.CARPA.getCientifico(),AlmacenPropiedades.COBIA.getCientifico(),
                                    AlmacenPropiedades.DORADA.getCientifico(),AlmacenPropiedades.KOI.getCientifico(),AlmacenPropiedades.PEJERREY.getCientifico(),
                                    AlmacenPropiedades.RODABALLO.getCientifico(),AlmacenPropiedades.SALMON_CHINOOK.getCientifico(),AlmacenPropiedades.TILAPIA_NILO.getCientifico()};


                for(int i = 0;i<nomPeces.length;i++){
                    if(comprobar.isEmpty() || !comprobar.contains(nomPeces[i])){
                        ps.setString(1, nomPeces[i]);
                        ps.setString(2, nomCien[i]);
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
        } catch(SQLException e){
            ErrorWriter.writeInErrorLog("Error al insertar los peces en la tabla");
        } finally{
            if(rs!=null){
                try{
                    rs.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar resultado al insertar peces");
                }
            }
            if(st!=null){
                try{
                    st.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar sentencia al insertar peces");
                }
            }
            if(ps!=null){
                try{
                    ps.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar la sentencia de inserción de peces");
                }
            }
        }
    }

    /**
     * Añade un pedido aleatorio a la base de datos
     */
    public static void anadirPedido(){
        
        Statement st = null;

        try{
            st = Simulador.conn.createStatement();
            st.execute("INSERT INTO pedido (cliente_id,pez_id,cantidad) VALUES ("+(RNG.RandomInt(10)+1)+","+(RNG.RandomInt(12)+1)+","+(RNG.RandomInt(50)+10)+",0)");

        } catch(SQLException e){
            ErrorWriter.writeInErrorLog("Error al insertar un nuevo pedido");
        } finally{
            if(st!=null){
                try{
                    st.close();
                } catch(SQLException sq){
                    ErrorWriter.writeInErrorLog("Error al cerrar la sentencia de insertar pedido");
                }
            }
        }
    }
}