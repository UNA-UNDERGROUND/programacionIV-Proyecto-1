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
 * @author jonathan
 */
public class Controlador {

    private Controlador(){
        
    }
    
    public Usuario login(Usuario credenciales){
        return new Usuario();
    }
    
    
    private static Controlador instancia;

    public static Controlador getInstancia() {
        return  instancia == null ? instancia=new Controlador() : instancia;
    }
    BancoDAO bd = BancoDAO.obtenerInstancia();
}
