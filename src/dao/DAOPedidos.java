package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dnl.utils.text.table.TextTable;
import helpers.ErrorWriter;

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
            allFromClientes = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre', nif AS 'NIF', telefono AS 'Teléfono' FROM cliente");
            allFromSpecificCliente = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre', nif AS 'NIF', telefono AS 'Teléfono' FROM cliente WHERE id = ?");
            allFromPeces = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre común', cientifico AS 'Nombre científico' FROM pez");
            allFromSpecificPez = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre común', cientifico AS 'Nombre científico' FROM pez WHERE id = ?");
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
                "ORDER BY pedido.id DESC"
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
                "ORDER BY pedido.id DESC"
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
                "ORDER BY pedido.id DESC"
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
                "ORDER BY pedido.id DESC"
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
        try {
            result = allFromClientes.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showClientInfo(result);
    }

    /**
     * Devuelve toda la información de un cliente en específico.
     * 
     * @param clientID  El ID del cliente.
     * @return  La información del cliente.
     */
    public void getAllInfoFromClient(int clientID) {
        ResultSet result = null;
        try {
            allFromSpecificCliente.setInt(1, clientID);
            result = allFromSpecificCliente.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showClientInfo(result);
    }

    /**
     * Devuelve toda la información de todos los peces.
     * 
     * @return  La información de todos los peces.
     */
    public void getAllInfoFromPeces() {
        ResultSet result = null;
        try {
            result = allFromPeces.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showPezInfo(result);
    }

    /**
     * Devuelve toda la información de un pez en específico.
     * 
     * @param pezID El ID del pez.
     * @return  La información del pez.
     */
    public void getAllInfoFromPez(int pezID) {
        ResultSet result = null;
        try {
            allFromSpecificPez.setInt(1, pezID);
            result = allFromSpecificPez.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showPezInfo(result);
    }

    /**
     * Devuelve toda la información de todos los pedidos.
     * 
     * @return  La información de todos los pedidos.
     */
    public void getAllInfoFromPedidos() {
        ResultSet result = null;
        try {
            result = allFromPedidos.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showPedidoInfo(result);
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param pedidoID  El ID del pedido.
     * @return  La información del pedido.
     */
    public void getAllInfoFromPedido(int pedidoID) {
        ResultSet result = null;
        try {
            allFromSpecificPedido.setInt(1, pedidoID);
            result = allFromSpecificPedido.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showPedidoInfo(result);
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param pedidoID  El ID del pedido.
     * @return  La información del pedido.
     */
    public void getAllInfoFromClientePedidos(int clienteID) {
        ResultSet result = null;
        try {
            allFromPedidosFromSpecificCliente.setInt(1, clienteID);
            result = allFromSpecificPedido.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showPedidoInfo(result);
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param pedidoID  El ID del pedido.
     * @return  La información del pedido.
     */
    public static void getAllInfoFromPezPedidos(int pezID) {
        ResultSet result = null;
        try {
            allFromPedidosFromSpecificPez.setInt(1, pezID);
            result = allFromSpecificPedido.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        showPedidoInfo(result);
    }

    /**
     * Muestra en formato tabla la información de una sentencia a la tabla cliente
     * 
     * @param info  El resultado de la sentencia
     */
    private static void showClientInfo(ResultSet info) {
        String tableFormat = "%5d%20s%10s%10d";
        System.out.format("%5s%20s%10s%10s", "ID", "Nombre", "NIF", "Teléfono");
        if (info != null) {
            try {
                while (info.next()) {
                    System.out.format(tableFormat, info.getInt(1), info.getString(2), info.getString(3), info.getInt(4));
                }
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al mostrar información de una sentencia ejecutada.");
            }
        }
    }

    /**
     * Muestra en formato tabla la información de una sentencia a la tabla pez
     * 
     * @param info  El resultado de la sentencia
     */
    private static void showPezInfo(ResultSet info) {
        String tableFormat = "%5d%20s%25s%";
        System.out.format("%5s%20s%25s%", "ID", "Nombre", "Nombre científico");
        if (info != null) {
            try {
                while (info.next()) {
                    System.out.format(tableFormat, info.getInt(1), info.getString(2), info.getString(3), info.getInt(4));
                }
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al mostrar información de una sentencia ejecutada.");
            }
        }
    }

    /**
     * Muestra en formato tabla la información de una sentencia a la tabla pedido
     * 
     * @param info  El resultado de la sentencia
     */
    private static void showPedidoInfo (ResultSet info) {
        String tableFormat = "%5d%15d%20s%15d%20s%15d%20d";
        System.out.format("%5s%15s%20s%15s%20s%15s%20s", "ID", "ID del cliente", "Nombre del cliente", "ID de pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada");
        if (info != null) {
            try {
                while (info.next()) {
                    System.out.format(tableFormat, info.getInt(1), info.getInt(2), info.getString(3), info.getInt(4), info.getString(5), info.getInt(6), info.getString(7));
                }
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al mostrar información de una sentencia ejecutada.");
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

    private String[][] resultSetToArray(ResultSet forfor) {
        //TODO arreglar esta mierda
    }
}