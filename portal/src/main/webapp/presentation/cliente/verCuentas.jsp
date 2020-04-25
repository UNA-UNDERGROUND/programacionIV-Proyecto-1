
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
    Map<String, Moneda> monedas = (Map<String, Moneda>) request.getAttribute("monedas");

    List<Cuenta> cuentasV = (List<Cuenta>) request.getAttribute("cuentasV");
    Map<String, Moneda> monedasV = (Map<String, Moneda>) request.getAttribute("monedasV");

    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");

%>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaccion de cuenta</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link rel="stylesheet" href="/portal/css/componentes/formulario.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <jsp:include page="/presentation/Header.jsp" />

    <div class="contenido">

        <%if (movimientos == null) {%>
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
                    <td><%=m.esDeposito()?"Deposito":"Retiro"%></td>
                    <td><%=m.getFechaDeposito()%></td>
                    <td><%=m.getMonto()%></td>
                    <td><%=m.getDescripcion()%></td>
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

