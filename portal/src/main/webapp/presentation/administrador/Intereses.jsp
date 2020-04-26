<!DOCTYPE html>
<%@page import="banco.backend.estructuras.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/presentation/Head.jsp" %>
        <title>Banco PSB</title>
    </head>

    <body>
        <jsp:include page="/presentation/Header.jsp" />
        <div class="contenido">
            <%if (request.getAttribute("completado") == null) {%>
            <div class = "formulario">
                <h6>Confirmar Proceso por lotes</h6>
                <form method="post" style="margin: 0px 5px;">
                    <input type="hidden" name="procesar" value="">
                    <button class="submit" >Acreditar Interes</button>
                </form>
            </div>
            <%}%>
            <%else {%>
            <div>
                <h1>Proceso Terminado</h1>
            </div>
            <%}%>

        </div>



        <%@ include file="/presentation/Footer.jsp" %>
    </body>

</html>