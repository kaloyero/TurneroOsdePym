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
<script src="js/jquery.js"></script>
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
				+ "  " + h + ":" + m + ":" + s;
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
	$(document).ready(function() {
		refreshTurnos();
		setInterval(function() {
			refreshTurnos()
		}, 3000);
		function refreshTurnos() {
			
			$.ajax({
				type : 'POST',
				url : "http://localhost:8080/Login/enEspera.action",
				data:{"usuario":$("#id_user").val()},
				success : function(data) {
					//nombreSector
					console.log("DAT2a",data["sectoresEspera"])
					$(".numerosEsperaSectores").empty()
					for (key in data["sectoresEspera"]) {
						//console.log("SE",data["sectoresEspera"][key]["habilitado"])
						if (data["sectoresEspera"][key]["nomSector"] == $("#nomSector").val()){
							$("#numerosEspera").text(data["sectoresEspera"][key]["nrosEspera"])
						}else{
							
							$(".numerosEsperaSectores").append("<p>"+data["sectoresEspera"][key]["codSector"]+" "+data["sectoresEspera"][key]["nrosEspera"]+"</p>")
							
						}
					}
				}
			});

		}
	});
</script>
<script>
	$(function() {
		$("#dialog-message1").dialog({
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
	});
</script>

</head>
<body>
	<s:hidden name="id_user" />
	<s:hidden name="letra_sector" />
	<s:hidden name="nomSector" />
	<s:hidden name="puesto" />
	<s:hidden name="perfil" />
	<s:hidden name="nro_sector" />
	<s:hidden name="ipv4" />
	<s:hidden name="nomUsuario" />
	<s:hidden name="nrosEspera" id="nrosEspera" />
	<s:hidden name="contraCambiada" />
	<s:if test="contraCambiada == 1">
		<div id="dialog-message1" title="Cambiar Contraseña">
			<p>
				<span class="ui-icon ui-icon-circle-check"
					style="float: left; margin: 0 7px 50px 0;"></span> La contraseña se
				ha actualizado exitosamente.

			</p>
		</div>
	</s:if>

	<div id="header-smart">
		<div id="masthead">
			<!-- Logo -->
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora">
				<p>Sistema de Administraci&oacute;n de Turnos</p>
			</div>

			<div id="admin-content">
				<div id="columna-00">
					<div class="numero">
						Sector:
						<s:property value="nomSector" />
					</div>
					<div class="dates" id="nrosEspera">
						Hay <strong id="numerosEspera"><s:property value="nrosEspera" /></strong> turnos en
						espera.
					</div>
				</div>
				<div id="columna-01">
					<a id="siguiente" class="btn-admin" href="llamarTurno.action">Siguiente</a>
				</div>
				<div id="columna-02">
					<div id="admin-central-box">
						<div class="llamado">Llamado</div>
						<div class="llamado-var">
							<s:property value="nroLlamado" />
						</div>
					</div>
				</div>
				<div id="columna-03">
					<a class="btn-admin" href="atendido.action">Terminar</a> <a
						class="btn-admin" href="cancelado.action">Cancelar</a>
				</div>
			</div>

			<div id="admin-footer">
				<div id="usuario-footer">
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
					<div class="numero">Turnos en espera:</div>
					<div class="numero numerosEsperaSectores">
						<s:iterator value="sectores" var="sector">
							<p  id=<s:property value="%{#sector.codSector}"/>>
								<s:property value="%{#sector.codSector}" />
								<s:property value="%{#sector.nrosEspera}"/>
							</p>
						</s:iterator>
					</div>
				</div>

				<div id="menu-footer">
					<ul>
						<li><s:a cssClass="links-footer" href="cambiarSector.action">Cambiar Sector</s:a></li>
						<li><a class="links-footer" href="generarEstadisticas.action">Estadisticas</a></li>
			<!-- 		<li><a class="links-footer" href="cambiarContrasena.action">Cambiar
								Contraseña</a></li> -->	
					</ul>
					<ul>
						<li><div id="reloj" align="right" style="font-size: 20px;" /></li>
					</ul>
					<ul>
						<li><div align="right">
								<a class="links-footer" href="desconectar">Cerrar
									Sesi&oacute;n</a>
							</div></li>
					</ul>


				</div>
			</div>
		</div>
	</div>


</body>
</html>