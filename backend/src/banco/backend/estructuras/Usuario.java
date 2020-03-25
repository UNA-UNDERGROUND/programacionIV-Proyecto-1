/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras;

/**
 *
 * @author jonguz
 */
public class Usuario {
    public Usuario(){
        
    }
    public Usuario(String cedula, String pass){
        this.cedula = cedula;
        this.pass = pass;
    }
    public Usuario(Object[] datos){
        this((String)datos[0], (String)datos[1]);
    }
    
    public Usuario setPass(String pass){
        this.pass = pass;
        return this;
    }
    public String getCedula(){
        return cedula;
    }
    
    public String getPass(){
        return pass;
    }
    
    public Object[] toArray(){
        return new Object[]{cedula, pass};
    }
    
    public String toString(){
        return "Cedula de Usuario: " + cedula + " Pass: " + pass;
    }
    
    private String cedula;
    private String pass;
}
