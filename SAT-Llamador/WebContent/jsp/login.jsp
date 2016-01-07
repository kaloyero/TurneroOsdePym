<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>OSDEPYM</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" href="css/style.css" />
		<script src="js/jquery.js"></script>
		 <script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery.placeholder.js" type="text/javascript" charset="utf-8"></script>
	</head>
	
	<body>
		<div id="header-wrapper">
				<div id="header">
					<!-- Logo -->
					<h1><img src="images/osdepym-logo.png"></h1>
					<!-- Intro -->
					<p class="ticket-message">Sistema de Administraci&oacute;n de Turnos</p>
					
					<div class="login">
						<form id="myForm" action="pantalla.action" method="post">
							<s:textfield name="username" placeholder="Usuario" cssClass="usuario"></s:textfield>
							<s:password name="password" placeholder="Contraseña" cssClass="pass"></s:password>
							<s:submit method="execute" cssClass="btn" value="Ingresar"></s:submit>
						</form>
					</div>
				</div>
		</div>
	</body>
</html>