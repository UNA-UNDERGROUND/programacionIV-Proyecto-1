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
import java.util.ArrayList;
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
        String viewUrl = "/presentation/Error.jsp";
        try {
            if (validarSesion(request)) {
                String res = request.getServletPath();
                switch (request.getServletPath()) {
                    case "/admin/Movimiento":
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
        Object tt = request.getParameter("tipoTramite");
        if (validarCampos(request) && request.getParameter("tipoTramite") != null) {
            switch (request.getParameter("tipoTramite")) {
                case "Deposito":
                    return procesarDeposito(request);
                case "Retiro":
                    return procesarRetiro(request);
                case "Movimiento":
                    return procesarMovimiento(request);
            }
        }
        return "/presentation/administrador/Movimiento.jsp";
    }

    private String procesarDeposito(HttpServletRequest request) {
        if (realizarMovimiento(request, true)) {
            request.setAttribute("exitoso", true);
        }
        return "/presentation/administrador/Movimiento.jsp";
    }

    private String procesarRetiro(HttpServletRequest request) {
        if (realizarMovimiento(request, false)) {
            request.setAttribute("exitoso", true);
        }
        return "/presentation/administrador/Movimiento.jsp";
    }

    private String procesarMovimiento(HttpServletRequest request) {
        if(request.getAttribute("idDepositado")!= null 
                && realizarTransaccion(request)){
            request.setAttribute("exitoso", true);
        }
        return "/presentation/administrador/Movimiento.jsp";
    }

    public boolean realizarMovimiento(HttpServletRequest request, boolean esDeposito) {
        BigDecimal monto = (BigDecimal) request.getAttribute("monto");
        String descripcion = (String) request.getAttribute("descripcion");
        Integer idCuenta = ((Cuenta) request.getAttribute("cuenta")).getIdCuenta();
        Usuario creds = (Usuario) request.getSession().getAttribute("usuario");

        return Controlador.getInstancia().agregarMovimiento(idCuenta, esDeposito, monto, descripcion, creds);
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
        Controlador controlador = Controlador.getInstancia();
        Integer cedula;
        Integer idCuenta;
        try {
            cedula = Integer.parseInt(request.getParameter("cedula"));
        } catch (NumberFormatException ex) {
            cedula = null;
        }
        try {
            idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
        } catch (NumberFormatException ex) {
            idCuenta = null;
        }

        if (cedula != null && idCuenta == null) {
            request.setAttribute("cedula", cedula);
            Cliente cliente = controlador.recuperarDatosPersonales(cedula);
            if (cliente != null) {
                Object[] resultado = controlador.recuperarCuentas(cliente);
                List<Cuenta> cuentas = new ArrayList<>();
                Map<String, Moneda> monedas = (Map<String, Moneda>) resultado[1];

                cuentas.addAll(Arrays.asList((Cuenta[]) resultado[0]));

                request.setAttribute("cuentas", cuentas);
                request.setAttribute("monedas", monedas);
                request.setAttribute("cliente", cliente);
            }
        }
        if (idCuenta != null) {
            Cuenta cuenta = Controlador.getInstancia().recuperarCuenta(idCuenta);

            BigDecimal monto;
            String descripcion = request.getParameter("descripcion");
            try {
                monto = new BigDecimal(request.getParameter("monto"));
            } catch (Exception ex) {
                monto = null;
            }

            request.setAttribute("descripcion", descripcion);
            request.setAttribute("monto", monto);


            if (request.getParameterMap().containsKey("tipoTramite")
                    && request.getParameter("tipoTramite").equals("Movimiento")) {
                Integer idDepositado;
                try {
                    idDepositado = Integer.parseInt(request.getParameter("idDepositado"));
                } catch (NumberFormatException ex) {
                    idDepositado = null;
                }

                request.setAttribute("idDepositado", idDepositado);
                
                if(idDepositado == null){
                    request.setAttribute("requiereInfoTransaccion", true);
                }
            }
            request.setAttribute("cuenta", cuenta);
        }
    }

    public boolean validarCampos(HttpServletRequest request) {
        Map<String, String> errores = new HashMap();
        Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
        Cliente cliente = (Cliente) request.getAttribute("cliente");

        if (cuenta == null) {
            if (request.getParameter("cedula") != null && cliente == null) {
                errores.put("cedula", "el cliente no tiene cuentas asignadas");
            } else if (request.getParameter("idCuenta") != null) {
                errores.put("idCuenta", "la cuenta indicada en el sistema no existe");
            }
        } else {
            if (request.getParameterMap().containsKey("tipoTramite")) {
                BigDecimal monto = (BigDecimal) request.getAttribute("monto");
                String descripcion = (String) request.getAttribute("descripcion");

                try {
                    if (monto.compareTo(BigDecimal.ZERO) < 0) {
                        errores.put("monto", "el monto no puede ser menor a 0");
                    }
                } catch (Exception ex) {
                    errores.put("monto", "no se ingreso un monto valido");
                }

                try {
                    if (descripcion.isEmpty()) {
                        errores.put("descripcion", "la descripcion no puede estar vacia");
                    }
                } catch (Exception ex) {
                    errores.put("descripcion", "la descripcion no puede estar vacia");
                }

            }

        }

        request.setAttribute("errores", errores);
        return errores.isEmpty();
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
