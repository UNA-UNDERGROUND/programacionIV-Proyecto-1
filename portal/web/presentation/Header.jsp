<%@page import="banco.backend.estructuras.Usuario"%>
<% Usuario usuario = (Usuario) session.getAttribute("usuario");%>

<header>
    <div class="logo">
        <img src="/images/logo.png" alt="logo">
        <div class="overlay">
            <div>
                <a href="/">Inicio</a>
            </div>
        </div>
    </div>

    <nav>
        <ul>
            <%if (usuario != null) {%>
            <li><a href="#">Ver Cuentas</a></li>
            <%}%>
        </ul>
    </nav>
    <%if (usuario == null) {%>
            <a href="/login/show"><button>Iniciar Sesion</button></a>
        <%} else {%>
            <a href="/logout"><button>Cerrar Sesion</button></a>
     <%}%>
</header>