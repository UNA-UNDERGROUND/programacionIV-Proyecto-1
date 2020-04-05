
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
        String textoError = (String) request.getAttribute("textoError");
        Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
    %>


    <div class="contenido">
        <div class="formulario">
            <h6>Realizar Movimiento</h6>

            <form action="/portal/admin/Movimiento" method="post">
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
                <%if (request.getAttribute("exitoso") != null) {%>
                <div class="exitoso">
                    <label>Movimiento Procesado.</label>
                </div>
                <%}%>


                <button class="submit">---</button>
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

