<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<title>OSDEPYM</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="stylesheet" href="css/style.css" />
<script src="js/jquery.js"></script>
<script type="text/javascript">
	function startTime() {
		today = new Date();
		h = today.getHours();
		m = today.getMinutes();
		d = today.getDate();
		mm = 1+today.getMonth();
		y = today.getFullYear();
		m = checkTime(m);
		document.getElementById('reloj').innerHTML =d+"/"+mm+"/"+y+"  "+ h + ":" + m ;
		t = setTimeout('startTime()', 500);
	}
	function checkTime(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
	window.onload = function() {
		startTime();
	}
</script>

<script>
	$(document)
			.ready(
					function() {
						setInterval(function() {
							$
							.ajax({
								type : 'POST',
								url : "refreshTablero.action",
								success : function(data) {
									//nombreSector
									console.log("DAT2a", data, data["datos"]);
									$(".datos-tablero").empty();
									$(".datos-tablero").append(
											"<tr>"+
											"<td>"+ "Sector" +"</td>" + 
											"<td>"+ "Clientes en espera" +"</td>"+
											"<td>"+ "Tiempo maximo de espera (hs:min)" +"</td>"+
											"<td>"+ "Tiempo Promedio de atencion (hs:min)" +"</td>"+
											"</tr>"		
									);
									for (key in data["datos"]) {
										$(".datos-tablero").append(
												"<tr>"+
												"<td>"+ data["datos"][key]["sector"]+"</td>" + 
												"<td>"+ data["datos"][key]["clientesEspera"]+"</td>"+
												"<td>"+ data["datos"][key]["tiempoEspera"]+"</td>"+
												"<td>"+ data["datos"][key]["tiempoAtencion"]+"</td>"+
												"</tr>"
												
												);
										}
									}
							});
						}, 3000);
					});
</script>

</head>
<body>
	<div id="header-smart">
		<div id="masthead">
			<!-- Logo -->
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora">
				<p>Sistema de Administraci&oacute;n de Turnos</p>
				<div id="reloj" align="right" style="font-size: 20px;"></div>
			</div>

			<div id="administrador-content">

				<div id="columnas-administrador">
					<div>
					<br></br>
					<div id="botones-administrador">
						<a class="btn-admin" href="volverEstadisticas.action">Volver</a>
					</div>
						<div class="datos-tablero">
												<strong>Cargando</strong>
						
						</div>
						
					<br></br>
					</div>
				</div>
			</div>

			<div id="admin-footer">

				<div id="menu-footer">
					<ul>
						<li><a class="links-footer" href="desconectar.action">Cerrar
								Sesi&oacute;n</a></li>
					</ul>
				</div>

			</div>
		</div>
	</div>


</body>
</html>