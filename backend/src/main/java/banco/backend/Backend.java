/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend;

import banco.backend.dao.BancoDAO;
import banco.backend.estructuras.Usuario;


/**
 *
 * @author jonguz
 */
public class Backend {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BancoDAO instancia = BancoDAO.obtenerInstancia();

        Usuario cuentas[] = instancia.recuperarUsuarios();
        
        for(Usuario u: cuentas){
            System.out.println(u);
        }
        
    }
    
}
