
<%@page import="java.sql.Date"%>
<%@page import="banco.backend.estructuras.Movimiento"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="banco.backend.Controlador"%>
<%@page import="java.util.List"%>
<%@page import="banco.backend.estructuras.Moneda"%>
<%@page import="banco.backend.estructuras.Cuenta"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    Cliente cliente = (Cliente) request.getAttribute("cliente");

    List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
    List<Cuenta> cuentasV = (List<Cuenta>) request.getAttribute("cuentasV");

    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
    Date inicio = (Date) request.getAttribute("inicio");
    Date fin = (Date) request.getAttribute("fin");

%>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Cuentas</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link rel="stylesheet" href="/portal/css/componentes/formulario.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <jsp:include page="/presentation/Header.jsp" />

    <div class="contenido">

        <%if (request.getParameter("cuenta") == null) {%>
        <div class="formulario">
            <table>
                <caption>Cuentas de <%=cliente.getNombre() + " " + cliente.getApellidos()%></caption>
                <tr>
                    <th>ID</th>
                    <th>Moneda</th>
                    <th>Limite Transferencia</th>
                    <th>Saldo</th>
                </tr>
                <%for (Cuenta cuenta : cuentas) {%>
                <tr>
                    <td>
                        <a href="?cuenta=<%=cuenta.getIdCuenta()%>">
                            <%=cuenta.getIdCuenta()%>
                        </a></td>
                    <td><%=cuenta.getMoneda()%></td>
                    <td><%=cuenta.getLimiteTransferencia()%></td>
                    <td><%=cuenta.getSaldo()%></td>
                </tr>
                <%}%>
            </table>
            <table>
                <caption>Cuentas Vinculadas</caption>
                <tr>
                    <th>ID</th>
                    <th>Moneda</th>
                    <th>Limite Transferencia</th>
                    <th>Saldo</th>
                </tr>
                <%for (Cuenta cuenta : cuentasV) {%>
                <tr>
                    <td><%=cuenta.getIdCuenta()%></td>
                    <td><%=cuenta.getMoneda()%></td>
                    <td><%=cuenta.getLimiteTransferencia()%></td>
                    <td><%=cuenta.getSaldo()%></td>
                </tr>
                <%}%>
            </table>
        </div>
        <%}%>
        <%else if (request.getParameter("confirmar") == null) {%>

        <div class="formulario">
            <h6>Seleccione un rango</h6>
            <form method="post">
                <div class="campo-entrada">
                    <input type="date" name="inicio" id="inicio" required>
                    <label for="inicio">Fecha de Inicio</label>
                </div>
                <div class="campo-entrada">
                    <input type="date" name="fin" value="<%=fin == null ? "" : fin%>" required>
                    <label for="fin">Fecha final</label>
                </div>
                <input type="hidden" name="confirmar" value="">
                <input type="hidden" name="cuenta" value="<%=request.getParameter("cuenta")%>"> 
                <button class="submit">Seleccionar Rango</button>
            </form>
            <form method="post">
                <input type="hidden" name="confirmar" value="">
                <input type="hidden" name="cuenta" value="<%=request.getParameter("cuenta")%>"> 
                <button class="submit">Mostrar todos los movimientos</button>
            </form>
        </div>

        <%}%>
        <%else {%>
        <div class="formulario">
            <table>
                <caption>Movimientos de Cuenta <%=request.getParameter("cuenta")%></caption>
                <tr>
                    <th>ID</th>
                    <th>Tipo</th>
                    <th>Fecha</th>
                    <th>Monto</th>
                    <th>Descripcion</th>
                </tr>
                <%for (Movimiento m : movimientos) {%>
                <tr>
                    <td><%=m.getIdTransaccion()%></td>
                    <td><%=m.esDeposito() ? "Deposito" : "Retiro"%></td>
                    <td><%=m.getMonto()%></td>
                    <td><%=m.getDescripcion()%></td>
                    <td><%=m.getFechaDeposito()%></td>
                </tr>
                <%}%>
            </table>

        </div>
        <%}%>
    </div>


    <%@ include file="/presentation/Footer.jsp" %>


</html>


<%!
    private String erroneo(String campo, Map<String, String> errores) {
        if ((errores != null) && (errores.get(campo) != null)) {
            return "erroneo";
        } else {
            return "";
        }
    }
%> 

