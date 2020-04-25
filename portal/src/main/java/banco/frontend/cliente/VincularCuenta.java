/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.frontend.cliente;

import banco.backend.Controlador;
import banco.backend.estructuras.Cliente;
import banco.backend.estructuras.Cuenta;
import banco.backend.estructuras.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jonguz
 */
@WebServlet(name = "vincularCuentas", urlPatterns = {"/cliente/cuentas/vincular"})
public class VincularCuenta extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String viewUrl = "";
        if (validarSesion(request)) {
            switch (request.getServletPath()) {
                case "/cliente/cuentas/vincular":
                    viewUrl = vincularCuenta(request);
                    break;
            }
        } else {
            viewUrl = "/logout";
        }

        request.getRequestDispatcher(viewUrl).forward(request, response);
    }

    public String vincularCuenta(HttpServletRequest request) {
        Controlador c = Controlador.getInstancia();
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        Cliente cliente = c.recuperarDatosPersonales(u.getCedula());
        Integer idCuenta;
        Cuenta cuenta;

        try {
            idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
            cuenta = c.recuperarCuenta(idCuenta);
        } catch (NumberFormatException ex) {
            cuenta = null;
            idCuenta = null;
        }
        request.setAttribute("cuenta", cuenta);
        if (cuenta == null && idCuenta != null) {
            request.setAttribute("textoError", "esta cuenta no es valida");
        } else if (cuenta != null
                && cuenta.getCedula() == cliente.getCedula()) {
            request.setAttribute("textoError", "no se pueden vincular cuentas del mismo cliente");
            request.removeAttribute("cuenta");
        } else if (request.getParameter("confirmar") != null) {
            if (!Controlador.getInstancia().agregarCuentaVinculada(cliente, cuenta)) {
                request.setAttribute("textoError", "no se pudo vincular la cuenta");
            } else {
                request.setAttribute("completado", "");
            }
        }

        return "/presentation/cliente/vincularCuenta.jsp";
    }

    public void vincular(HttpServletRequest request) {

    }

    public boolean validarSesion(HttpServletRequest request) {
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        return u != null && !u.esAdministrativo();
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
