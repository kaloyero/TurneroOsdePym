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
<div id="header-wrapper">
<div id="header"><!-- Logo -->
<h1><img src="images/osdepym-logo.png"></h1>
<p>Seleccione el sector</p>

<div id="buttons"><s:iterator value="sectores" var="sector">
	<form action="cambioSector.action" method="POST"><s:submit value="%{#sector.NomSector}" align="center"
		cssClass="btn" /> <s:hidden name="id_user" /> <s:hidden
		name="letra_sector" /> <s:hidden name="perfil" /> <s:hidden
		name="nomSector" id="nomSector" /> <s:hidden name="puesto" /> <s:hidden
		name="nro_sector" id="nro_sector" /> <s:hidden name="nomUsuario" />
	</form>
</s:iterator></div>
</div>
</div>
</body>
</html>