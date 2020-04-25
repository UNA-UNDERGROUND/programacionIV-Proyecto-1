/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend;

import banco.backend.estructuras.Cuenta;
import banco.backend.estructuras.Usuario;
import java.math.BigDecimal;



/**
 *
 * @author jonguz
 */
public class Backend {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controlador controlador = Controlador.getInstancia();
        
        Usuario creds = new Usuario(208010443, "asdf1234", false);
        Cuenta origen = controlador.recuperarCuenta(1);

        boolean res = controlador.agregarTransferencia(origen,2, new BigDecimal("1000"),"Prueba Limite Transferencia(Backend)",creds);
        
        if(res){
            System.out.println("exitoso");
        }
        else{
            System.out.println("fallido");
        }
        
    }
    
}
