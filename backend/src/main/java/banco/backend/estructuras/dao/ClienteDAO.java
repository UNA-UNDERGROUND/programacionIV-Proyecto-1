/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras.dao;

import banco.backend.db.BancoDAO;
import banco.backend.estructuras.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author jonguz
 */
public class ClienteDAO extends BancoDAO{
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public boolean agregarCliente(Cliente cliente) {
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_CLIENTE)) {

            stm.clearParameters();
            stm.setInt(1, cliente.getCedula());
            stm.setString(2, cliente.getNombre());
            stm.setString(3, cliente.getApellidos());
            stm.setString(4, cliente.getNumero());

            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un cliente nuevo: %s \n", ex.getMessage());
        }
        return false;
    }

    public Cliente recuperarCliente(int cedula) {
        Cliente resultado = null;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_CLIENTE)) {
            stm.clearParameters();

            stm.setInt(1, cedula);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    resultado
                            = new Cliente(
                                    rs.getInt(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4)
                            );
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } catch (Exception ex) {
            String error = ex.getLocalizedMessage();
            System.err.println(error);
        }
        return resultado;
    }

    public boolean actualizarCliente(Cliente cliente) {
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR_CLIENTE)) {

            stm.clearParameters();

            stm.setString(1, cliente.getNombre());
            stm.setString(2, cliente.getApellidos());
            stm.setString(3, cliente.getNumero());
            stm.setInt(4, cliente.getCedula());

            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un cliente nuevo: %s \n", ex.getMessage());
        }
        return false;
    }

    //</editor-fold>
    
    //<editor-fold desc="Comandos" defaultstate="collapsed">
    private static final String CMD_AGREGAR_CLIENTE
            = "insert into cliente "
            + "(cedula, nombre, apellidos, telefono) "
            + "values (?, ?, ?, ?);";
    private static final String CMD_RECUPERAR_CLIENTE
            = "select * from cliente "
            + "where cedula = ?;";
    private static final String CMD_ACTUALIZAR_CLIENTE
            = "UPDATE cliente "
            + "SET nombre = ?, apellidos=?, numero=? "
            + "WHERE cedula=?;";
    //</editor-fold>
}
