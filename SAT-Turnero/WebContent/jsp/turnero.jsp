<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>

<html>
<head>
<title>OSDEPYM</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="stylesheet" href="css/jquery-ui-smooth.css">
<link href="css/style.css" rel="stylesheet" id="style1">
<script src="js/jquery.js"></script>
 <link type="text/css" href="css/jquery.keypad.css" rel="stylesheet"> 
<script type="text/javascript" src="js/jquery.plugin.js"></script> 
<script type="text/javascript" src="js/jquery.keypad.js"></script>
 <script src="js/jquery-ui.js"></script>
 <script type="text/javascript" src="js/jquery.keypad-es.js"></script>

<style type="text/css">

 .ui-dialog{
   z-index: 10
  }
.keypad-key, .keypad-special { 
    width: 98px; height: 38px; padding: 5px; 
    color: black; font-size: 1.8em; font-weight: bold; } 

input[type='checkbox'] {
    display:none;
}
input[type='checkbox'] + label {
    display:inline-block;
    width:30px;
    height:30px;
    background-color:rgb(213, 213, 237);
}
input[type='checkbox']:checked + label {
    background-color:rgb(152, 208, 172);
}
.keypad-start, .keypad-end { background: #ff0; }
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#apagar").click(function(e) {
	  $("#opcion").val("Apagar")
          $("#secreto").submit()
	})
	$("#reiniciar").click(function(e) {
	 $("#opcion").val("Reiniciar")
          $("#secreto").submit()
	})
})
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

</head>

<body>
	<div id="header-wrapper">
		<div id="header">
			<!-- Logo -->
			<h1>
				<img id="imagenMenu" src="images/osdepym-logo.png">
			</h1>
			<div id="reloj" align="right"
				style="font-size: 20px; position: absolute; right: 150px; top: 50px;"></div>
			<!-- Intro -->
			<h2>LE DA LA BIENVENIDA</h2>
			<p>Seleccione el sector</p>

			<div id="buttons">
				<s:iterator value="sectoresDisponibles" status="sector">

					<s:form action="inicio.action">
						<s:hidden name="nro_sector" value="%{numeroSector}" />

						<s:hidden name="letra_sector" value="%{letra}" />
						<s:hidden name="nom_sector" value="%{nombre}" />

						<a class="btn" href=""><s:property value="nombre" /></a>

					</s:form>



				</s:iterator>
			</div>
		</div>
		<div id="dialog" title="Apagado/Reinicio">
  <p>Ingrese el codigo para Reiniciar/Apagar Turnero</p>
  <form action="shutdown.action" id="secreto" method="POST">
  
 <br><br><br>
    
<fieldset>
    


    
      <label for="password">Codigo</label>
      <input type="password" value="" name="password" id="password" placeholder="Contraseña" class="text ui-widget-content ui-corner-all" >
 
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="hidden" id="opcion" name="opcion">

    </fieldset>
  </form>
</div>
	</div>
	<script>
		var opcionSeleccionada = false;
		var dialog;
		$("a")
				.click(
						function(e) {
							if (opcionSeleccionada == false) {
								opcionSeleccionada = true
								console.log("Data", $(this).closest('form'))
								$
										.ajax({
											type : 'POST',
											data : $(this).closest('form')
													.serialize(),
											url : "sacarTicket.action",
											success : function(data) {
												//$("body").replaceWith(data); 
												//$("#style1").attr("disabled", "disabled");

												$("body").empty()
												$("body").append(data)
												if ($("body").find(
														'.ticketSalida').length == 0) {
													$("#style1").attr(
															"disabled",
															"disabled");
												}
												window
														.setTimeout(
																function() {
																	window.location.href = "inicio.action";
																}, 8000);
											}
										})
							} else {
								e.preventDefault();

							}
							return false

						});
		$("#imagenMenu").dblclick(function(e) {
						$("#password").val("");
						dialog.dialog( "open" );
				})
		dialog= $( "#dialog" ).dialog({ autoOpen: false});
		$.keypad.addKeyDef('START', 'start', 
			    function(inst) { $("#opcion").val("Apagar")
	         				 $("#secreto").submit(); }); 
			$.keypad.addKeyDef('END', 'end', 
			    function(inst) { $("#opcion").val("Reiniciar")
		          $("#secreto").submit(); }); 
		$("#password").keypad({prompt: '', keypadOnly: false, 
		    startText: 'Apagar', startStatus: 'Apagar', 
		    endText: 'Reiniciar', endStatus: 'Reiniciar', 
		    layout: ['789' + $.keypad.CLOSE, 
		        '456' + $.keypad.CLEAR, 
		        '123' + $.keypad.BACK, 
		        '.0' + $.keypad.SPACE + $.keypad.START + $.keypad.END]});
		
	</script>
</body>
</html>