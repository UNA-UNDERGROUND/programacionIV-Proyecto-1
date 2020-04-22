/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras.dao;

import banco.backend.db.BancoDAO;
import banco.backend.estructuras.Cuenta;
import banco.backend.estructuras.Movimiento;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author jonguz
 */
public class MovimientoDAO extends BancoDAO {

    public Movimiento[] recuperarMovimientos(int idCuenta) {
        ArrayList<Movimiento> resultado = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_MOVIMIENTOS)) {
            stm.clearParameters();
            stm.setInt(1, idCuenta);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Movimiento nuevo
                            = new Movimiento(
                                    rs.getInt(1),
                                    rs.getInt(2),
                                    rs.getBoolean(3),
                                    rs.getBigDecimal(4),
                                    rs.getString(5),
                                    rs.getDate(6)
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
        return resultado.toArray(new Movimiento[0]);
    }

    public Movimiento recuperarMovimiento(int idMovimiento) {
        Movimiento resultado = null;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_MOVIMIENTO)) {
            stm.clearParameters();
            stm.setInt(1, idMovimiento);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    resultado
                            = new Movimiento(
                                    rs.getInt(1),
                                    rs.getInt(2),
                                    rs.getBoolean(3),
                                    rs.getBigDecimal(4),
                                    rs.getString(5),
                                    rs.getDate(6)
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

    public boolean agregarMovimiento(Movimiento movimiento) {
        String comandoMovimiento = CMD_AGREGAR_MOVIMIENTO;
        String comandoCuenta = CMD_ACTUALIZAR_CUENTA;
        try (Connection cnx = obtenerConexion()) {

            try (
                    PreparedStatement stmMovimiento
                    = cnx.prepareStatement(comandoMovimiento);
                    PreparedStatement stmCuenta
                    = cnx.prepareStatement(comandoCuenta)) {
                cnx.setAutoCommit(false);

                stmMovimiento.clearParameters();
                stmCuenta.clearParameters();

                stmMovimiento.setInt(1, movimiento.getIdCuenta());
                stmMovimiento.setBoolean(2, movimiento.esDeposito());
                stmMovimiento.setBigDecimal(3, movimiento.getMonto());
                stmMovimiento.setString(4, movimiento.getDescripcion());

                BigDecimal monto
                        = movimiento.esDeposito()
                        ? movimiento.getMonto()
                        : movimiento.getMonto().negate();
                //if (!movimiento.esDeposito()) {
                //   monto.negate();
                //}

                stmCuenta.setBigDecimal(1, monto);
                stmCuenta.setInt(2, movimiento.getIdCuenta());
                stmCuenta.setBigDecimal(3, monto);

                if (stmCuenta.executeUpdate() + stmMovimiento.executeUpdate() != 2) {
                    cnx.rollback();
                    return false;
                }
                cnx.commit();
                return true;
            } catch (Exception ex) {
                cnx.rollback();
            }

            cnx.setAutoCommit(true);
        } catch (Exception ex) {
            System.err.printf("No se pudo realizar la transaccion: %s \n", ex.getMessage());
        }
        return false;
    }

    public boolean agregarTransferencia(Cuenta cuenta, Movimiento movimiento) {
        String comandoMovimiento = CMD_AGREGAR_MOVIMIENTO;
        String comandoCuenta = CMD_ACTUALIZAR_CUENTA;
        try (Connection cnx = obtenerConexion()) {

            try (
                    PreparedStatement stmMovimiento
                    = cnx.prepareStatement(comandoMovimiento);
                    PreparedStatement stmCuenta
                    = cnx.prepareStatement(comandoCuenta);
                    //deducciones
                    PreparedStatement stmMovimientoD
                    = cnx.prepareStatement(comandoMovimiento);
                    PreparedStatement stmCuentaD
                    = cnx.prepareStatement(comandoCuenta);) {
                cnx.setAutoCommit(false);
                
                stmMovimiento.clearParameters();
                stmCuenta.clearParameters();
                stmMovimientoD.clearParameters();
                stmCuentaD.clearParameters();
                
                //primero el deposito

                stmMovimiento.setInt(1, movimiento.getIdCuenta());
                stmMovimiento.setBoolean(2, true);
                stmMovimiento.setBigDecimal(2, movimiento.getMonto());
                stmMovimiento.setString(4, movimiento.getDescripcion());

                BigDecimal monto = movimiento.getMonto();

                stmCuenta.setBigDecimal(1, monto);
                stmCuenta.setInt(2, movimiento.getIdCuenta());
                stmCuenta.setBigDecimal(3, monto);
                
                //deduccion de cuenta origen
                
                stmMovimientoD.setInt(1, cuenta.getIdCuenta());
                stmMovimientoD.setBoolean(2, false);
                stmMovimientoD.setBigDecimal(2, movimiento.getMonto());
                stmMovimientoD.setString(4, movimiento.getDescripcion());
                
                monto = monto.negate();

                stmCuentaD.setBigDecimal(1, monto);
                stmCuentaD.setInt(2, movimiento.getIdCuenta());
                stmCuentaD.setBigDecimal(3, monto);
                

                if (stmCuenta.executeUpdate()
                        + stmMovimiento.executeUpdate()
                        + stmCuentaD.executeUpdate()
                        + stmMovimientoD.executeUpdate()
                        == 4) {
                    cnx.rollback();
                    return false;
                }
                cnx.commit();
                return true;
            } catch (Exception ex) {
                cnx.rollback();
            }

            cnx.setAutoCommit(true);
        } catch (Exception ex) {
            System.err.printf("No se pudo realizar la transaccion: %s \n", ex.getMessage());
        }
        return false;
    }

    //<editor-fold desc="Movimiento" defaultstate="collapsed">
    private static final String CMD_AGREGAR_MOVIMIENTO
            = "insert into movimiento "
            + "(id_cuenta, deposito, monto, descripcion)"
            + "values (?, ?, ?, ?);";
    private static final String CMD_ACTUALIZAR_CUENTA
            = "update cuenta "
            + "set saldo = saldo + ? "
            + "where id_cuenta = ? and saldo + ? >= 0;";
    private static final String CMD_RECUPERAR_MOVIMIENTO
            = "select * from movimiento "
            + "where id_transaccion = ?";
    private static final String CMD_RECUPERAR_MOVIMIENTOS
            = "select * from movimiento "
            + "where id_cuenta = ?";
    //</editor-fold>
}
