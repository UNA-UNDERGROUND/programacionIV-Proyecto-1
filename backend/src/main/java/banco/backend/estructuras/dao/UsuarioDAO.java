/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras.dao;

import banco.backend.db.BancoDAO;
import banco.backend.estructuras.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author jonguz
 */
public class UsuarioDAO extends BancoDAO{
    
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public boolean agregarUsuario(Usuario usuario) {
        String comando = usuario.esAdministrativo() ? CMD_AGREGAR_ADMIN : CMD_AGREGAR_USUARIO;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(comando)) {

            stm.clearParameters();
            stm.setInt(1, usuario.getCedula());
            stm.setString(2, usuario.getPass());

            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un usuario nuevo: %s \n", ex.getMessage());
        }
        return false;
    }

    public Usuario recuperarUsuario(Usuario credenciales) {
        Usuario resultado = null;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm
                = cnx.prepareStatement(
                        credenciales.esAdministrativo()
                        ? CMD_RECUPERAR_ADMIN
                        : CMD_RECUPERAR_USUARIO)) {
            stm.clearParameters();

            stm.setInt(1, credenciales.getCedula());

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    resultado = new Usuario(rs.getInt(1), rs.getString(2), credenciales.esAdministrativo());
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return resultado;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String comando = usuario.esAdministrativo() ? CMD_ACTUALIZAR_ADMIN : CMD_ACTUALIZAR_USUARIO;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(comando)) {

            stm.clearParameters();
            stm.setString(1, usuario.getPass());
            stm.setInt(2, usuario.getCedula());
            stm.setString(3, usuario.getPass());

            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un usuario nuevo: %s \n", ex.getMessage());
        }
        return false;
    }

    //</editor-fold>
    
    //<editor-fold desc="Usuario" defaultstate="collapsed">
    private static final String CMD_AGREGAR_USUARIO
            = "INSERT INTO usuario "
            + "(cedula, pass) "
            + "VALUES (?, ?);";
    private static final String CMD_RECUPERAR_USUARIO
            = "select * from usuario "
            + "where cedula = ?;";
    private static final String CMD_ACTUALIZAR_USUARIO
            = "UPDATE usuario "
            + "SET pass = ? "
            + "WHERE cedula=?, pass = ?;";
    //</editor-fold>
    //<editor-fold desc="Administrador" defaultstate="collapsed">
    private static final String CMD_AGREGAR_ADMIN
            = "INSERT INTO administrador "
            + "(cedula, pass) "
            + "VALUES (?, ?);";
    private static final String CMD_RECUPERAR_ADMIN
            = "select * from administrador "
            + "where cedula = ?;";
    private static final String CMD_ACTUALIZAR_ADMIN
            = "UPDATE administrador "
            + "SET pass = ? "
            + "WHERE cedula=?, pass = ?;";
    //</editor-fold>
    
    
}
