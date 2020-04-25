
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="banco.backend.estructuras.Moneda"%>
<%@page import="banco.backend.estructuras.Cuenta"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>


<%
    List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
    List<Cuenta> cuentasV = (List<Cuenta>) request.getAttribute("cuentasV");
    Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
    Integer idDeposito = (Integer) request.getAttribute("idDeposito");
    BigDecimal monto = (BigDecimal) request.getAttribute("monto");
    String descripcion = (String) request.getAttribute("descripcion");
    String textoError = (String) request.getAttribute("textoError");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transferencia remota</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link rel="stylesheet" href="/portal/css/componentes/formulario.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>

    <jsp:include page="/presentation/Header.jsp" />

    <div class="contenido">
        <div class="formulario">
            <h6>Transferencia Remota</h6>
            <%if (request.getAttribute("exitoso") != null) {%>
            <div class="exitoso">
                <label>Transferencia realizada.</label>
            </div>
            <%}%>
            <%if (textoError != null) {%>
            <div class="erroneo">
                <label><%=textoError%></label>
            </div>
            <%}%>
            <form action="/portal/cliente/cuentas/transferencia" method="post">
                <%if (cuenta == null) {%>
                <div class="campo-entrada" >
                    <a>Cuenta: </a>
                    <select required name="cuenta">
                        <option value=""  disabled selected>Seleccione una cuenta</option>
                        <%for (Cuenta cuentav : cuentas) {%>
                        <option value="<%=cuentav.getIdCuenta()%>">
                            <%=cuentav.getIdCuenta()%> (<%=cuentav.getMoneda()%>)
                        </option>
                        <%}%>
                    </select>
                </div>
                <button class="submit">Seleccionar Cuenta</button>
                <%}%>
                <%else {%>
                <div class="campo">
                    <label>
                        Cuenta de Origen:
                        <input type="text"
                               value="<%=cuenta.getIdCuenta()%> (<%=cuenta.getMoneda()%>)"
                               disabled>
                    </label>
                    <label>
                        Monto de la cuenta:
                        <input type="text" 
                               value="<%=cuenta.getSaldo()%>"
                               disabled>
                    </label>
                </div>
                <input type="hidden" name="cuenta" value="<%=cuenta.getIdCuenta()%>">

                <div class="campo-entrada" >
                    <input 
                        type="text"
                        list="listaID"
                        id="idDepositado" 
                        name="idDepositado" 
                        value="<%=idDeposito == null ? "" : idDeposito%>"  
                        pattern="\d+" 
                        title="Ingrese una cuenta destino valida" 
                        placeholder=" " required>
                    <label for="idDepositado">cuenta a depositar</label>
                    <datalist id="listaID">
                        <%for (Cuenta cuentav : cuentas) {%>
                        <%if (cuentav.getIdCuenta() != cuenta.getIdCuenta()) {%>
                        <option value="<%=cuentav.getIdCuenta()%>">Cuenta Propia (<%=cuentav.getMoneda()%>)</option>
                        <%}%>
                        <%}%>
                        <%for (Cuenta cuentav : cuentasV) {%>
                        <%if (cuentav.getIdCuenta() != cuenta.getIdCuenta()) {%>
                        <option value="<%=cuentav.getIdCuenta()%>">Cuenta Vinculada (<%=cuentav.getMoneda()%>)</option>
                        <%}%>
                        <%}%>
                    </datalist>
                </div>
                <div class="campo-entrada" >
                    <input type="number" id="monto" name="monto"  step="any"
                           value="<%=monto == null ? "" : monto%>"  
                           placeholder=" " required>
                    <label for="monto">monto</label>
                </div>
                <div class="campo-entrada" >
                    <input type="text" id="descripcion" name="descripcion" 
                           value="<%=descripcion == null ? "" : descripcion%>"  
                           placeholder=" " required>
                    <label for="descripcion">descripcion</label>
                </div>

                <button class="submit">Seleccionar Cuenta</button>
                <%}%>

            </form>
        </div>
    </div>
    <%@ include file="/presentation/Footer.jsp" %>
</html>


