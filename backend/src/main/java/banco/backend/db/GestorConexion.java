/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author jonguz
 */
public class GestorConexion {

    private GestorConexion() {
        try {

            this.cfg.load(getClass().getResourceAsStream(UBICACION_CREDENCIALES));
            this.baseDatos = cfg.getProperty("base_datos");
            this.usuario = cfg.getProperty("usuario");
            this.clave = cfg.getProperty("clave");
        } catch (Exception ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }
    
    public static GestorConexion obtenerInstancia() {
        return instancia == null ? instancia = new GestorConexion() : instancia;
    }

    Connection obtenerConexion() {
        try {
            return GestorBD.obtenerInstancia().obtenerConexion(baseDatos, usuario, clave);
        } catch (SQLException e) {
            String error = e.getLocalizedMessage();
            System.err.printf("No se pudo conectar con la base de datos: %s \n", error);
            return null;
        }

    }
    
    private static GestorConexion instancia;
    private static final String UBICACION_CREDENCIALES = "/configuraciones/credenciales-bd.properties";
    private Properties cfg = new Properties();
    private String baseDatos;
    private String usuario;
    private String clave;
}
