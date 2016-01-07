<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OSDEPYM</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link href="css/jquery-ui.css" rel="stylesheet">
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
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
<script>
	$(function() {
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
	});
</script>
<link rel="stylesheet" href="css/style.css" />

<script>
	$(function() {
		$("#fechaDesde").datepicker();
		$("#fechaDesde").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$("#fechaDesde").datepicker("option", "dateFormat", "dd/mm/yy");
		$("#fechaHasta").datepicker();
		$("#fechaHasta").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$("#fechaHasta").datepicker("option", "dateFormat", "dd/mm/yy");
		$('#horaDesde').timepicker();
		$('#horaHasta').timepicker();
		$("#horaDesde").timepicker("option", "timeFormat", "hh:mm");
		$("#horaHasta").timepicker("option", "timeFormat", "hh:mm");

	});
</script>
<style>
.ui-datepicker table tr {

font-size: 10px;
}
.ui-timepicker
table tr {

font-size: 10px;
}
</style>
</head>
<body>
<s:hidden name="exportado" />
	<s:if test="exportado == 1" >
	<div id="dialog-message" title="Exportacion exitosa">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> La exportacion se ha realizado exitosamente.
				
		</p>
	</div>
	</s:if>
	<div id="header-smart">
		<div id="masthead">
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora">
				<p>Sistema de Administraci&oacute;n de Turnos</p>
			</div>

			<div id="estadisticas-content">

				<div id="estadisticas-title">
					<h2>Estad&iacute;sticas</h2>
				</div>
				<form action="">
					<s:hidden name="perfil" />
					Fecha Desde: <input placeholder="  dd/mm/aaaa" type="text"
						id="fechaDesde" name="fechaDesde">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					Fecha Hasta: <input placeholder="  dd/mm/aaaa" type="text"
						id="fechaHasta" name="fechaHasta">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:if test="perfil == 1 || perfil == 3">
						Hora Desde: <input placeholder="  hh" type="text" id="horaDesde"
							name="horaDesde" width="50%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									Hora Hasta: <input placeholder="  hh" type="text"
							id="horaHasta" name="horaHasta" width="50%">
					</s:if>

					<div id="estadisticas">
						<ul>
							<li><s:submit align="center" cssClass="links-footer"
									key="reporte.turnos" action="clientesAtendidosPuesto"
									method="clientesAtendidosPuesto" /></li>
							<s:if test="perfil == 1 || perfil == 3">

								<li><s:submit align="center" cssClass="links-footer"
										key="turnos.por.usuario" action="clientesAtendidosPorUsuarios"
										method="clientesAtendidosPorUsuarios" /></li>
								<li><s:submit align="center" cssClass="links-footer"
										key="turnos.por.sector" action="clientesAtendidosPorSector"
										method="clientesAtendidosPorSector" /></li>
								<li><s:submit align="center" cssClass="links-footer"
										key="turnos.por.sucursal" action="clientesAtendidosPorSector"
										method="clientesAtendidosPorSector" /></li>
								<li><s:submit align="center" cssClass="links-footer"
										key="total.espera.sector" action="clientesEnEsperaPorHora"
										method="clientesEnEsperaPorHora" /></li>
								<li><s:submit align="center" cssClass="links-footer"
										key="total.sucursal" action="clientesAtendidosPorSector"
										method="clientesAtendidosPorSector" /></li>

							</s:if>
						</ul>
					</div>
				</form>
			</div>

			<div id="admin-footer">
				<div id="usuario-footer-tres">
					<div class="numero">
						Usuario:
						<s:property value="nomUsuario" />
						&nbsp;&nbsp;&nbsp;&nbsp;Puesto:
						<s:property value="puesto" />
					</div>
					<div class="numero">
						IP:
						<s:property value="ipv4" />
					</div>
				</div>

				<div id="menu-footer-tres">
					<ul>
						<li><a class="links-footer" href="volver.action">Llamador</a></li>
						<li><a class="links-footer" href="desconectar.action">Cerrar
								Sesi&oacute;n</a></li>
					</ul>
				</div>

				<div id="sector-footer-tres">
					<div class="numero">
						Sector:
						<s:property value="nomSector" />
					</div>
					<ul><div id="reloj" align="right" style="font-size: 20px;"></div></ul>
				</div>
			</div>
		</div>
	</div>


</body>
</html>