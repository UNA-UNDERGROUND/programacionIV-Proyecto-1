
<%@page import="banco.backend.Controlador"%>
<%@page import="java.util.List"%>
<%@page import="banco.backend.estructuras.Moneda"%>
<%@page import="banco.backend.estructuras.Cuenta"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

    <%
        List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
        Map<String, Moneda> monedas = (Map<String, Moneda>) request.getAttribute("monedas");
        Integer cedula = 0;
        Cliente cliente = null;
        Integer idCuenta = 0;
        Cuenta cuenta = (Cuenta) request.getAttribute("cuenta");
        if (request.getAttribute("cedula") != null) {
            Object valor = request.getAttribute("cedula");
            cedula = (Integer) valor;
        }
        if (cuenta != null) {
            cliente = Controlador.getInstancia().recuperarDatosPersonales(cuenta.getCedula());
        }

        String textoError = (String) request.getAttribute("textoError");
        Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
    %>


    <div class="contenido">
        <div class="formulario">

            <%if (cuentas != null && cuenta == null) {%>
            <h6>Realizar Movimiento</h6>
            <form action="/portal/admin/Movimiento" method="post">

                <%if (textoError != null) {%>
                <div class="erroneo">
                    <label><%=textoError%></label>
                    <%if (errores != null) {%>
                    <%for (String error : errores.values()) {%>
                    <label><%=error%></label>
                    <%}%>
                    <%}%>
                </div>
                <%}%>
                <%if (request.getAttribute("exitoso") != null) {%>
                <div class="exitoso">
                    <label>Movimiento Procesado.</label>
                </div>
                <%}%>

                <div class="campo-entrada" 
                     <%=erroneo("moneda", errores)%>">
                    <a>Seleccione la cuenta: </a>
                    <select required name="idCuenta">

                        <option value=""  disabled selected>Seleccione una cuenta</option>
                        <%for (Cuenta seleccionado : cuentas) {%>
                        <option value="<%=seleccionado.getIdCuenta()%>"><%=seleccionado.getIdCuenta()%> (<%=seleccionado.getMoneda()%>)</option>
                        <%}%>

                    </select>
                </div>


                <button class="submit">Seleccionar Cuenta</button>
            </form>
            <%}%>
            <% else if (cuenta == null) {%>
            <h6>Recuperar Cuenta</h6>
            <div style="
                 display: flex;
                 justify-content: center;">
                <form action="/portal/admin/Movimiento" id="recuperarCuenta" style="margin: 0px 5px;">
                    <div class="campo-entrada <%=erroneo("idCuenta", errores)%>">
                        <input type="text" id="idCuenta" name="idCuenta" 
                               value="<%=idCuenta == 0 ? "" : idCuenta%>"  
                               pattern="\d+" title="Ingrese una cuenta valida sin guiones y/o espacios" placeholder=" " required>
                        <label for="idCuenta">numero de cuenta</label>
                    </div>
                    <button class="submit">Recuperar Cuentas</button>
                </form>
                <form action="/portal/admin/Movimiento" style="margin: 0px 5px;">

                    <div class="campo-entrada <%=erroneo("cedula", errores)%>" >
                        <input type="text" id="cedula" name="cedula" 
                               value="<%=cedula == 0 ? "" : cedula%>"  
                               pattern="\d+" title="Ingrese una cedula valida sin guiones y/o espacios" placeholder=" " required>
                        <label for="cedula">Cedula</label>
                    </div>

                    <button class="submit" >Recuperar por cedula</button>
                </form>


            </div>
            <%}%>
            <%else {%>
            <h6>Informacion de la cuenta</h6>
            <div>
                <div>
                    <a>Propietario de la cuenta: <%=cliente.getNombre() + " " + cliente.getApellidos()%></a>
                </div>
                <div>
                    <a>Numero de cuenta: <%=cuenta.getIdCuenta()%></a>
                </div>
                <div>
                    <a>Moneda de la cuenta: <%=cuenta.getMoneda()%></a>
                </div>
            </div>
            <h6>Detalles del deposito</h6>
            <form action="/portal/admin/Movimiento" style="margin: 0px 5px;">

                <div class="campo-entrada <%=erroneo("tipoTramite", errores)%>" >
                    <label>Tipo de Tramite</label>
                    <p>
                        <input type="radio" name = "tipoTramite" value="Deposito" checked="checked"/>
                        <input type="radio" name = "tipoTramite" value="Retiro"/>
                        <input type="radio" name = "tipoTramite" value="Movimiento"/>
                    </p>

                </div>

            </form>


            <%}%>

        </div>

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
