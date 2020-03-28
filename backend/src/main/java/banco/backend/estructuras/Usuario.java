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

    //<editor-fold desc="Constructores" defaultstate="collapsed">
    public Usuario() {

    }

    public Usuario(int cedula, String pass) {
        this.cedula = cedula;
        this.pass = pass;
    }

    public Usuario(int cedula, String pass, boolean administrativo) {
        this.cedula = cedula;
        this.pass = pass;
        this.administrativo = administrativo;
    }
    //</editor-fold>

    //<editor-fold desc="Getters/Setters" defaultstate="collapsed">
    //<editor-fold desc="Getters" defaultstate="collapsed">
    public int getCedula() {
        return cedula;
    }

    public String getPass() {
        return pass;
    }

    public boolean esAdministrativo() {
        return administrativo;
    }
    //</editor-fold>
    //<editor-fold desc="Setters" defaultstate="collapsed">
    public Usuario setCedula(int cedula) {
        this.cedula = cedula;
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
    //</editor-fold>
    //</editor-fold>


    public boolean equals(Usuario u) {
        if (administrativo != u.administrativo) {
            return false;
        }
        if (cedula != u.cedula) {
            return false;
        }
        if (!pass.equals(u.pass)) {
            return false;
        }

        return true;
    }

    private boolean administrativo = false;
    private int cedula = 0;
    private String pass = "";

}
