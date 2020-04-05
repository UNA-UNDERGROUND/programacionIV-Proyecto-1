/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.frontend.admin;

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
@WebServlet(name = "MovimientoAdmin", urlPatterns = {"/admin/Movimiento"})
public class Movimiento extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String viewUrl;
        try {
            if(validarSesion(request)){

            switch (request.getServletPath()) {
                case "/admin/Movimiento/":
                    viewUrl = procesarMovimiento(request);
                break;
                default:
                    viewUrl = "/presentation/Error.jsp";
                    break;
            }
            }
            else{
                viewUrl = "/portal/logout";
            }
        } catch (Exception ex) {
            viewUrl = "/presentation/Error.jsp";
        }

        request.getRequestDispatcher(viewUrl).forward(request, response);
    }

    private String procesarMovimiento(HttpServletRequest request) {
        generarAtributos(request);
        if (request.getParameter("deposito") != null) {

        } else {

        }
        return "/presentation/administrador/Movimiento.jsp";
    }
    
    public void generarAtributos(HttpServletRequest request){
        
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
