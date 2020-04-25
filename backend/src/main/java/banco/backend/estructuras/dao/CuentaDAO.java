/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras.dao;

import banco.backend.db.BancoDAO;
import banco.backend.estructuras.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author jonguz
 */
public class CuentaDAO extends BancoDAO {

    //<editor-fold desc="Cuenta" defaultstate="collapsed">
    public boolean agregarCuenta(Cuenta cuenta) {
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_CUENTA)) {

            stm.clearParameters();
            stm.setInt(1, cuenta.getCedula());
            stm.setString(2, cuenta.getMoneda());
            stm.setInt(3, cuenta.getLimiteTransferencia());

            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un cliente nuevo: %s \n", ex.getMessage());
        }
        return false;
    }

    public Cuenta recuperarCuenta(int numeroCuenta) {
        Cuenta resultado = null;
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_CUENTA)) {
            stm.clearParameters();

            stm.setInt(1, numeroCuenta);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    resultado
                            = new Cuenta(
                                    rs.getInt(1),
                                    rs.getInt(2),
                                    rs.getString(3),
                                    rs.getBigDecimal(4),
                                    rs.getInt(4)
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

    public Cuenta[] recuperarCuentas(int cedula) {
        ArrayList<Cuenta> resultado = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_CUENTAS)) {
            stm.clearParameters();

            stm.setInt(1, cedula);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Cuenta nuevo
                            = new Cuenta(
                                    rs.getInt(1),
                                    rs.getInt(2),
                                    rs.getString(3),
                                    rs.getBigDecimal(4),
                                    rs.getInt(4)
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
        return resultado.toArray(new Cuenta[0]);
    }

    //</editor-fold>
    //<editor-fold desc="Cuenta Vinculada" defaultstate="collapsed">
    public boolean agregarCuentaVinculada(int cedula, int id_cuenta) {
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_CUENTA_VINCULADA)) {

            stm.clearParameters();
            stm.setInt(1, id_cuenta);
            stm.setInt(2, cedula);
            stm.setInt(3, id_cuenta);
            stm.setInt(4, cedula);
            stm.setInt(5, id_cuenta);
            stm.setInt(6, cedula);

            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un cliente nuevo: %s \n", ex.getMessage());
        }
        return false;
    }

    public Cuenta[] recuperarCuentasVinculadas(int cedula) {
        ArrayList<Cuenta> resultado = new ArrayList<>();
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR_CUENTAS_VINCULADAS)) {
            stm.clearParameters();

            stm.setInt(1, cedula);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Cuenta nuevo
                            = new Cuenta(
                                    rs.getInt(1),
                                    rs.getInt(2),
                                    rs.getString(3),
                                    rs.getBigDecimal(4),
                                    rs.getInt(4)
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
        return resultado.toArray(new Cuenta[0]);
    }

    public boolean removerCuentaVinculada(int cedula, int id_cuenta) {
        try (Connection cnx = obtenerConexion();
                PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR_CUENTA_VINCULADA)) {

            stm.clearParameters();
            stm.setInt(1, id_cuenta);
            stm.setInt(2, cedula);

            return stm.executeUpdate() == 1;
        } catch (Exception ex) {
            System.err.printf("No se pudo insertar un cliente nuevo: %s \n", ex.getMessage());
        }
        return false;
    }
    //</editor-fold>

    //<editor-fold desc="Cuenta" defaultstate="collapsed">
    private static final String CMD_AGREGAR_CUENTA
            = "insert into cuenta "
            + "(cedula, moneda, saldo, limite_transferencia) "
            + "values (?, ?, 0, ?);";
    private static final String CMD_RECUPERAR_CUENTA
            = "select * from cuenta "
            + "where id_cuenta = ?;";
    private static final String CMD_RECUPERAR_CUENTAS
            = "select * from cuenta "
            + "where cedula= ?;";
    private static final String CMD_ACTUALIZAR_CUENTA_SALDO
            = "UPDATE cliente "
            + "SET saldo = ? "
            + "WHERE cedula=?;";
    private static final String CMD_ACTUALIZAR_CUENTA_LIMITE
            = "UPDATE cliente "
            + "SET saldo = ? "
            + "WHERE cedula=?;";
    //</editor-fold>
    //<editor-fold desc="Cuenta Vinculada" defaultstate="collapsed">
    private static final String CMD_AGREGAR_CUENTA_VINCULADA
            = "insert into cuenta_vinculada "
            + "select ?, ? "
            + "from cuenta "
            + "where "
            + "id_cuenta = ? and "
            + "not cedula = ? "
            + "and not exists( "
            + "select * "
            + "from cuenta_vinculada "
            + "where id_cuenta = ? and cedula = ?"
            + ");";
    private static final String CMD_RECUPERAR_CUENTAS_VINCULADAS
            = "select cuenta.* from cuenta_vinculada "
            + "inner join cuenta "
            + "on cuenta_vinculada.id_cuenta=cuenta.id_cuenta and cuenta_vinculada.cedula = ?;";
    private static final String CMD_REMOVER_CUENTA_VINCULADA
            = "delete from cuenta_vinculada "
            + "where id_cuenta= ? cedula= ? ";

    //</editor-fold>
}
