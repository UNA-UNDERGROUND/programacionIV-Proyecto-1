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
public class Moneda {

    public Moneda(
            String codigo,
            String nombre,
            String signo,
            BigDecimal precioCompra,
            float porcentajeInteres) {
        
            this.codigo = codigo;
            this.nombre = nombre;
            this.signo = signo;
            this.precioCompra = precioCompra;
            this.porcentajeInteres = porcentajeInteres;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSigno() {
        return signo;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public float getPorcentajeInteres() {
        return porcentajeInteres;
    }
    
    public boolean equals(Moneda otro){
        return codigo.equals(otro.codigo);
    }

    private final String codigo;
    private final String nombre;
    private final String signo;
    private final BigDecimal precioCompra;
    private final float porcentajeInteres;

}
