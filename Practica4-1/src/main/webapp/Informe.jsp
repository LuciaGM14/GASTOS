<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*, es.studium.MVC.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Informe de Compras</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div style="text-align: right; margin: 10px;">
    <a href="<%=request.getContextPath()%>/logout" class="btn btn-danger">Cerrar Sesión</a>
</div>
<h1>Informe de Compras</h1>

<form action="<%=request.getContextPath()%>/InformeServlet" method="post">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="mes">Seleccione el Mes:</label>
            <select class="form-control" name="mes" id="mes">
                <%-- Agregar opciones de los meses --%>
                <%
                    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
                    for (int i = 0; i < meses.length; i++) {
                %>
                        <option value="<%=i+1%>"><%=meses[i]%></option>
                <%
                    }
                %>
            </select>
        </div>
        <div class="form-group col-md-6">
            <label for="anio">Seleccione el Año:</label>
            <select class="form-control" name="anio" id="anio">
                <%-- Agregar opciones de los años --%>
                <%
                    Calendar cal = Calendar.getInstance();
                    int añoActual = cal.get(Calendar.YEAR);
                    for (int i = añoActual; i >= 2000; i--) {
                %>
                        <option value="<%=i%>"><%=i%></option>
                <%
                    }
                %>
            </select>
        </div>
    </div>
    <button type="submit" class="btn btn-warning">Generar Informe</button>
</form>

<!-- Tabla para mostrar las compras -->
<table class="table table-bordered">
    <thead>
        <tr>
            <th class="text-center">Fecha</th>
            <th class="text-center">Tienda</th>
            <th class="text-center">Importe</th>
        </tr>
    </thead>
    <tbody>
        <%-- Iterar sobre las compras para mostrarlas en la tabla --%>
        <%
            ArrayList<Compra> compras = (ArrayList<Compra>) request.getAttribute("compras");
            if (compras != null && !compras.isEmpty()) {
                for (Compra compra : compras) {
        %>
                    <tr>
                        <td><%=compra.getFechaCompra()%></td>
                        <td><%=compra.getNombreTienda()%></td>
                        <td><%=compra.getImporteCompra()%> €</td>
                    </tr>
        <%
                }
                // Obtener el total de las compras
                double total = (double) request.getAttribute("total");
        %>
                    <tr>
                        <td colspan="2" class="text-center"><strong>Total:</strong></td>
                        <td><strong><%=total%> €</strong></td>
                    </tr>
        <%
            } else {
        %>
                <tr>
                    <td colspan="3">No hay compras disponibles para este mes y año.</td>
                </tr>
        <%
            }
        %>
    </tbody>
</table>
<div class="text-center mt-3">
    <a href="<%=request.getContextPath()%>/NuevaCompra.jsp" class="btn btn-primary">Nueva Compra</a>
    <a href="<%=request.getContextPath()%>/PrincipalServlet" class="btn btn-primary">Volver a la Página Principal</a>
</div>

<!-- Bootstrap JS and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>

