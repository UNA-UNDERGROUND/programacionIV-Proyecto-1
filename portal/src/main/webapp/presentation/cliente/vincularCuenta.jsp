<%@page import="banco.backend.estructuras.Cuenta"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>


<%
    String textoError = (String) request.getAttribute("textoError");
    Integer idCuenta = (Integer) request.getAttribute("idCuenta");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vincular Cuenta</title>
        <%@ include file="/presentation/Head.jsp" %>
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>

    <body>
        <jsp:include page="/presentation/Header.jsp" />


        <div class="contenido centrado">
            <div class="formulario">
                <h6>Vincular Cuenta</h6>
                <form action="/portal/cliente/cuentas/vincular" method="post">
                    <%if (request.getAttribute("completado") != null) {%>
                    <div class="exitoso">
                        <label>Se vinculo correctamente la cuenta</label>
                    </div>
                    <%}%>
                    <%if (textoError != null) {%>
                    <div class="erroneo">
                        <label><%=textoError%></label>
                    </div>
                    <%}%>
                    <%if (cuenta == null) {%>
                    <div class="campo-entrada">
                        <input type="text" id="idCuenta" name="idCuenta" 
                               value="<%=idCuenta == null ? "" : idCuenta%>"  
                               pattern="\d+" title="Ingrese una cuenta valida" placeholder=" " required>
                        <label for="idCuenta">idCuenta</label>
                    </div>
                    <button class="submit">Recuperar Cuenta</button>
                    <%}%>
                    <%else {%>
                    <div class="campo">
                        <label>
                            Cedula del propietario:
                            <input type="text"
                                   value="<%=cuenta.getCedula()%>"
                                   disabled>
                        </label>
                    </div>
                    <div class="campo">
                        <label>
                            Id de cuenta:
                            <input type="text"
                                   value="<%=cuenta.getIdCuenta()%> (<%=cuenta.getMoneda()%>)"
                                   disabled>
                        </label>
                    </div>
                    <input type="hidden" name="idCuenta" value="<%=cuenta.getIdCuenta()%>">
                    <input type="hidden" name="confirmar" value="">
                    <button class="submit">Confirmar Vinculacion</button>
                    <%}%>
                </form>
            </div>
        </div>

        <%@ include file="/presentation/Footer.jsp" %>
    </body>

</html>


