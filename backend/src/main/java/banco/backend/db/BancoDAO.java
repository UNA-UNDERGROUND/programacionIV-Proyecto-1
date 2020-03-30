/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.db;

import banco.backend.estructuras.Cliente;
import banco.backend.estructuras.Cuenta;
import banco.backend.estructuras.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author jonguz
 */
public class BancoDAO {

    //<editor-fold desc="Metodos de generales" defaultstate="collapsed">


    protected Connection obtenerConexion() {
        return GestorConexion.obtenerInstancia().obtenerConexion();
    }

    
    //</editor-fold>

}
