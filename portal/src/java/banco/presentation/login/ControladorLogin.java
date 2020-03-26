/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.presentation.login;

import banco.backend.Controlador;
import banco.backend.estructuras.Cliente;
import banco.backend.estructuras.Usuario;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "AccountLoginController", urlPatterns = {"/login","/login/show", "/logout"})
public class ControladorLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        request.setAttribute("credenciales", new Credenciales());

        String viewUrl="";
        switch (request.getServletPath()) {
            case "/login/show":
                viewUrl = this.show(request);
                break;
            case "/login":
                viewUrl = this.login(request);
                break;
            case "/logout":
                viewUrl = this.logout(request);
                break;
        }
        request.getRequestDispatcher(viewUrl).forward(request, response);
    }

    boolean validarCampos(HttpServletRequest request) {
        Map<String, String> errores= new HashMap();
        if (request.getParameter("usuario").isEmpty() ) {
            errores.put("usuario", "cedula requerida");
        }
        else{
            try{
                Integer.parseInt(request.getParameter("usuario"));
            }
            catch(NumberFormatException e){
                errores.put("usuario", "formato de cedula incorrecto");
            }
        }
        if (request.getParameter("pass").isEmpty()) {
            errores.put("pass", "contraseña requerida");
        }
        request.setAttribute("errores", errores);
        return errores.isEmpty();
    }

    public String show(HttpServletRequest request) {
        return this.showAction(request);
    }

    private String login(HttpServletRequest request) {
        try {
            if (this.validarCampos(request)) {
                this.updateModel(request);
                return this.loginAction(request);
            } else {
                request.setAttribute("textoError", "Verifique la informacion de los datos");
                return "/presentation/login/View.jsp";
            }
        } catch (Exception e) {
            return "/presentation/Error.jsp";
        }
    }

    public String logout(HttpServletRequest request){
        return this.logoutAction(request);
    }

    public String showAction(HttpServletRequest request) {
        Credenciales model = (Credenciales) request.getAttribute("credenciales");
        HttpSession session = request.getSession(true);
        model.reset();
        return "/presentation/login/View.jsp";
    }

    public String loginAction(HttpServletRequest request) {
        Credenciales credenciales = (Credenciales) request.getAttribute("credenciales");
        Controlador controlador = Controlador.getInstancia();
        HttpSession session = request.getSession(true);
        String viewUrl="";
        try {
            Usuario usuario = controlador.login(credenciales.getCurrent());
            session.setAttribute("usuario", usuario);

            if(usuario.esAdministrativo()){
                viewUrl = "/admin";
            }
            else{
                Cliente cliente = controlador.recuperarDatosPersonales(usuario);
                session.setAttribute("cliente", cliente);
                viewUrl = "/";
            }
            
        } catch (Exception ex) {
            request.setAttribute("textoError", "Usuario y/o clave incorrectos");
            viewUrl = "/login/show";
        }
        return viewUrl;
    }

    public String logoutAction(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.removeAttribute("usuario");
        session.invalidate();
        return "/";   
    }
    
    void updateModel(HttpServletRequest request) {
        Credenciales model = (Credenciales) request.getAttribute("credenciales");

        model.getCurrent().setCedula(Integer.parseInt(request.getParameter("usuario")));
        model.getCurrent().setPass(request.getParameter("pass"));
        //las casillas sin marcar no son enviadas
        if(request.getParameterValues("admin")!=null){
            model.getCurrent().setAdministrativo(true);
        }
          
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
