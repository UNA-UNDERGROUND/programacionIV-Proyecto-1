/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.backend;

import banco.backend.db.BancoDAO;
import banco.backend.estructuras.Cliente;
import banco.backend.estructuras.Cuenta;
import banco.backend.estructuras.Moneda;
import banco.backend.estructuras.Usuario;
import banco.backend.estructuras.dao.ClienteDAO;
import banco.backend.estructuras.dao.CuentaDAO;
import banco.backend.estructuras.dao.MonedaDAO;
import banco.backend.estructuras.dao.UsuarioDAO;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public class Controlador {

    private Controlador() {

    }

    public Usuario login(Usuario credenciales) {
        Usuario res = daoUsuario.recuperarUsuario(credenciales);

        if (res != null && !res.equals(credenciales)) {
            res = null;
        }

        return res;
    }
    
    public Usuario recuperarUsuario(int cedula){
        return daoUsuario.recuperarUsuario(new Usuario(cedula));
    }
    public boolean registrarUsuario(Usuario cliente){
        return daoUsuario.agregarUsuario(cliente);
    }

    public Cliente recuperarDatosPersonales(int cedula) {
        Cliente resultado = daoCliente.recuperarCliente(cedula);

        return resultado;
    }

    public boolean registrarCliente(Cliente cliente) {
        return daoCliente.agregarCliente(cliente);
    }

    public boolean actualizarCliente(Cliente cliente) {
        return daoCliente.actualizarCliente(cliente);
    }

    public Cuenta recuperarCuenta(int idCuenta) {
        return daoCuenta.recuperarCuenta(idCuenta);
    }

    public Object[] recuperarCuentas(Cliente cliente) {
        Cuenta[] cuentas 
                = daoCuenta.recuperarCuentas(cliente.getCedula());
        Map<String, Moneda> monedas = new HashMap<>();
        for (Cuenta cuenta : cuentas) {
            String codigo = cuenta.getMoneda();
            if (!monedas.containsKey(codigo)) {
                monedas.put(
                        codigo,
                        daoMoneda.recuperarMoneda(codigo)
                );
            }
        }
        return new Object[]{cuentas, monedas};
    }

    public Object[] recuperarCuentasVinculadas(Cliente cliente) {
        Cuenta[] cuentas 
                = daoCuenta.recuperarCuentasVinculadas(cliente.getCedula());
        Map<String, Moneda> monedas = new HashMap<>();
        for (Cuenta cuenta : cuentas) {
            String codigo = cuenta.getMoneda();
            if (!monedas.containsKey(codigo)) {
                monedas.put(
                        codigo,
                        daoMoneda.recuperarMoneda(codigo)
                );
            }
        }
        return new Object[]{cuentas, monedas};
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        return daoCuenta.agregarCuenta(cuenta);
    }
    public boolean agregarCuentaVinculada(Cliente cliente, Cuenta cuenta){
        return daoCuenta.agregarCuentaVinculada(cliente.getCedula(), cuenta.getIdCuenta());
    }
    
    public Moneda recuperarMoneda(String codigo){
        return daoMoneda.recuperarMoneda(codigo);
    }

    public Moneda[] recuperarMonedas() {
        return daoMoneda.recuperarMonedas();
    }

    private static Controlador instancia;

    public static Controlador getInstancia() {
        return instancia == null ? instancia = new Controlador() : instancia;
    }
    BancoDAO bd = new BancoDAO();
    UsuarioDAO daoUsuario = new UsuarioDAO();
    ClienteDAO daoCliente = new ClienteDAO();
    CuentaDAO daoCuenta = new CuentaDAO();
    MonedaDAO daoMoneda = new MonedaDAO();
}
