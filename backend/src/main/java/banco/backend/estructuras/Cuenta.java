/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras;

import java.math.BigDecimal;

/**
 *
 * @author jonguz
 */
public class Cuenta {

    //<editor-fold desc="Constructores" defaultstate="collapsed">
    public Cuenta(){
        
    }
    public Cuenta(int cedula){
        this.cedula = cedula;
    }
    public Cuenta(int cedula, String moneda, BigDecimal saldo, int limiteDiario) {
        this(0, cedula, moneda, saldo, limiteDiario);
    }

    public Cuenta(int id, int cedula, String moneda, BigDecimal saldo, int limiteDiario) {
        this.idCuenta = id;
        this.cedula = cedula;
        this.moneda = moneda;
        this.saldo = saldo;
        this.limiteTransferencia = limiteDiario;
    }
    //</editor-fold>

    //<editor-fold desc="Getters/Setters" defaultstate="collapsed">
    //<editor-fold desc="Getters" defaultstate="collapsed">
    public int getIdCuenta() {
        return idCuenta;
    }

    public int getCedula() {
        return cedula;
    }

    public String getMoneda() {
        return moneda;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public int getLimiteTransferencia() {
        return limiteTransferencia;
    }
    
    //</editor-fold>
    //<editor-fold desc="Setters" defaultstate="collapsed">

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setLimiteTransferencia(int limiteTransferencia) {
        this.limiteTransferencia = limiteTransferencia;
    }

    //</editor-fold>
    //</editor-fold>

    private int idCuenta = 0;
    private int cedula = 0;
    private String moneda = "CRC";
    private BigDecimal saldo = new BigDecimal(0);
    private int limiteTransferencia = 0;

}
