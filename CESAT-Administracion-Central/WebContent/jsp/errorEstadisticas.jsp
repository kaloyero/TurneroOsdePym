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
		<link rel="stylesheet" href="css/style-error.css" />
	</head>
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
	
	<body>
		<div id="header-wrapper">
				<div id="header">

					<h2>Sistema de Administraci&oacute;n de Turnos</h2>
					
					<p><strong><s:property value="errorMessage" /></strong></p>
					
					<div id="buttons">
						<a class="btn" href="volverEstadisticas.action">Presione para continuar</a>
					</div>
				</div>
		</div>
	</body>
</html>