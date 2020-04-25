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

/**
 *
 * @author jonguz
 */
@WebServlet(name = "TransferenciaCliente", urlPatterns = {"/cliente/cuentas/transferencia"})
public class Transferencia extends HttpServlet {

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
        String viewUrl = "/presentation/Error.jsp";
        try {
            if (validarSesion(request)) {
                switch (request.getServletPath()) {
                    case "/cliente/cuentas/transferencia":
                        viewUrl = procesarTramite(request);
                        break;
                }
            } else {
                viewUrl = "/portal/logout";
            }
        } catch (Exception ex) {
            viewUrl = "/presentation/Error.jsp";
        }

        request.getRequestDispatcher(viewUrl).forward(request, response);
    }

    private String procesarTramite(HttpServletRequest request) {
        generarAtributos(request);
        return procesarMovimiento(request);
    }

    private String procesarMovimiento(HttpServletRequest request) {
        if (request.getAttribute("idDepositado") != null
                && realizarTransaccion(request)) {
            request.setAttribute("exitoso", true);
        }
        return "/presentation/cliente/transferencia.jsp";
    }

    public boolean realizarTransaccion(HttpServletRequest request) {
        BigDecimal monto = (BigDecimal) request.getAttribute("monto");
        String descripcion = (String) request.getAttribute("descripcion");
        Cuenta cuentaOrigen = ((Cuenta) request.getAttribute("cuenta"));
        Usuario creds = (Usuario) request.getSession().getAttribute("usuario");
        Integer cuentaDestino = (Integer) request.getAttribute("idDepositado");

        return Controlador.getInstancia().agregarTransferencia(cuentaOrigen, cuentaDestino, monto, descripcion, creds);
    }

    public void generarAtributos(HttpServletRequest request) {
        Controlador c = Controlador.getInstancia();
        Cuenta cuenta;
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        Cliente cliente = c.recuperarDatosPersonales(u.getCedula());
        Object res[] = c.recuperarCuentas(cliente);

        List<Cuenta> cuentas = Arrays.asList((Cuenta[]) res[0]);
        request.setAttribute("cuentas", cuentas);

        res = c.recuperarCuentasVinculadas(cliente);
        List<Cuenta> cuentaV = Arrays.asList((Cuenta[]) res[0]);
        request.setAttribute("cuentasV", cuentaV);

        try{
            Integer idCuenta = Integer.parseInt(request.getParameter("cuenta"));
            cuenta = c.recuperarCuenta(idCuenta);
        }
        catch(NumberFormatException ex){
            cuenta = null;
        }
        
        request.setAttribute("cuenta", cuenta);
        
    }

    public boolean validarCampos(HttpServletRequest request) {
        Map<String, String> errores = new HashMap();

        if(request.getParameter("cuenta") != null &&request.getAttribute("cuenta") == null){
            errores.put("cuenta","la cuenta ingresada no es valida");
        }
        
        request.setAttribute("errores", errores);
        return errores.isEmpty();
    }

    public boolean validarSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return !usuario.esAdministrativo();
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
