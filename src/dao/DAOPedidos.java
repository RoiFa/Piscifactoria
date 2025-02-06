package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import dnl.utils.text.table.TextTable;
import helpers.ErrorWriter;
import helpers.Reader;

/** Clase DAO para la preparación de sentencias SQL */
public class DAOPedidos {

    /** Sentencia preparada que devuelve toda la información de todos los clientes. */
    private static PreparedStatement allFromClientes;
    /** Sentencia preparada que devuelve toda la información de un cliente en específico. */
    private static PreparedStatement allFromSpecificCliente;
    /** Sentencia preparada que devuelve toda la información de todos los peces. */
    private static PreparedStatement allFromPeces;
    /** Sentencia preparada que devuelve toda la información de un pez en específico. */
    private static PreparedStatement allFromSpecificPez;
    /** Sentencia preparada que devuelve toda la información de todos los pedidos. */
    private static PreparedStatement allFromPedidos;
    /** Sentencia preparada que devuelve toda la inforamción de un pedido en específico. */
    private static PreparedStatement allFromSpecificPedido;
    /** Sentencia preparada que devuelve toda la información de todos los pedidos hecho por un cliente en específico. */
    private static PreparedStatement allFromPedidosFromSpecificCliente;
    /** Sentencia preparada que devuelve toda la información de todos los pedidos donde se pida un pez en específico. */
    private static PreparedStatement allFromPedidosFromSpecificPez;

    /**
     * Prepara todas las sentencias con comodines para facilitar su uso.
     * 
     * @param conn  La conexión con la que crear las sentencias.
     */
    public static void prepareStatements(Connection conn) {
        try {
            allFromClientes = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre', nif AS 'NIF', telefono AS 'Teléfono' FROM cliente ORDER BY ? DESC");
            allFromSpecificCliente = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre', nif AS 'NIF', telefono AS 'Teléfono' FROM cliente WHERE id = ? ORDER BY ? DESC");
            allFromPeces = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre común', cientifico AS 'Nombre científico' FROM pez");
            allFromSpecificPez = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre común', cientifico AS 'Nombre científico' FROM pez WHERE id = ? ORDER BY ? DESC");
            allFromPedidos = conn.prepareStatement(
                "SELECT " +
                    "pedido.id AS 'ID', " +
                    "pedido.cliente_id AS 'ID del cliente', " +
                    "cliente.nombre AS 'Nombre del cliente', " +
                    "pedido.pez_id AS 'ID del pez', " +
                    "pez.nombre AS 'Tipo de pez', " +
                    "pedido.cantidad AS 'Cantidad pedida', " +
                    "pedido.enviados AS 'Cantidad entregada' " +
                "FROM pedido " +
                "JOIN cliente ON pedido.cliente_id = cliente.id " +
                "JOIN pez ON pedido.pez_id = pez.id " +
                "ORDER BY ? DESC"
            );
            allFromSpecificPedido = conn.prepareStatement(
                "SELECT " +
                    "pedido.id AS 'ID', " +
                    "pedido.cliente_id AS 'ID del cliente', " +
                    "cliente.nombre AS 'Nombre del cliente', " +
                    "pedido.pez_id AS 'ID del pez', " +
                    "pez.nombre AS 'Tipo de pez', " +
                    "pedido.cantidad AS 'Cantidad pedida', " +
                    "pedido.enviados AS 'Cantidad entregada' " +
                "FROM pedido " +
                "JOIN cliente ON pedido.cliente_id = cliente.id " +
                "JOIN pez ON pedido.pez_id = pez.id " +
                "WHERE pedido.id = ? " +
                "ORDER BY ? DESC"
            );
            allFromPedidosFromSpecificCliente = conn.prepareStatement(
                "SELECT " +
                    "pedido.id AS 'ID', " +
                    "pedido.cliente_id AS 'ID del cliente', " +
                    "cliente.nombre AS 'Nombre del cliente', " +
                    "pedido.pez_id AS 'ID del pez', " +
                    "pez.nombre AS 'Tipo de pez', " +
                    "pedido.cantidad AS 'Cantidad pedida', " +
                    "pedido.enviados AS 'Cantidad entregada' " +
                "FROM pedido " +
                "JOIN cliente ON pedido.cliente_id = cliente.id " +
                "JOIN pez ON pedido.pez_id = pez.id " +
                "WHERE cliente.id = ? " +
                "ORDER BY ? DESC"
            );
            allFromPedidosFromSpecificPez = conn.prepareStatement(
                "SELECT " +
                    "pedido.id AS 'ID', " +
                    "pedido.cliente_id AS 'ID del cliente', " +
                    "cliente.nombre AS 'Nombre del cliente', " +
                    "pedido.pez_id AS 'ID del pez', " +
                    "pez.nombre AS 'Tipo de pez', " +
                    "pedido.cantidad AS 'Cantidad pedida', " +
                    "pedido.enviados AS 'Cantidad entregada' " +
                "FROM pedido " +
                "JOIN cliente ON pedido.cliente_id = cliente.id " +
                "JOIN pez ON pedido.pez_id = pez.id " +
                "WHERE pez.id = ? " +
                "ORDER BY ? DESC"
            );

        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al preparar las sentencias SQL.");
        }
    }
    
