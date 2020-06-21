<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Control de Inventario</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Incluimos la vista parcial la cual contiene las url de las librerias 
             correspondientes a estilos y js
        -->
        <%@include file="WEB-INF/Vistas-Parciales/css-js.jspf" %>
        
    </head>
    <body>
        <!-- Vistar parciales de la parte superior de nuestra aplicación -->
        <%@include file="WEB-INF/Vistas-Parciales/encabezado.jspf" %>
        
        <!-- Todo el contenido de la aplicación -->
        <h3>¡BIENVENID@S!</h3>
        
        <!-- Vistar parciales de la parte inferior de nuestra aplicación -->
        <%@include file="WEB-INF/Vistas-Parciales/pie.jspf" %>
    </body>
</html>
