package mannagementBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

import helpers.ErrorWriter;

public class Conexion{

    /** Las propiedades de conexión */
    private static Properties prop;
    /** La conexión a la base de datos */
    private static Connection conn;

    /** El servidor al que conectarse */
    private static final String SERVER = "jjgomez.iescotarelo.es";
    /** El puerto al que conectarse */
    private static final String PORT_NUMBER = "3306";
    /** La base de datos a la que conectarse */
    private static final String DATABASE = "piscifactoria";
    /** El usuario */
    private static final String USERNAME = "admin_pisci";
    /** La contraseña */
    private static final String PASSWORD = "abc123.";

    /** 
     * Conecta con la base de datos
     * @return La conexión con la base de datos
     */
    public static Connection getConect(){

        if (conn==null) {
            try{
                prop = new Properties();
                prop.put("user", USERNAME);
                prop.put("password", PASSWORD);
                prop.put("rewriteBatchedStatements", true);
                conn = DriverManager.getConnection("jdbc:mysql://"+SERVER+":"+PORT_NUMBER+"/"+DATABASE, prop);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeCon(){
        if(conn!=null){
            try{
                conn.close();
                conn = null;
            } catch(SQLException e){
                ErrorWriter.writeInErrorLog("Error al cerrar la conexión a la base de datos");
            }
        }
    }
     
}
