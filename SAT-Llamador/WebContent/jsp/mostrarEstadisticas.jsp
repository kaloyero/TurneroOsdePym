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
<script type="text/javascript">
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
			<p style="position: absolute; left: 6%; top: 1%;">
				<s:property value="reporte" />
				-
				<s:property value="fecha" />
			</p>
			<div id="fecha-hora">
				<p>Sistema de Administraci&oacute;n de Turnos</p>
				<div id="reloj" align="right" style="font-size: 20px;"></div>
			</div>

			<div style="position: absolute; top: 100px;"
				id="administrador-content">

				<div id="columnas-administrador">
					<div class="datos-administrador">
						<table>
							<tr style="position: absolute; width: 90.2%; top:3.5%;">
								<td width="10%">Fecha Turno</td>
								<td width="9.8%">Horario Turno</td>
								<td width="9.85%">Horario Atenci&oacute;n</td>
								<td width="9.85%">Horario Fin</td>
								<td width="9.85%">Sector</td>
								<td width="9.85%">C&oacute;digo Sector</td>
								<td width="9.85%">Numero Turno</td>
								<td width="9.85%">Atendido</td>
								<s:if test="noAtendidos == 0">
									<td width="11%">Usuario</td>
								</s:if>
							</tr>
						</table>
						</div>
						<div style="width: 90%" class="datos-administrador-1" id="tabla-administrador">
							<table>
								<s:iterator value="turnos" var="turno">
									<tr style="">
										<td width="10%"><s:property
												value="%{#turno.fecha_ticket}" /></td>
										<td width="10%"><s:property value="%{#turno.horaTurno}" /></td>
										<td width="10%"><s:property
												value="%{#turno.horaAtencion}" /></td>
										<td width="10%"><s:property value="%{#turno.horaFin}" /></td>
										<td width="10%"><s:property value="%{#turno.nomSector}" /></td>
										<td width="10%"><s:property
												value="%{#turno.id_cod_sector}" /></td>
										<td width="10%"><s:property
												value="%{#turno.numero_turno}" /></td>
										<td width="10%"><s:property value="%{#turno.atendido}" /></td>
										<s:if test="noAtendidos == 0">
											<td width="10%"><s:property value="%{#turno.nomUsuario}" /></td>
										</s:if>
									</tr>
								</s:iterator>
							</table>
						</div>
					

				</div>
				<div id="columna-01">
					<a class="btn-admin" href="exportar.action" >Exportar</a>
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

			

		</div>
	</div>
	</div>


</body>
</html>