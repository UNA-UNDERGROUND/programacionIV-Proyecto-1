/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras.dao;

import banco.backend.db.BancoDAO;

/**
 *
 * @author jonguz
 */
public class MovimientoDAO extends BancoDAO{
    //<editor-fold desc="Movimiento" defaultstate="collapsed">
    private static final String CMD_AGREGAR_MOVIMIENTO
            = "insert into movimiento "
            + "(id_cuenta, deposito, monto, descripcion)"
            + "values (?, ?, ?, ?);";
    private static final String CMD_RECUPERAR_MOVIMIENTO
            = "select * from movimiento "
            + "where id_transaccion = ?";
    //</editor-fold>
}
