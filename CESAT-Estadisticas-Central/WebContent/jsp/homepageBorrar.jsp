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
		d = today.getDate();
		mm = 1 + today.getMonth();
		y = today.getFullYear();
		m = checkTime(m);
		document.getElementById('reloj').innerHTML = d + "/" + mm + "/" + y
				+ "  " + h + ":" + m;
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
				url : "enEspera.action",
				//data:{"usuario":$("#id_user").val()},
				success : function(data) {
					//nombreSector
					console.log("DAT2a",data,data["sectoresEspera"])
					$(".numerosEsperaSectores").empty()
					for (key in data["sectoresEspera"]) {
						//console.log("SE",data["sectoresEspera"][key]["habilitado"])
						if (data["sectoresEspera"][key]["nomSector"] == $("#nomSector").val()){
							$("#numerosEspera").text(data["sectoresEspera"][key]["nrosEspera"])
						}else{if(data["sectoresEspera"][key]["nrosEspera"] != 0){
							$(".numerosEsperaSectores")
							.append(
									"<p>"
											+ data["sectoresEspera"][key]["nomSector"]
											+ ": "
											+ data["sectoresEspera"][key]["nrosEspera"]
											+ "</p>")

				}
						}
					}
				}
			});

		}
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
	<s:hidden name="llamado" id="llamado" />

	<div id="header-smart">
		<div id="masthead">
			<!-- Logo -->
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora">
				<p>Sistema de Administraci&oacute;n de Turnos</p>
				<div id="reloj" align="right" style="font-size: 20px;"></div>
			</div>
	<s:form id="admin-content">
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
		
					<s:submit id="siguiente"  cssStyle="position:absolute; left:4%;"  cssClass="btn-admin" action="llamarTurno" method="execute" value="Siguiente"></s:submit>
						
				</div>
				<div id="columna-02">
					<div style="position:relative; left:100.5%;" id="admin-central-box">
						<div class="llamado">Llamado</div>
						<div class="llamado-var">
							<s:property value="nroLlamado" />
						</div>
					</div>
				</div>
				<div id="columna-03">
									<s:submit id="terminar" cssStyle="position:absolute; right:4%;"  cssClass="btn-admin" action="atendido" value="Terminar"></s:submit>
									<s:submit id="cancelar" cssStyle="position:absolute; right:4%; top:300px;"  cssClass="btn-admin" action="cancelado" value="Cancelar"></s:submit>
				</div>
		</s:form>
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
								<s:property value="%{#sector.nomSector}" />
								<s:property value="%{#sector.nrosEspera}"/>
							</p>
						</s:iterator>
					</div>
				</div>

				<div id="menu-footer">
					<ul>
						<li><s:a cssClass="links-footer" href="cambiarSector.action">Cambiar Sector</s:a></li>
						<li><a class="links-footer" href="generarEstadisticas.action">Estadisticas</a></li>
						<li style="position: absolute; right: 50px; top: 480px;"><a class="links-footer" href="desconectar">Cerrar Sesi&oacute;n</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>


</body>
</html>