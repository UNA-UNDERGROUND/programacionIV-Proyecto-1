/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.frontend.admin;

import banco.backend.Controlador;
import banco.backend.estructuras.Cliente;
import banco.backend.estructuras.Cuenta;
import banco.backend.estructuras.Moneda;
import banco.backend.estructuras.Usuario;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author jonguz
 */
@WebServlet(name = "AdminCrearCuentaController", urlPatterns = {"/admin/AbrirCuenta/show", "/admin/AbrirCuenta"})
public class ControladorAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String viewUrl = "";
        try {
            if (validarSesion(request)) {
                generarAtributos(request);
                switch (request.getServletPath()) {
                    case "/admin/AbrirCuenta":
                        viewUrl = this.abrirCuenta(request);
                        break;
                    case "/admin/AbrirCuenta/show":
                        viewUrl = this.abrirCuentaShow(request);
                        break;
                }
            }
        } catch (Exception ex) {
            viewUrl = "/presentation/Error.jsp";
        }

        request.getRequestDispatcher(viewUrl).forward(request, response);
    }

    public String abrirCuentaShow(HttpServletRequest request) {
        return this.abrirCuentaActionShow(request);
    }

    public String abrirCuenta(HttpServletRequest request) {
        if (validarCampos(request)) {
            return this.abrirCuentaAction(request);
        }
        return "presentation/administrador/AbrirCuenta.jsp";
    }

    public String abrirCuentaActionShow(HttpServletRequest request) {
        String direccion;
        try {
            direccion = "/presentation/administrador/AbrirCuenta.jsp";
        } catch (Exception ex) {
            direccion = "/presentation/error.jsp";
        }
        return direccion;
    }

    public String abrirCuentaAction(HttpServletRequest request) {
        Controlador controlador = Controlador.getInstancia();
        int cedula = Integer.parseInt(request.getParameter("cedula"));
        Cliente cliente = controlador.recuperarDatosPersonales(cedula);
        Usuario usuario = controlador.recuperarUsuario(cedula);
        if (cliente != null) {
            request.setAttribute("cuentaRequerida", (Boolean)false);
            String moneda = request.getParameter("moneda");
            int limiteDiario = Integer.parseInt(request.getParameter("limite"));
            Cuenta cuenta = new Cuenta(cedula, moneda, new BigDecimal(0), limiteDiario);

            request.setAttribute("cuenta", cuenta);
            request.setAttribute("moneda", moneda);
            if (controlador.agregarCuenta(cuenta)) {
                if (usuario == null) {
                    controlador.registrarUsuario(new Usuario(
                            cedula,
                            RandomStringUtils.randomAlphanumeric(8)
                    ));
                }
                request.setAttribute("exitoso", (Boolean)true);
                request.setAttribute("cliente", new Cliente());
                request.setAttribute("cuenta", new Cuenta());
                
                return "/presentation/administrador/AbrirCuenta.jsp";
            }
        } else {
            String nombre = (String) request.getParameter("nombre");
            String apellidos = (String) request.getParameter("apellidos");
            String numero = (String) request.getParameter("numero");
            
            boolean cuentaValida 
                    = nombre != null
                    & apellidos != null
                    & numero != null;
            
            nombre = nombre == null ? "" : nombre;
            apellidos = apellidos == null ? "" : apellidos;
            numero = numero == null ? "" : numero;
            cliente = new Cliente(cedula);
            cliente.setNombre(nombre);
            cliente.setApellidos(apellidos);
            cliente.setNumero(numero);
            request.setAttribute("cliente", cliente);
            if (cuentaValida) {
                if (controlador.registrarCliente(cliente)) {
                    request.setAttribute("cuentaRequerida", (Boolean) false);
                }
            } else {
                request.setAttribute("cuentaRequerida", (Boolean) true);
            }

        }
        return "/presentation/administrador/AbrirCuenta.jsp";
    }

    boolean validarCampos(HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();

        if ((Boolean) request.getAttribute("cuentaRequerida")) {
            if (request.getParameter("nombre").isEmpty()) {
                errores.put("nombre", "el nombre es un campo obligatorio");
            }
            if (request.getParameter("apellidos").isEmpty()) {
                errores.put("apellidos", "la apellidos es un campo obligatorio");
            }
            if (request.getParameter("numero").isEmpty()) {
                errores.put("numero", "se requiere de un numero telefonico");
            }
        }

        if (request.getParameter("cedula").isEmpty()) {
            errores.put("cedula", "la cedula es un campo obligatorio");
        } else {
            try {
                Integer.parseInt(request.getParameter("cedula"));
            } catch (NumberFormatException e) {
                errores.put("usuario", "formato de cedula incorrecto");
            }
        }
        if (request.getParameter("limite").isEmpty()) {
            errores.put("limite", "el limite de transferencia no puede estar en blanco");
        } else {
            try {
                Integer.parseInt(request.getParameter("limite"));
            } catch (NumberFormatException e) {
                errores.put("limite", "el limite de transferencia debe de ser un valor numerico");
            }
        }
        if (request.getParameter("moneda").isEmpty()) {
            errores.put("moneda", "no se indico una moneda");
        } else {
            Moneda moneda = Controlador.getInstancia().recuperarMoneda(request.getParameter("moneda"));
            if (moneda == null) {
                errores.put("moneda", "esta moneda no se encuentra disponible");
            }
        }

        request.setAttribute("errores", errores);
        return errores.isEmpty();
    }

    public void generarAtributos(HttpServletRequest request) {
        int cedula;
        if (request.getParameter("cedula") == null) {
            cedula = 0;
        } else {
            try {
                cedula = Integer.parseInt((String) (String) request.getParameter("cedula"));
            } catch (NumberFormatException ex) {
                cedula = 0;
            }
        }
        request.setAttribute("cedula", (Integer) cedula);
        if (request.getAttribute("cuentaRequerida") == null) {
            request.setAttribute("cuentaRequerida", (Boolean) false);
        }
        if (request.getAttribute("cuenta") == null) {
            Cuenta cuenta = new Cuenta(cedula);
            cuenta.setLimiteTransferencia(100000);
            request.setAttribute("cuenta", cuenta);
        }
        if (request.getParameter("moneda") == null) {
            request.setAttribute("moneda", " ");
        } else {
            request.setAttribute("moneda", request.getParameter("moneda"));
        }
        List<Moneda> monedas = Arrays.asList(Controlador.getInstancia().recuperarMonedas());
        request.setAttribute("monedas", monedas);

    }

    public boolean validarSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return usuario.esAdministrativo();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
