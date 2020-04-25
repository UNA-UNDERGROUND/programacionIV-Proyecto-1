/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.frontend.cliente;

import banco.backend.Controlador;
import banco.backend.estructuras.Cliente;
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
@WebServlet(name = "ClientDashboardController", urlPatterns = {"/cliente/cuentas"})
public class VisorCuentas extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String viewUrl = "";
        if (validarSesion(request)) {
            switch (request.getServletPath()) {
                case "/cliente/cuentas":
                    viewUrl = verCuentas(request);
                    break;
            }
        }
        else{
            viewUrl = "/logout";
        }

        request.getRequestDispatcher(viewUrl).forward(request, response);
    }
    
    public String verCuentas(HttpServletRequest request){
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        Cliente c = Controlador.getInstancia().recuperarDatosPersonales(u.getCedula());
        
        request.setAttribute("cliente", c);
        
        return "/presentation/cliente/verCuentas.jsp";
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
