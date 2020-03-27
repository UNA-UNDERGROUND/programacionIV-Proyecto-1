<%@page import="banco.backend.estructuras.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%Cliente cliente = (Cliente) session.getAttribute("cliente");%>
<% Usuario usuario = (Usuario) session.getAttribute("usuario");%>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/presentation/Head.jsp" %>
        <title>Banco PSB</title>
    </head>

    <body>
        <jsp:include page="/presentation/Header.jsp" />
        <div class="contenido">

            <div>
                <%if (usuario == null) {%>
                <h1>Bienvendio al Banco PSB</h1>
                <%} else {%>
                <%if (usuario.esAdministrativo()) {%>
                <h1>DashBoard Administrativo</h1>
                <%} else {%>
                <h1>Bienvenido: <%=cliente.getNombre()%> <%=cliente.getApellidos()%></h1>
                <%}%>
                <%}%>

            </div>

        </div>



        <%@ include file="/presentation/Footer.jsp" %>
    </body>

</html>