
<%@page import="java.util.List"%>
<%@page import="banco.backend.estructuras.Moneda"%>
<%@page import="banco.backend.estructuras.Cuenta"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link rel="stylesheet" href="/portal/css/componentes/formulario.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>


    <jsp:include page="/presentation/Header.jsp" />

    <%
        Cliente cliente = (Cliente) request.getAttribute("cliente");
        Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
        boolean cuentaRequerida = (Boolean) request.getAttribute("cuentaRequerida");
        String textoError = (String) request.getAttribute("textoError");
        Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
        List<Moneda> monedas = (List<Moneda>) request.getAttribute("monedas");
        String codigoMoneda = (String) request.getAttribute("moneda");
    %>


    <div class="contenido">
        <div class="formulario">
            <h6>Registrar Cuenta</h6>

            <form action="/portal/admin/AbrirCuenta" method="post">
                <%if (textoError != null) {%>
                <div class="erroneo">
                    <label><%=textoError%></label>
                    <%if (errores != null) {%>
                    <%for (String error : errores.values()) {%>
                    <label><%=error%></label>
                    <%}%>
                    <%}%>
                </div>
                <%}%>

                <div class="campo-entrada 
                     <%=erroneo("usuario", errores)%>">
                    <input type="text" id="cedula" name="cedula" 
                           value="<%=cuenta.getCedula() == 0 ? "" : cuenta.getCedula()%>"  
                           pattern="\d+" title="Ingrese una cedula valida sin guiones y/o espacios" placeholder=" " required>
                    <label for="cedula">Cedula</label>
                </div>

                <div class="campo-entrada 
                     <%=erroneo("limiteTransferencia", errores)%>">
                    <input type="number" id="limite" name="limite" value="<%=cuenta.getLimiteTransferencia()%>" placeholder=" " required>
                    <label for="limite">Limite transferencia</label>
                </div>

                <div class="campo-entrada" 
                     <%=erroneo("moneda", errores)%>">
                    <a>Moneda: </a>
                    <select required name="moneda">

                        <option value=""  disabled selected>Seleccione una moneda</option>
                        <%for (Moneda moneda : monedas) {%>
                        <%if (codigoMoneda.equals(moneda.getCodigo())) {%>
                        <option value="<%=moneda.getCodigo()%>" required><%=moneda.getNombre()%></option>
                        <%} else {%>
                        <option value="<%=moneda.getCodigo()%>"><%=moneda.getNombre()%></option>
                        <%}%>
                        <%}%>

                    </select>
                </div>

                <%if (cuentaRequerida && false) {%>
                <h6>Informacion de usuario</h6>
                <div class="campo-entrada 
                     <%=erroneo("nombre", errores)%>">
                    <input type="text" id="nombre" name="nombre" value="<%=cliente.getNombre()%>" placeholder=" " required>
                    <label for="limite">Nombre del Cliente</label>
                </div>

                <div class="campo-entrada 
                     <%=erroneo("apellidos", errores)%>">
                    <input type="text" id="apellidos" name="apellidos" value="<%=cliente.getApellidos()%>" placeholder=" " required>
                    <label for="apellidos">Apellidos</label>
                </div>

                <div class="campo-entrada 
                     <%=erroneo("numero", errores)%>">
                    <input type="text" id="numero" name="numero" value="<%=cliente.getNumero()%>" placeholder=" " required>
                    <label for="numero">Numero Telefonico</label>
                </div>

                <%}%>

                <button class="submit">Crear Cuenta</button>
            </form>

        </div>

    </div>

    <%@ include file="/presentation/Footer.jsp" %>


</html>


<%!
    private String erroneo(String campo, Map<String, String> errores) {
        if ((errores != null) && (errores.get(campo) != null)) {
            return "erroneo";
        } else {
            return "";
        }
    }
%> 

