<%@page import="banco.backend.estructuras.Cliente"%>
<%@page import="banco.backend.estructuras.Usuario"%>
<%Cliente cliente = (Cliente) session.getAttribute("cliente");%>
<% Usuario usuario = (Usuario) session.getAttribute("usuario");%>



<header>
    <div class="logo">
        <img src="/portal/images/logo.png" alt="logo">
        <div class="overlay">
            <div>
                <a href="/portal">Inicio</a>
            </div>
        </div>
    </div>

    <nav>
        <ul>
            <%if (usuario != null) {%>
            <%if (usuario.esAdministrativo()) {%>
            <li><a href="/portal/admin/AbrirCuenta/show">Abrir Cuenta</a></li>
            <%}else{%>
            <li><a href="#">Ver Cuentas</a></li>
            <%}}%>
        </ul>
    </nav>
    <%if (usuario == null) {%>
            <a href="/portal/login/show"><button>Iniciar Sesion</button></a>
        <%} else {%>
            <a href="/portal/logout"><button>Cerrar Sesion</button></a>
     <%}%>
</header>