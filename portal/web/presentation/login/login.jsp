<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="/portal/css/componentes/login-form.css">
        <link rel="stylesheet" href="/portal/css/dark-style.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <div class="login-form">
        <h6>Iniciar sesion</h6>

        <form action="login">

            <div class="campo-entrada">
                <input type="text" id="cedula" required>
                <label for="cedula">Cedula</label>
            </div>
            <div class="campo-entrada">
                <input type="password" id="contraseña" required>
                <label for="contraseña">Contraseña</label>
            </div>


            <div class="campo-entrada">
                <input type="checkbox" id="login-admin" value="login-admin">
                <label for="login-admin">Login administrativo</label>
            </div>

            <button class="submit">Iniciar sesion</button>
        </form>

    </div>

    </html>