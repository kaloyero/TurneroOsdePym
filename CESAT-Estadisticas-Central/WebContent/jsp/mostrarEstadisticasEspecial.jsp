<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<title>OSDEPYM</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
 <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <meta http-equiv="expires" content=<%= new java.util.Date() %>>
    <meta http-equiv="no-cache">
    	<script>
    history.forward();
</script>
<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript">
idleTime = 0;
	function startTime() {
		today = new Date();
		h = today.getHours();
		m = today.getMinutes();
		s = today.getSeconds();
		d = today.getDate();
		mm = 1 + today.getMonth();
		y = today.getFullYear();
		m = checkTime(m);
		s = checkTime(s);
		document.getElementById('reloj').innerHTML = d + "/" + mm + "/" + y
				+ "  " + h + ":" + m ;
		t = setTimeout('startTime()', 500);
	}
	function checkTime(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
	var idleInterval = setInterval(timerIncrement, 30000); // 1 minute

    //Zero the idle timer on mouse movement.
    $(this).mousemove(function (e) {
        idleTime = 0;
    });
    $(this).keypress(function (e) {
        idleTime = 0;
    });
    
    function timerIncrement() {
    	 idleTime = idleTime + 1;
         if (idleTime > 1) { // 1 minutes
         	$('#myForm').submit();
         }
        	}
	window.onload = function() {
		startTime();
	}
</script>
<style>
.especial {
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #4dbfb7
 ), color-stop(1, #4dbfb7)) !important;
   color:white !important;


}
.datos-administrador-1 table {
  
  height: 0px !important;
  }
  p {
	font-size: 15px !important;
}
</style>
</head>
<body>
	<div id="header-smart">
		<div id="masthead">
			<!-- Logo -->
			<img src="images/osdepym-logo.png">
			<p style="position: absolute; left: 6%; top: 1%;    font-size: 25px !important;;margin-top:50px">
				Resumen Sucursales
				-
				<s:property value="fecha" />
			</p>
	<p style="position: absolute; left: 6%; top: 15px;font-weight: bold;">Ultima Comprobacion-<s:property value="ultimaFechaCompobacion" /></p>
			<div id="fecha-hora">
				<p>Central de Estadistica -Sistema de Administraci&oacute;n de Turnos</p>
				<div id="reloj" align="right" style="font-size: 20px;"></div>
			</div>

			<div style="position: absolute; top: 100px;"
				id="administrador-content">

				<div id="columnas-administrador">
					
						<div style="width: 90%" class="datos-administrador-1" id="tabla-administrador-especial">
							<table>
							<tr style="">
									<td width="10%" class="especial">Codigo Sucursal</td>
									<td width="10%" class="especial">Nombre Sucursal</td>
												<td width="10%" class="especial">Codigo Sector</td>
									<td width="10%" class="especial">Nombre Sector</td>
									<td width="10%" class="especial">Cantidad Usuarios Distintos</td>
									
												<td width="10%" class="especial">Cant. Turnos Emitidos</td>
											<td width="10%" class="especial">Cant. Turnos Llamados</td>
												
												<td width="10%" class="especial">Cant. Turnos Atendidos</td>
												
										<td width="10%" class="especial">Cant Turnos Cancelados</td>
														<td width="10%" class="especial">Tiempo Promedio Espera</td>
														<td width="10%" class="especial">Tiempo Maximo Espera</td>
										<td width="10%" class="especial">Tiempo Promedio Atencion</td>
										<td width="10%" class="especial">Tiempo Maximo Atencion</td>
								
								
										
										
									
									</tr>
								<s:iterator value="turnos" var="turno">
									<tr style="">
									<td width="10%"><s:property
												value="%{#turno.codSucursal}" /></td>
									<td width="10%"><s:property
												value="%{#turno.nombreSucursal}" /></td>
												<td width="10%"><s:property
												value="%{#turno.id_cod_sector}" /></td>
									<td width="10%"><s:property
												value="%{#turno.nomSector}" /></td>
							<td width="10%"><s:property value="%{#turno.cantidadDistintosUsuarios}" /></td>
												
												<td width="10%"><s:property value="%{#turno.cantidadTurnos}" /></td>
																						<td width="10%"><s:property value="%{#turno.cantidadLlamados}" /></td>
												
										<td width="10%"><s:property value="%{#turno.cantidadAtendidos}" /></td>
														<td width="10%"><s:property value="%{#turno.cantidadCancelados}" /></td>
										<td width="10%"><s:property value="%{#turno.tiempPromedioEsperaTexto}" /></td>
										<td width="10%"><s:property
												value="%{#turno.tiempMaximoEsperaTexto}" /></td>
										<td width="10%"><s:property value="%{#turno.tiempPromedioAtencionTexto}" /></td>
										<td width="10%"><s:property value="%{#turno.tiempMaximoAtencionTexto}" /></td>
										
								
										
										
									
									</tr>
								</s:iterator>
							</table>
						</div>
					

				</div>
				<div id="columna-01">
					<a class="btn-admin" href="exportarEspecial.action" >Exportar</a>
				</div>
				<div id="columna-03">
					<a class="btn-admin" href="volverEstadisticas.action" >Volver</a>
				</div>
				<div id="columna-03">
					<a style="position: absolute; right: 10%; top:90%;" class="links-footer" href="desconectar.action">Cerrar
							Sesión</a>
				</div>
			</div>
		</div>

		<div id="admin-footer">

							<div><s:form id="myForm" action="desconectar.action"></s:form></div>
			

		</div>
	</div>
	</div>


</body>
</html>