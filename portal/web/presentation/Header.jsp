<%@page import="banco.backend.estructuras.Usuario"%>
<% Usuario usuario=  (Usuario) session.getAttribute("usuario");%>

<header>
    <div class="logo">
        <span>Proyecto 1</span>
    </div> 
    <div class="menu">
        <ul> 
              <li>
                <a href="/portal">Inicio</a>
              </li>
              <%if(usuario==null){%>
                <li>
                  <a href="/portal/login/show">Iniciar Sesion</a>
                </li>       
                <%}else{%>
                <li>
                  <a href="/portal/logout">Cerrar Sesion</a>
                </li>   
                <%}%>
            </ul>
    </div>
  </header>          

