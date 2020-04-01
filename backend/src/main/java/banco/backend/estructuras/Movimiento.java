/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author jonguz
 */
public class Movimiento {

    public Movimiento(
            int idCuenta,
            boolean deposito,
            BigDecimal monto,
            String descripcion) {
        this.idCuenta = idCuenta;
        this.deposito = deposito;
        this.monto = monto.signum() == -1 ? monto.negate() : monto;
        this.descripcion = descripcion;

    }

    public Movimiento(
            int idTransaccion,
            int idCuenta,
            boolean deposito,
            BigDecimal monto,
            String descripcion,
            Date fechaDeposito) {
        this(idCuenta, deposito, monto, descripcion);
        this.idTransaccion = idTransaccion;
        this.fechaDeposito = fechaDeposito;

    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public boolean esDeposito() {
        return deposito;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    private int idTransaccion = 0;
    private int idCuenta;
    private boolean deposito;
    private BigDecimal monto;
    private String descripcion = "";
    private Date fechaDeposito = null;

}
