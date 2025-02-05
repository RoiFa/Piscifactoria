package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.ErrorWriter;

public class DAOPedidos {

    private static PreparedStatement allFromClientes;
    private static PreparedStatement allFromSpecificCliente;
    private static PreparedStatement allFromPeces;
    private static PreparedStatement allFromSpecificPez;
    private static PreparedStatement allFromPedidos;
    private static PreparedStatement allFromSpecificPedido;

    public static void prepareStatements(Connection conn) {
        try {
            allFromClientes = conn.prepareStatement("SELECT * FROM cliente");
            allFromSpecificCliente = conn.prepareStatement("SELECT * FROM cliente WHERE id = ?");
            allFromPeces = conn.prepareStatement("SELECT * FROM pez");
            allFromSpecificPez = conn.prepareStatement("SELECT * FROM pez WHERE id = ?");
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
            allFromSpecificPedido = conn.prepareStatement()

        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al preparar las sentencias SQL.");
        }
    }
    
    public ResultSet getAllClientsInfo() {
        try {
            PreparedStatement stm = conn.prepareStatement(
                "SELECT * FROM cliente"
            );
        } catch (SQLException e) {
            ErrorWriter.writeInErrorLog("Error al preparar una sentencia SQL.");
        }
    }
}