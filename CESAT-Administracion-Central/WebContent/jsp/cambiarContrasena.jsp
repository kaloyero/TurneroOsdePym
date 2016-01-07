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
		<link rel="stylesheet" href="css/style.css" />
	</head>
	
	<body>
		<div id="header-wrapper">
				<div id="header">
					<!-- Logo -->
					<h1><img src="images/osdepym-logo.png"></h1>
					
					<!-- Intro -->
					<p class="ticket-message">Sistema de Administraci&oacute;n de Turnos</p>
					
					<div class="login">
						<form action="actualizarContrasena.action">
							<s:password name="contrasenaAnterior" placeholder="Contraseña Anterior" cssClass="usuario"></s:password>
							<s:password name="contrasenaNueva" placeholder="Contraseña Nueva" cssClass="pass"></s:password>
							<s:password name="contrasenaRepetida" placeholder="Repetir Contraseña" cssClass="pass"></s:password>
							<s:submit method="actualizarContrasena" cssClass="btn" value="Actualizar"></s:submit>
							<s:submit action="volver" method="volver" cssClass="btn" value="Volver"></s:submit>
						</form>
					</div>
				</div>
		</div>
	</body>
</html>