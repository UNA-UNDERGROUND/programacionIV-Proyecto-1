<%@page import="banco.backend.estructuras.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%Cliente cliente = (Cliente)session.getAttribute("cliente");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/presentation/Head.jsp" %>
        <title>Banco SWBC</title>
    </head>
    <body>
        <%@ include file="/presentation/Header.jsp" %>
        <div class="contenido centrado">
            <%if(cliente==null){%>
            <h1>Banco SWBC</h1>
            <%}else{%>
            <h1>Bienvenido: <%=cliente.getNombre()%> <%=cliente.getApellidos()%> </h1>
            <%}%>
        </div>
        
        
        
        <%@ include file="/presentation/Footer.jsp" %>
    </body>
</html>
