/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras.dao;

import banco.backend.db.BancoDAO;
import banco.backend.estructuras.Cuenta;
import banco.backend.estructuras.Moneda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author jonguz
 */
public class MonedaDAO extends BancoDAO{
    
    public Moneda[] recuperarMonedas(){
        ArrayList<Moneda> resultado = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_MONEDAS)) {
            stm.clearParameters();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Moneda nuevo
                            = new Moneda(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getBigDecimal(4),
                                    rs.getFloat(4)
                            );
                    resultado.add(nuevo);
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } catch (Exception ex) {
            String error = ex.getLocalizedMessage();
            System.err.println(error);
    }
        return resultado.toArray(new Moneda[0]);
    }
    public Moneda recuperarMoneda(String codigo){
        Moneda resultado = null;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_MONEDA)) {
            stm.clearParameters();
            
            stm.setString(1, codigo);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    resultado
                            = new Moneda(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getBigDecimal(4),
                                    rs.getFloat(4)
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
    
    
    //<editor-fold desc="Moneda" defaultstate="collapsed">
    private static final String CMD_RECUPERAR_MONEDA
            = "select * from moneda "
            + "where codigo= ?";
    private static final String CMD_RECUPERAR_MONEDAS
            = "select * from moneda";
            
    //</editor-fold>
}
