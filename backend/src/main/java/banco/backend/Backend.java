/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend;

import banco.backend.estructuras.Usuario;
import banco.presentation.login.Credenciales;
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
        
        Usuario creds = new Usuario(208010443, "asdf1234", true);

        boolean res = controlador.agregarMovimiento(1, false, new BigDecimal("2000"), "prueba backend", creds);
        
        if(res){
            System.out.println("exitoso");
        }
        else{
            System.out.println("fallido");
        }
        
    }
    
}
