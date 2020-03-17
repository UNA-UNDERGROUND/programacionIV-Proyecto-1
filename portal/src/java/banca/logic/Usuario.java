/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca.logic;

/**
 *
 * @author jonathan
 */
public class Usuario {


    public Usuario(){
        
    }
    public Usuario(String cedula, String nombre){
        this.cedula = cedula;
        this.nombre = nombre;
    }
    
        public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }
    
    public String nombre;
    public String cedula;
}
