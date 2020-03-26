/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.dao;

import banco.backend.estructuras.Cliente;
import banco.backend.estructuras.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author jonguz
 */
public class BancoDAO {

    //<editor-fold desc="Metodos de generales" defaultstate="collapsed">
    private BancoDAO() {
        try {
            this.cfg.load(getClass().getResourceAsStream("configuracion.properties"));
            this.baseDatos = cfg.getProperty("base_datos");
            this.usuario = cfg.getProperty("usuario");
            this.clave = cfg.getProperty("clave");
        } catch (IOException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }
    
    private Connection obtenerConexion(){
        try{
            return GestorBD.obtenerInstancia().obtenerConexion(baseDatos, usuario, clave);
        }
        catch(SQLException e){
            String error = e.getLocalizedMessage();
            System.err.printf("No se pudo conectar con la base de datos: %s \n", error);
            return null;
        }
        
    }

    public static BancoDAO obtenerInstancia() {
        return instancia == null ? instancia=new BancoDAO() : instancia;
    }
    //</editor-fold>

    //<editor-fold desc="Metodos de estructuras" defaultstate="collapsed">
    //<editor-fold desc="Cuenta" defaultstate="collapsed">
    public boolean agregarUsuario(Usuario usuario) {

        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_USUARIO)) {

            stm.clearParameters();
            stm.setInt(1, usuario.getCedula());
            stm.setString(2, usuario.getPass());
            
            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un usuario nuevo: %s \n", ex.getMessage());
        }
        return false;
    }
    public Usuario[] recuperarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_USUARIOS)) {
            stm.clearParameters();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Usuario(rs.getInt(1), rs.getString(2)));
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return lista.toArray(new Usuario[0]);
    }
    public Usuario recuperarUsuario(int  cedula) {
        Usuario resultado=null;
        try (Connection cnx = obtenerConexion();
            PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_USUARIO)) {
            stm.clearParameters();
            
            stm.setInt(1, cedula);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    resultado = new Usuario(rs.getInt(1), rs.getString(2));
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return resultado;
    }
    
    

    //</editor-fold>

    //<editor-fold desc="Cliente" defaultstate="collapsed">

        public Cliente recuperarCliente(int  cedula) {
        Cliente resultado=null;
        try (Connection cnx = obtenerConexion();
            PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_CLIENTE)) {
            stm.clearParameters();
            
            stm.setInt(1, cedula);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    resultado = 
                        new Cliente(
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
            System.err.println(ex.getMessage());
        }
        return resultado;
    }
    
    //</editor-fold>
    //</editor-fold>
    private static BancoDAO instancia = null;

    private Properties cfg=new Properties();
    private String baseDatos;
    private String usuario;
    private String clave;

    //<editor-fold desc="SENTENCIAS SQL" defaultstate="collapsed">
    private static final String CMD_AGREGAR_USUARIO
            = "INSERT INTO usuario (cedula, pass) VALUES (?, ?);";
    private static final String CMD_RECUPERAR_USUARIOS
            = "select * from usuario;";
    private static final String CMD_RECUPERAR_USUARIO
        = "select * from usuario "
        + "where cedula = ?";
    
    private static final String CMD_ACTUALIZAR_USUARIO
            = "UPDATE usuario " +
            "SET pass = ? " +
            "WHERE cedula=?, pass = ?;";

    private static final String CMD_RECUPERAR_CLIENTE
        = "select * from cliente"
        + "where cedula = *";
    
    
    //</editor-fold>


}