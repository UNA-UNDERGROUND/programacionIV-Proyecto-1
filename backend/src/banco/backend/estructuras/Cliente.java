/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend.estructuras;

/**
 *
 * @author jonathan
 */
public class Cliente {

    public Cliente(int cedula) {
        this.cedula = cedula;
    }

    public Cliente(int cedula, String nombre, String apellidos, String numero) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numero = numero;
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Cliente setApellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public Cliente setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public int cedula;
    public String nombre = "";
    public String apellidos = "";
    public String numero = "";
}
