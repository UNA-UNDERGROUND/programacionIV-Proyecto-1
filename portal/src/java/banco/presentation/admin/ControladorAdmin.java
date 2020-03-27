/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.presentation.admin;

import banco.presentation.cliente.*;
import banco.presentation.login.Credenciales;
import banco.backend.Controlador;
import banco.backend.estructuras.Usuario;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jonguz
 */
@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/AbrirCuenta/show"})
public class ControladorAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String viewUrl = "";
        switch (request.getServletPath()) {
            case "/admin/AbrirCuenta/show":
                viewUrl = this.showAbrirCuenta(request);
                break;
        }
        request.getRequestDispatcher(viewUrl).forward(request, response);
    }

    public String showAbrirCuenta(HttpServletRequest request) {
        return this.showAbrirCuentaAction(request);
    }

    public String showAbrirCuentaAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String direccion;
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario.esAdministrativo()) {
                direccion =  "/presentation/administrador/AbrirCuenta.jsp";
            }
            else{
                direccion = "/portal/logout";
            }
        } catch (Exception ex) {
            direccion = "/presentation/error.jsp";
        }
        return direccion;
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
