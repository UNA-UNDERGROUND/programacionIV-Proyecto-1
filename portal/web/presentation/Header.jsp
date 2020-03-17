<%@page import="banca.logic.Usuario"%>
<% Usuario usuario=  null;/* null;*/  %>

<header>
    <div class="logo">
        <span>Proyecto 1</span>
    </div> 
    <div class="menu">
        <ul> 
              <li>
                <a href="/Proyecto 1/presentation/Index.jsp">Inicio</a>
              </li>
                        <% if (usuario!=null){ %>
                <li>
                  <a href="/Proyecto 1/presentation/cliente/cuentas/View.jsp">Cuentas</a>
                  <ul>  <!--submenu --> </ul>
                </li>                        
                <li >
                  <a  href="#">User:<%=usuario.getCedula()%></a>
                  <ul>  <!--submenu --> </ul>
                </li> 
                        <% } %>
                        <% if (usuario==null){%>
                <li>
                  <a href="/Proyecto 1/presentation/login/show">Login</a>
                </li>            
                        <% }%>             
            </ul>
    </div>
  </header>          

