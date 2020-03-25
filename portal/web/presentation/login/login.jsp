<%-- 
    Document   : login
    Created on : 25 mar. 2020, 12:14:24
    Author     : jonathan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="/css/login-dark.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
     <div class="login-form">
      <h6>Iniciar sesion</h6>

      <form action="login">
        <div class="textbox">
          <input type="text" placeholder="Identificador de Usuario">
          <span class="check-message hidden">Requerido</span>
        </div>

        <div class="textbox">
          <input type="password" placeholder="Contraseña">
          <span class="check-message hidden">Requerido</span>
        </div>

        <div class="opciones">
          <label class="remember-me">
            <span class="checkbox">
              <input type="checkbox">
              <span class="checked"></span>
            </span>
            <a>Recordarme</a>
          </label>

          <a href="#">Login Administrativo</a>
        </div>
        
        <input type="submit" value="Iniciar Sesion" class="login-btn" disabled>
        <button class="google-login" formaction="/google-login">
          Iniciar sesion con google
        </button>
      </form>

    </div>
</html>

