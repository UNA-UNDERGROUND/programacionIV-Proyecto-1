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

    public Usuario() {
        
    }

    public Usuario(String cedula, String pass) {
        this.cedula = cedula;
        this.pass = pass;
    }

    public Usuario(String cedula, String pass, boolean administrativo) {
        this.cedula = cedula;
        this.pass = pass;
        this.administrativo = administrativo;
    }

    public Usuario(Object[] datos) {
        this((String) datos[0], (String) datos[1], (boolean) datos[2]);
    }

    public Usuario setCedula(String cedula) {
        this.cedula=cedula;
        return this;
    }

    public Usuario setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public Usuario setAdministrativo(boolean administrativo) {
        this.administrativo = administrativo;
        return this;
    }

    public String getCedula() {
        return cedula;
    }

    public String getPass() {
        return pass;
    }

    public boolean esAdministrativo() {
        return administrativo;
    }

    public Object[] toArray() {
        return new Object[]{cedula, pass, administrativo};
    }

    public String toString() {
        return "Cedula de Usuario: " + cedula + " Tipo de cuenta : " + (administrativo ? "Administrativo" : "Cliente");
    }

    private String cedula="";
    private String pass="";
    private boolean administrativo=false;
}