    /**
     * Devuelve toda la información de todos los clientes.
     * 
     * @return  La información de todos los clientes.
     */
    public void getAllInfoFromClients() {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "Nombre", "NIF", "Teléfono"});
        if (orderBy != 0) {
            try {
                allFromClientes.setInt(1, orderBy);
                result = allFromClientes.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Devuelve toda la información de un cliente en específico.
     * 
     * @param clientID  El ID del cliente.
     * @return  La información del cliente.
     */
    public void getAllInfoFromClient(int clientID) {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "Nombre", "NIF", "Teléfono"});
        if (orderBy != 0) {
            try {
                allFromSpecificCliente.setInt(1, clientID);
                allFromSpecificCliente.setInt(2, orderBy);
                result = allFromSpecificCliente.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Devuelve toda la información de todos los peces.
     * 
     * @return  La información de todos los peces.
     */
    public void getAllInfoFromPeces() {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "Nombre común", "Nombre científico"});
        if (orderBy != 0) {
            try {
                allFromPeces.setInt(1, orderBy);
                result = allFromPeces.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Devuelve toda la información de un pez en específico.
     * 
     * @param pezID El ID del pez.
     * @return  La información del pez.
     */
    public void getAllInfoFromPez(int pezID) {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "Nombre común", "Nombre científico"});
        if (orderBy != 0) {
            try {
                allFromSpecificPez.setInt(1, pezID);
                allFromSpecificPez.setInt(2, orderBy);
                result = allFromSpecificPez.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Devuelve toda la información de todos los pedidos.
     * 
     * @return  La información de todos los pedidos.
     */
    public void getAllInfoFromPedidos() {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "ID del cliente", "Nombre del cliente", "ID del pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada"});
        if (orderBy != 0) {
            try {
                allFromPedidos.setInt(1, orderBy);
                result = allFromPedidos.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param pedidoID  El ID del pedido.
     * @return  La información del pedido.
     */
    public void getAllInfoFromPedido(int pedidoID) {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "ID del cliente", "Nombre del cliente", "ID del pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada"});
        if (orderBy != 0) {
            try {
                allFromSpecificPedido.setInt(1, pedidoID);
                allFromSpecificPedido.setInt(2, orderBy);
                result = allFromSpecificPedido.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param clienteID  El ID del cliente.
     * @return  La información del pedido.
     */
    public void getAllInfoFromClientePedidos(int clienteID) {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "ID del cliente", "Nombre del cliente", "ID del pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada"});
        if (orderBy != 0) {
            try {
                allFromPedidosFromSpecificCliente.setInt(1, clienteID);
                allFromPedidosFromSpecificCliente.setInt(2, orderBy);
                result = allFromSpecificPedido.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param pezID  El ID del pez.
     * @return  La información del pedido.
     */
    public static void getAllInfoFromPezPedidos(int pezID) {
        ResultSet result = null;
        int orderBy = Reader.menuGenerator(new String[]{"ID", "ID del cliente", "Nombre del cliente", "ID del pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada"});
        if (orderBy != 0) {
            try {
                allFromPedidosFromSpecificPez.setInt(1, pezID);
                allFromPedidosFromSpecificPez.setInt(2, orderBy);
                result = allFromSpecificPedido.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
            showTable(result);
        }
    }

    /**
     * Muestra el resultado de una sentencia en una tabla.
     * 
     * @param res   El resultado de la sentencia.
     */
    private static void showTable(ResultSet res) {
        ResultSetMetaData resMD = null;
        String[] colNames = null;
        String[][] resArray = null;
        int rows = 0;
        try {
            if (res.last()) {
                rows = res.getRow();
            }
            resMD = res.getMetaData();
            colNames = new String[resMD.getColumnCount()];
            for (int i = 0; i < colNames.length; i++) {
                colNames[i] = resMD.getColumnName(i+1);
            }
            resArray = new String[rows-1][colNames.length];

            int i = 0;
            while(res.next()) {
                for (int j = 0; j < colNames.length; j++) {
                    resArray[i][j] = res.getString(j+1);
                }
                i++;
            }

            TextTable table = new TextTable(colNames, resArray);
            table.printTable();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al transformar datos de una sentencia SQL");
        } finally {
            try {
                res.close();
            } catch (SQLException e) {
                res = null;
            }
        }
    }

    /**
     * Cierra todas las sentencias.
     */
    public static void close() {
        try {
            allFromClientes.close();
            allFromSpecificCliente.close();
            allFromPeces.close();
            allFromSpecificPez.close();
            allFromPedidos.close();
            allFromSpecificPedido.close();
            allFromPedidosFromSpecificCliente.close();
            allFromPedidosFromSpecificPez.close();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al cerrar una sentencia SQL");
        }
    }
}