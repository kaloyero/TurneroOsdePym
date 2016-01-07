<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
<title>Modulo Administracion Osdepym</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <meta http-equiv="expires" content=<%= new java.util.Date() %>>
    <meta http-equiv="no-cache">
		<link rel="stylesheet" href="css/style.css" />
		<script>
    history.forward();
</script>
	</head>
	
	<body>
		<div id="header-wrapper" style="text-align: center">
				<div id="header">
					<!-- Logo -->
					<h1><img src="images/osdepym-logo.png"></h1>
					
					<!-- Intro -->
					<p class="ticket-message" style="font-weight: 300;font-size: 22px;
color: #5d5d5d;
font-family: 'Source Sans Pro';">Modulo Administracion -Central de Estadisticas Sistema de Administraci&oacute;n de Turnos</p>
					
					<div class="login">
						<form action="login.action" method="POST">
							<s:textfield name="username" placeholder="Usuario" cssClass="usuario"></s:textfield>
							<s:password name="password" placeholder="Contraseña" cssClass="pass"></s:password>
							<s:submit method="login" cssClass="btn" value="Ingresar"></s:submit>
						</form>
					</div>
				</div>
		</div>
	</body>
</html>