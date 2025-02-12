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
    /** Sentencia preparada para entregar peces a un pedido */
    private static PreparedStatement deliverFish;

    /**
     * Prepara todas las sentencias con comodines para facilitar su uso.
     * 
     * @param conn  La conexión con la que crear las sentencias.
     */
    public static void prepareStatements(Connection conn) {
        try {
            allFromClientes = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre', nif AS 'NIF', telefono AS 'Teléfono' FROM cliente ORDER BY ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            allFromSpecificCliente = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre', nif AS 'NIF', telefono AS 'Teléfono' FROM cliente WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            allFromPeces = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre común', cientifico AS 'Nombre científico' FROM pez ORDER BY ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            allFromSpecificPez = conn.prepareStatement("SELECT id AS 'ID', nombre AS 'Nombre común', cientifico AS 'Nombre científico' FROM pez WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
                "ORDER BY ?",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
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
                "WHERE pedido.id = ? ",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
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
                "WHERE pedido.cliente_id = ? " +
                "ORDER BY ?",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
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
                "ORDER BY ?",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
            );

            deliverFish = conn.prepareStatement(
                "UPDATE pedido SET enviados = enviados + ? WHERE id = ?"
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
    public static ResultSet getAllInfoFromClients(boolean order) {
        ResultSet result = null;
        int orderBy = 1;
        if (order) {
            orderBy = Reader.menuGenerator(new String[]{"Cómo quieres que se ordenen los datos?", "ID", "Nombre", "NIF", "Teléfono"});
        }
        if (orderBy != 0) {
            try {
                allFromClientes.setInt(1, orderBy);
                result = allFromClientes.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
        }
        return result;
    }

    /**
     * Devuelve toda la información de un cliente en específico.
     * 
     * @param clientID  El ID del cliente.
     * @return  La información del cliente.
     */
    public static ResultSet getAllInfoFromClient(int clientID) {
        ResultSet result = null;
        try {
            allFromSpecificCliente.setInt(1, clientID);
            result = allFromSpecificCliente.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        return result;
    }

    /**
     * Devuelve toda la información de todos los peces.
     * 
     * @return  La información de todos los peces.
     */
    public static ResultSet getAllInfoFromPeces(boolean order) {
        ResultSet result = null;
        int orderBy = 1;
        if (order) {
            orderBy = Reader.menuGenerator(new String[]{"Cómo quieres que se ordenen los datos?", "ID", "Nombre común", "Nombre científico"});
        }
        if (orderBy != 0) {
            try {
                allFromPeces.setInt(1, orderBy);
                result = allFromPeces.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
        }
        return result;
    }

    /**
     * Devuelve toda la información de un pez en específico.
     * 
     * @param pezID El ID del pez.
     * @return  La información del pez.
     */
    public static ResultSet getAllInfoFromPez(int pezID) {
        ResultSet result = null;
        try {
            allFromSpecificPez.setInt(1, pezID);
            result = allFromSpecificPez.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        return result;
    }

    /**
     * Devuelve toda la información de todos los pedidos.
     * 
     * @return  La información de todos los pedidos.
     */
    public static ResultSet getAllInfoFromPedidos(boolean order) {
        ResultSet result = null;
        int orderBy = 1;
        if (order) {
            orderBy = Reader.menuGenerator(new String[]{"Cómo quieres que se ordenen los datos?", "ID", "ID del cliente", "Nombre del cliente", "ID del pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada"});
        }
            if (orderBy != 0) {
            try {
                allFromPedidos.setInt(1, orderBy);
                result = allFromPedidos.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
        }
        return result;
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param pedidoID  El ID del pedido.
     * @return  La información del pedido.
     */
    public static ResultSet getAllInfoFromPedido(int pedidoID) {
        ResultSet result = null;
        try {
            allFromSpecificPedido.setInt(1, pedidoID);
            result = allFromSpecificPedido.executeQuery();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
        }
        return result;
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param clienteID  El ID del cliente.
     * @return  La información del pedido.
     */
    public static ResultSet getAllInfoFromClientePedidos(int clienteID, boolean order) {
        ResultSet result = null;
        int orderBy = 1;
        if (order) {
            orderBy = Reader.menuGenerator(new String[]{"Cómo quieres que se ordenen los datos?", "ID", "ID del cliente", "Nombre del cliente", "ID del pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada"});
        }
        if (orderBy != 0) {
            try {
                allFromPedidosFromSpecificCliente.setInt(1, clienteID);
                allFromPedidosFromSpecificCliente.setInt(2, orderBy);
                result = allFromPedidosFromSpecificCliente.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
        }
        return result;
    }

    /**
     * Devuelve toda la información de un pedido en específico.
     * 
     * @param pezID  El ID del pez.
     * @return  La información del pedido.
     */
    public static ResultSet getAllInfoFromPezPedidos(int pezID, boolean order) {
        ResultSet result = null;
        int orderBy = 1;
        if (order) {
            orderBy = Reader.menuGenerator(new String[]{"Cómo quieres que se ordenen los datos?", "ID", "ID del cliente", "Nombre del cliente", "ID del pez", "Tipo de pez", "Cantidad pedida", "Cantidad entregada"});
        }
            if (orderBy != 0) {
            try {
                allFromPedidosFromSpecificPez.setInt(1, pezID);
                allFromPedidosFromSpecificPez.setInt(2, orderBy);
                result = allFromPedidosFromSpecificPez.executeQuery();
            } catch (SQLException e) {
                ErrorWriter.writeInErrorLog("Error al ejecutar una sentencia SQL");
            }
        }
        return result;
    }

    /**
     * Comprueba la cantidad de peces necesarios para completar un pedido.
     * 
     * @param idPedido  El ID del pedido
     * @return  El número de peces necesarios para completar el pedido
     */
    public static int checkCantidad(int idPedido) {
        ResultSet pedido = getAllInfoFromPedido(idPedido);
        try {
            pedido.next();
            return pedido.getInt("Cantidad pedida") - pedido.getInt("Cantidad entregada");
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error en la comprobación de cantidad de peces necesarios apra un pedido");
        }
        return -1;
    }

    /**
     * Añade peces a un pedido.
     * 
     * @param idPedido
     * @param fishCount
     */
    public static void deliverFish(int idPedido, int fishCount) {
        try {
            deliverFish.setInt(1, fishCount);
            deliverFish.setInt(2, idPedido);
            deliverFish.execute();
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al entregar peces a un pedido");
        }
    }

    /**
     * Muestra el resultado de una sentencia en una tabla.
     * 
     * @param res   El resultado de la sentencia.
     */
    public static void showTable(ResultSet res) {
        ResultSetMetaData resMD = null;
        String[] colNames = null;
        String[][] resArray = null;
        int rows = 0;
        try {
            if (res.last()) {
                rows = res.getRow();
            }
            res.beforeFirst();
            resMD = res.getMetaData();
            colNames = new String[resMD.getColumnCount()];
            for (int i = 0; i < colNames.length; i++) {
                colNames[i] = resMD.getColumnName(i+1);
            }
            resArray = new String[rows][colNames.length];

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