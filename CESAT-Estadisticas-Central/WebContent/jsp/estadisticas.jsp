<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OSDEPYM</title>
 <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <meta http-equiv="expires" content=<%= new java.util.Date() %>>
    <meta http-equiv="no-cache">
    	<script>
    history.forward();
</script>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link href="css/jquery-ui.css" rel="stylesheet">
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript">
idleTime = 0;
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
	function habilitarBotonForzar(){
		$("#botonForzar" ).prop("disabled",false)
		$("#botonForzar" ).css("color","#007a4d")
		$("#botonForzar" ).text("Actualizar Sucursales")
	}
	function deshabilitarBotonForzar(){
		$("#botonForzar" ).prop("disabled",true)
		$("#botonForzar" ).css("color","#B3A7A7")
		$("#botonForzar" ).text("Actualizando...")
	}
	
	window.onload = function() {
		startTime();
		$("#botonForzar" ).click(function() {

			deshabilitarBotonForzar()
			
		    alert("Se ha iniciando la actualización de sucursales...")
			$.ajax({
			    // la URL para la petición
			    url : '/CESAT-TurneroBatch/servicio/ejecutarAhora',
			    type : 'GET',
			 
			    // el tipo de información que se espera de respuesta
			    //dataType : 'json',
			 
			    // código a ejecutar si la petición es satisfactoria;
			    // la respuesta es pasada como argumento a la función
			    success : function(json) {
//			        alert("Sucursales actualizadas con Exito!")
			    },
			 
			    // código a ejecutar si la petición falla;
			    // son pasados como argumentos a la función
			    // el objeto de la petición en crudo y código de estatus de la petición
			    error : function(xhr, status) {
			    	habilitarBotonForzar()
			        alert('Error de comunicación, por favor intente nuevamente');
			    }
			});
			});
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
		console.log("ENTRAA",idleTime)
        if (idleTime > 1) { // 1 minutes
        	console.log("ENTRA LINK",$(".salir"))
        	//$('#myForm').submit();
           //$(".salir").trigger("click")
        	window.location.href="desconectar.action"
        }
        	}
</script>
<script>
	$(document)
			.ready(
					function() {
						var terminoAnterior=true;
						intervaloActualizacionBatch();


						function intervaloActualizacionBatch() {
							setInterval(function() {
								console.log("terminoanteriorIntervalo",terminoAnterior)
								
								if (terminoAnterior)
								 obtenerActualizacionBatch()
							}, 4000);
						}
						
						function obtenerActualizacionBatch() {
							terminoAnterior=false;
							$
									.ajax({
										 url : '/CESAT-TurneroBatch/servicio/obtenerEstadoBatch',
										    type : 'GET',
										success : function(data) {
											terminoAnterior=true;
											console.log("terminoanteriorSucccess",data)
											if (data == "A"){
												deshabilitarBotonForzar()
											} else {
												habilitarBotonForzar()
											}
											
										}

									})
						}


					});
</script>
<script>
	$(function() {
		$("#dialog-message").dialog({
			width : 600,
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
		$("#fechaDesde").datepicker("setDate", new Date());
		$("#fechaHasta").datepicker("setDate", new Date());

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
	<s:if test="exportado == 1">
		<div id="dialog-message" title="Exportacion exitosa">
			<p>
				<span class="ui-icon ui-icon-circle-check"
					style="float: left; margin: 0 7px 50px 0;"></span> La exportacion
				se ha realizado exitosamente. Ubicacion del archivo:
				<s:property value="rutaArchivo" />

			</p>
		</div>
	</s:if>
	<div id="header-smart">
		<div id="masthead">
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora">
				<p>Central Estadisticas -Sistema de Administraci&oacute;n de Turnos</p>
				<div id="reloj" align="right" style="font-size: 20px;"></div>
			</div>

			<div id="estadisticas-content">
<button id ="botonForzar" type="button" class="links-footer">Actualizar Sucursales</button>

				<div id="estadisticas-title">
					<h2>Estad&iacute;sticas</h2>
				</div>
				<form action="">
				<s:select label="Elegir Sucursal" style="
    margin-right: 15px;
"
		list="sucursales" listKey="id"  listValue="value" name="sucursalSeleccionada" 
		/>
		<s:hidden name="perfil" />
					Fecha Desde: <input placeholder="  dd/mm/aaaa" type="text"
						id="fechaDesde" name="fechaDesde">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	

	<div style="margin-left: 210px;
">
					Fecha Hasta: <input placeholder="  dd/mm/aaaa" type="text"
						id="fechaHasta" name="fechaHasta">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
					
					<div id="estadisticas" style="
    margin-top: 50px;
">
						<ul style="heigth: 100%">
								<li><s:submit align="center" cssClass="links-footer"
										key="reporte.resumenSucursal" action="resumenSucursales"
										method="resumenSucursales" formmethod="post" /></li>
								<li><s:submit align="center" cssClass="links-footer"
										key="turnos.por.sucursal" action="clientesAtendidosPorSector"
										method="clientesAtendidosPorSucursal" formmethod="post" /></li>
								<li><s:submit align="center" cssClass="links-footer"
										key="turnossucursales" action="todasSucursales"
										method="todasSucursales" formmethod="post" /></li>
								<li><s:submit align="center" cssClass="links-footer"
										key="turnos.no.llamados" action="clientesNoLlamados"
										method="clientesNoLlamados" formmethod="post" /></li>
						</ul>
					</div><font style="
    FTON: RED;
    font-weight: bold;
">
					Ultimo Chequeo de sucursales realizado:
						<s:property value="chequeoSucursales" />
						&nbsp;&nbsp;&nbsp;&nbsp;</font>
				</form>
			</div>

			<div id="admin-footer">
				<div id="usuario-footer-tres">
					<div class="numero">
						Usuario:
						<s:property value="nomUsuario" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					
				</div>

				<div id="menu-footer-tres">
					<ul>
						
						<li><a class="links-footer salir" href="desconectar.action">Cerrar
								Sesi&oacute;n</a></li>
					</ul>
				</div>
			

				<div id="sector-footer-tres">
					
				</div>
			</div>
		</div>
	</div>


</body>
</html>