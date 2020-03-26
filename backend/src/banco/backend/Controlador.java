/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend;

import banco.backend.estructuras.Usuario;

/**
 *
 * @author jonathan
 */
public class Controlador {

    
    public Usuario login(Usuario credenciales){
        return new Usuario();
    }
    
    
    private static Controlador uniqueInstance;

    public static Controlador getInstancia() {
        if (uniqueInstance == null) {
            uniqueInstance = new Controlador();
        }
        return uniqueInstance;
    }
}
