<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Mostrador OSDEPYM</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<script src="js/jquery.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/audio.min.js"></script>


<link href="css/style.css" rel="stylesheet" id="style1">
<script>
	function playSound(soundfile) {
		console.log("ENTRA")
		$('#dummy')[0].play();
	}

	var ultimoNumeroLlamado = 0;
	$(document)
			.ready(
					function() {
						var ip;
						$.getJSON("jsp/Enviroment.json", function(data) {
							ip = data["ip"];
							intervaloTurnos();
						});

						function intervaloTurnos() {
							setInterval(function() {
								obtenerUltimosTurnos()
							}, 2000);
						}
						function obtenerUltimosTurnos() {
							console.log("DIre", "http:/" + ip
									+ ":8080/Mostrador/ultimosLlamados.action")
							$
									.ajax({
										type : 'POST',
										url : "ultimosLlamados.action",
										success : function(data) {

											actualizarHora()
											$($('.primero').find("td")[0])
													.text(
															data.listaTurnos[0].numeroTurno)
											if (data.listaTurnos[0].numeroTurno != ultimoNumeroLlamado) {
												ultimoNumeroLlamado = data.listaTurnos[0].numeroTurno;
												playSound()
												setTimeout(function() {
													playSound();
												}, 7000);
												for (i = 0; i < 5; i++) {
													$('.primero :eq(0)')
															.animate(
																	{
																		backgroundColor : "#A9A9F5"
																	}, 800)
															.animate(
																	{
																		backgroundColor : "#4dbfb7"
																	}, 800)
													$('.primero :eq(1)')
															.animate(
																	{
																		backgroundColor : "#A9A9F5"
																	}, 800)
															.animate(
																	{
																		backgroundColor : "#4dbfb7"
																	}, 800)
												}

											}

											$($('.primero').find("td")[1])
													.text(
															data.listaTurnos[0].sectorAtencion)
											$($('.segundo').find("td")[0])
													.text(
															data.listaTurnos[1].numeroTurno)
											$($('.segundo').find("td")[1])
													.text(
															data.listaTurnos[1].sectorAtencion)
											$($('.tercero').find("td")[0])
													.text(
															data.listaTurnos[2].numeroTurno)
											$($('.tercero').find("td")[1])
													.text(
															data.listaTurnos[2].sectorAtencion)
											$($('.cuarto').find("td")[0])
													.text(
															data.listaTurnos[3].numeroTurno)
											$($('.cuarto').find("td")[1])
													.text(
															data.listaTurnos[3].sectorAtencion)
											$($('.quinto').find("td")[0])
													.text(
															data.listaTurnos[4].numeroTurno)
											$($('.quinto').find("td")[1])
													.text(
															data.listaTurnos[4].sectorAtencion)

										}

									})
						}

						function actualizarHora() {
							var date = new Date();
							var numero = date.getDate();
							var dia = date.getDate();
							var mes = 1 + date.getMonth();
							var anio = date.getFullYear();
							var fechaFinal = dia + "/" + mes + "/" + anio;
							var h = date.getHours();
							var min = date.getMinutes();
							h = checkTime(h);
							min = checkTime(min);
							var horaFinal = h + ":" + min + "HS";
							$('#fecha').text(fechaFinal)
							$('#hora').text(horaFinal)
						}
						function checkTime(i) {
							if (i < 10) {
								i = "0" + i;
							}
							return i;
						}
					});
</script>


</head>


<body>
	<div id="header-smart">
		<div id="masthead">
			<!-- Logo -->
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora">
				<p id="fecha"></p>
				<p id="hora"></p>
			</div>
		</div>

		<table class="table-fill">
			<thead>
				<tr>
					<th class="text-center">Turno</th>
					<th class="text-center">Puesto</th>
				</tr>
			</thead>
			<tbody class="table-hover">
				<tr id="highlight" class="primero">
					<td class="text-center"></td>
					<td class="text-center"></td>
				</tr>
				<tr class="segundo">
					<td class="text-center segundo"></td>
					<td class="text-center"></td>
				</tr>
				<tr class="tercero">
					<td class="text-center tercero"></td>
					<td class="text-center"></td>
				</tr>
				<tr class="cuarto">
					<td class="text-center"></td>
					<td class="text-center"></td>
				</tr>
				<tr class="quinto">
					<td class="text-center"></td>
					<td class="text-center"></td>
				</tr>
			</tbody>
		</table>
		<audio id="dummy" hidden="true" src="js/sonido.mp3"></audio>

	</div>
</body>
</html>