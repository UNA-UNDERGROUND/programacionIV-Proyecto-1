<%@page import="banco.presentation.login.Credenciales"%>
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

        <body>
            <%@ include file="/presentation/Header.jsp" %>
            <% Credenciales credenciales= (Credenciales) request.getAttribute("credenciales"); %>
            <%String  textoError = (String) request.getAttribute("textoError"); %>
            <%Map<String, String>  errores = (Map<String, String>) request.getAttribute("errores"); %>
            
                <div class="contenido">
                    <div class="formulario">
                        <h6>Registrar Cuenta</h6>

                        <form action="/portal/login" method="post">
                            <%if(textoError!=null){%>
                            <div class="erroneo">
                                <label><%=textoError%></label>
                                <%if(errores!=null){%>
                                <%for(String error: errores.values()){%>
                                    <label><%=error%></label>
                                <%}}%>
                            </div>
                            <%}%>
                            
                            
                            
                            
                            
                            <div class="campo-entrada <%=erroneo("limite",errores)%>">
                                <input type="number" id="limite" name="limite" value="100000" placeholder=" " required>
                                <label for="limite">Limite transferencia</label>
                            </div>

                            <button class="submit">Crear Cuenta</button>
                        </form>

                    </div>

                </div>

                <%@ include file="/presentation/Footer.jsp" %>
        </body>

        </html>
        
        
 <%!
    private String erroneo(String campo, Map<String,String> errores){
      if ( (errores!=null) && (errores.get(campo)!=null) )
        return "erroneo";
      else
        return "";
    }

    
%> 