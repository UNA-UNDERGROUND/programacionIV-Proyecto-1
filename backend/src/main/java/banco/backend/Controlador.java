/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend;

import banco.backend.db.BancoDAO;
import banco.backend.estructuras.Cliente;
import banco.backend.estructuras.Usuario;
import banco.backend.estructuras.dao.ClienteDAO;
import banco.backend.estructuras.dao.UsuarioDAO;

/**
 *
 * @author jonathan
 */
public class Controlador {

    private Controlador() {

    }

    public Usuario login(Usuario credenciales) {
        Usuario  res = daoUsuario.recuperarUsuario(credenciales);
        
        if(res!=null && !res.equals(credenciales)){
                res=null;
        }

        return res;
    }
    
    public Cliente   recuperarDatosPersonales(int cedula){
        Cliente resultado = daoCliente.recuperarCliente(cedula);

        return resultado;
    }
    public boolean registrarCliente(Cliente cliente){
        return daoCliente.agregarCliente(cliente);
    }
    public boolean actualizarCliente(Cliente cliente){
        return daoCliente.actualizarCliente(cliente);
    }
    
    
    private static Controlador instancia;

    public static Controlador getInstancia() {
        return instancia == null ? instancia = new Controlador() : instancia;
    }
    BancoDAO bd = new BancoDAO();
    UsuarioDAO daoUsuario = new UsuarioDAO();
    ClienteDAO daoCliente = new ClienteDAO();
}
