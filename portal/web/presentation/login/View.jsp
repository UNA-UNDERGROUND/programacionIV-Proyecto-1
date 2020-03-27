<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="banco.presentation.login.Credenciales"%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <!DOCTYPE html>
        <html>

        <head>

            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Login</title>
            <%@ include file="/presentation/Head.jsp" %>
                <link rel="stylesheet" href="/portal/css/componentes/login-form.css">
                <meta name="viewport" content="width=device-width, initial-scale=1">
        </head>

        <body>
            <%@ include file="/presentation/Header.jsp" %>
            <% Credenciales model= (Credenciales) request.getAttribute("credenciales"); %>
            <%String  textoError = (String) request.getAttribute("textoError"); %>
            <%Map<String, String>  errores = (Map<String, String>) request.getAttribute("errores"); %>
            
                <div class="contenido centrado">
                    <div class="login-form">
                        <h6>Iniciar sesion</h6>

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
                            
                            <div class="campo-entrada <%=erroneo("usuario",errores)%>">
                                <input type="text" id="cedula" name="usuario" pattern="\d+" title="Ingrese una cedula valida sin guiones y/o espacios" required>
                                <label for="cedula">Cedula</label>
                            </div>
                            <div class="campo-entrada <%=erroneo("pass",errores)%>">
                                <input type="password" id="contraseña" name="pass" required>
                                <label for="contraseña">Contraseña</label>
                            </div>
                            <div class="campo-entrada">
                                <input type="checkbox" id="login-admin" value="login-admin" name="admin">
                                <label for="login-admin">Login administrativo</label>
                            </div>
                            <button class="submit">Iniciar sesion</button>
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