<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>OSDEPYM</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript">
	function startTime() {
		today = new Date();
		h = today.getHours();
		m = today.getMinutes();
		s = today.getSeconds();
		d = today.getDate();
		mm = 1+today.getMonth();
		y = today.getFullYear();
		m = checkTime(m);
		s = checkTime(s);
		document.getElementById('reloj').innerHTML =d+"/"+mm+"/"+y+"  "+ h + ":" + m + ":" + s;
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
</head>
<body>
	<div id="header-smart">
		<div id="masthead">
			<!-- Logo -->
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora">
				<p>Sistema de Administraci&oacute;n de Turnos</p>
			</div>

			<div id="administrador-content">

				<div id="columnas-administrador">
					<div id="tabla-administrador">
						<div class="datos-administrador">
							<table datapagesize="10">
								<tr>
									<td>Numero Turno</td>
									<td>Usuario</td>
									<td>C&oacute;digo Sector</td>
									<td>Sector</td>
									<td>Atendido</td>
									<td>Fecha</td>
								</tr>
								<s:iterator value="turnos" var="turno">
									<tr>
										<td><s:property value="%{#turno.numero_turno}" /></td>
										<td><s:property value="%{#turno.nomUsuario}" /></td>
										<td><s:property value="%{#turno.id_cod_sector}" /></td>
										<td><s:property value="%{#turno.nomSector}" /></td>
										<td><s:property value="%{#turno.atendido}" /></td>
										<td><s:property value="%{#turno.fecha_ticket}" /></td>
									</tr>
								</s:iterator>
							</table>
						</div>

					</div>
					<div id="botones-administrador">
						<a class="btn-admin" href="exportar.action">Exportar</a> <br></br>
						<a class="btn-admin" href="volverEstadisticas.action">Volver</a>
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
				<div align="right" id="menu-footer">
					<ul><div id="reloj" align="right" style="font-size: 20px;"></div></ul>
				</div>

			</div>
		</div>
	</div>


</body>
</html>