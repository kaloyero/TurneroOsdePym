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
	
	<body>
		<div id="header-wrapper">
				<div id="header">

					<h2>ERROR EN EL SISTEMA</h2>
					<p>Contacte al administrador</p>
					<p><strong><s:property value="errorMessage" /></strong></p>
					
				</div>
		</div>
	</body>
</html>