/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jonguz
 */
public class GestorBD {

    private GestorBD() {
        //System.out.printf("Inicializando manejador BD: '%s'..%n", CLASE_MANEJADOR);
        try {
            Class.forName(CLASE_MANEJADOR).getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

    public static GestorBD obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorBD();
        }
        return instancia;
    }

    public Connection obtenerConexion(String baseDatos,
            String usuario, String clave)
            throws SQLException {
        Connection cnx;
        
        //Cambiar URL
        String URL_conexion
                = String.format(FORMATO_CONEXION, PROTOCOLO, URL_SERVIDOR, PUERTO_SERVIDOR, baseDatos);
        cnx = DriverManager.getConnection(URL_conexion, usuario, clave);
        return cnx;
    }

    private static final String CLASE_MANEJADOR = "com.mysql.cj.jdbc.Driver";
    private static final String FORMATO_CONEXION = "%s//%s:%d/%s"
            + "?useTimezone=true&serverTimezone=UTC";
    private static final int PUERTO_SERVIDOR = 3306;
    private static final String PROTOCOLO = "jdbc:mysql:";
    private static final String URL_SERVIDOR = "localhost";

    private static GestorBD instancia = null;
}
