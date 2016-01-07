
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
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
		
		
<link rel="stylesheet" href="css/style.css" />
<link rel="shortcut icon" type="image/ico"
	href="http://www.datatables.net/favicon.ico">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=2.0">

<title>Administracion Osdepym</title>
<link rel="stylesheet" type="text/css"
	href="/CESAT-Administracion/datatable/media/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="/CESAT-Administracion/datatable/extensions/TableTools/css/dataTables.tableTools.css">
<link rel="stylesheet" type="text/css"
	href="/CESAT-Administracion/datatable/extensions/Editor-1.3.3/css/dataTables.editor.css">
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css">
<link rel="stylesheet" type="text/css"
	href="/CESAT-Administracion/datatable/examples/resources/syntax/shCore.css">

<link rel="stylesheet"
	href="css/jquery-ui-smooth.css">
<link rel="stylesheet" type="text/css"
	href="/CESAT-Administracion/datatable/examples/resources/demo.css">
<style type="text/css" class="init">
table.dataTable tr td:first-child {
	text-align: center;
}

th {
	text-align: left
}

table.dataTable tr td:first-child:before {
	content: "\f096"; /* fa-square-o */
	font-family: FontAwesome;
}

table.dataTable tr.selected td:first-child:before {
	content: "\f046"; /* fa-check-square-o */
}
</style>
<script type="text/javascript" language="javascript"
	src="/CESAT-Administracion/datatable/media/js/jquery.js"></script>
<script type="text/javascript"
	src="/CESAT-Administracion/datatable/examples/resources/jquery-ui.js"></script>
<script type="text/javascript" language="javascript"
	src="/CESAT-Administracion/datatable/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript"
	src="/CESAT-Administracion/datatable/extensions/TableTools/js/dataTables.tableTools.js"></script>
<script type="text/javascript" language="javascript"
	src="/CESAT-Administracion/datatable/extensions/Editor-1.3.3/js/dataTables.editor.js"></script>
<script type="text/javascript" language="javascript"
	src="/CESAT-Administracion/datatable/examples/resources/syntax/shCore.js"></script>
<script type="text/javascript" language="javascript"

	src="/CESAT-Administracion/datatable/examples/resources/demo.js"></script>
	<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript" language="javascript" class="init">
	var editorSectores; // use a global for the submit and return data rendering in the examples
	var editorPuestos;
	var editorUsuarios;
	var editorSucursal;
	var editorUsuariosSectores;
	var oTablePuestos;
	var oTableSectores;
	var oTableUsuarios;
	var oTableSucursales;
	var idleTime = 0;
	$(document)
			.ready(
					function() {
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
						
						
						$('li').click(function() {
							console.log("Val",$(this).attr("id"))
							if ($(this).attr("id")=="usua"){
								$("#filtroHabilitado").css("display","block")
								$( "#todoHabilitado" ).prop( "checked", true );
								$( "#siHabilitado" ).prop( "checked", false )
								oTableUsuarios.search('').columns().search( '' ).draw();
							}
							if ($(this).attr("id")=="sucu"){
								$("#filtroHabilitado").css("display","block")
								$( "#todoHabilitado" ).prop( "checked", true );
								$( "#siHabilitado" ).prop( "checked", false )
								oTableSucursales.search('').columns().search( '' ).draw();

								
							}
							if ($(this).attr("id")=="confi"){
								$("#filtroHabilitado").css("display","none")

								
							}
						} )
						$('.filter').change(function() {

							var value = $( 'input[name=opt]:checked' ).val();
						    //oTableUsuarios.fnFilter("NO", 2, true, false, false);
						    console.log("ESJ",$(".ui-tabs-active").attr("id"));
						    console.log("ESJ",value);
							if ($(".ui-tabs-active").attr("id")=="usua"){
								console.log("USU")
								if (value=="todos"){
									console.log("TODOS")
									oTableUsuarios.search('').columns().search( '' ).draw();
								}else{
									console.log("SIISISs")
									oTableUsuarios.columns(3).search("SI") .draw();
									//oTableSucursales.search( '' ).columns().search( '' ).draw();
								}
								
							}
							if ($(".ui-tabs-active").attr("id")=="sucu"){
								console.log("SUCs")

								if (value=="todos"){
									oTableSucursales.search('').columns().search( '' ).draw();
								}else{
									oTableSucursales.columns(4).search("SI") .draw();
									//oTableSucursales.search( '' ).columns().search( '' ).draw();
								}

							}
							
					}); 
						function checkTime(i) {
							if (i < 10) {
								i = "0" + i;
							}
							return i;
						}
						
						window.onload = function() {
							startTime();
						}
						var self = this;
						var ip;
						//$.getJSON("jsp/Enviroment.json", function(data) {

							//ip = data["ip"];
							//console.log("IP", ip)
							createEditors()
						//});

						function createEditors() {
							$("#tabs")
									.tabs(
											{
												"activate" : function(event, ui) {
													var table = $.fn.dataTable
															.fnTables(true);
													if (table.length > 0) {
														$(table)
																.dataTable()
																.fnAdjustColumnSizing();
													}
												}
											});

							
						

							editorUsuarios = new $.fn.dataTable.Editor(
									{
										ajax : "usuariosOperaciones.action",
										table : "#example1",

										fields : [ {
											label : "Nombre Usuario",
											name : "nombreUsuario"
										}, {
											label : "Password:",
											name : "password"
										}, {
											label : "Perfil:",
											name : "perfilId",
											type : "select",
											ipOpts : [ {
												label : "Administrador",
												value : "1"
											}, {
												label : "Estadistica",
												value : "2"
											} ]
										},
										// {
										//label: "Puesto:",
										//name: "puestoId",
										//type:  "select"
										//},
										{
											label : "Habilitado:",
											name : "habilitado",
											type : "select",
											ipOpts : [ {
												label : "SI",
												value : "SI"
											}, {
												label : "NO",
												value : "NO"
											} ]
										}

										],
										i18n : {
											create : {
												button : "Nuevo",
												title : "Nuevo",
												submit : "Guardar"
											},
											edit : {
												button : "Editar",
												title : "Edicion",
												submit : "Guardar"
											},
											error : {
												system : "No se pudo completar la operacion"
											}
										}
									});
							editorUsuarios.on("initCreate", function() {

								//this.field("puestoId").update(getPuestos())
							})
							editorUsuarios.on("preSubmit", function(e, o) {

								return validarUsuario(e, o)
							})

							editorUsuarios.on("initEdit", function(settings,
									json, a) {


							})

							
							editorSucursal = new $.fn.dataTable.Editor(
									{
										ajax : "sucursalOperaciones.action",
										table : "#example5",
										fields : [ {
											label : "Codigo Sucursal",
											name : "codSucursal"
										}, {
											label : "Nombre Sucursal:",
											name : "nombreSucursal"
										}, {
											label : "Ip:",
											name : "ip"
											},
											{
												label : "Habilitado:",
												name : "habilitado",
												type : "select",
												ipOpts : [ {
													label : "SI",
													value : "SI"
												}, {
													label : "NO",
													value : "NO"
												} ]
											}


										],
										i18n : {
											create : {
												button : "Nuevo",
												title : "Nuevo",
												submit : "Guardar"
											},
											edit : {
												button : "Editar",
												title : "Edicion",
												submit : "Guardar"
											},
											error : {
												system : "No se pudo completar la operacion"
											}
										}
									});
							editorSucursal.on("preSubmit", function(e, o) {

								return validarSucursal(e, o)
							})

						


							oTableSucursales = $('#example5')
							.DataTable(
									{
										dom : "Tfrtip",
										scrollY : 300,
										paging : false,
										language : {
											"info" : "Mostrando _START_ a _END_ de _TOTAL_ registros",
											"search" : "Busqueda",
											"infoEmpty" : "No existe informacion",
											"emptyTable" : "No existe informacion",
											"zeroRecords" : "No existe informacion",
											"infoFiltered" : "(Filtrando de un maximo de _MAX_ registros)"
										},
										ajax : "listaSucursales.action",
										columns : [ {
											data : null,
											defaultContent : '',
											orderable : false
										}, {
											data : "codSucursal"
										}, {
											data : "nombreSucursal"
										}, {
											data : "ip"
										}, {
											data : "habilitado"
										}, {
											data : "fechaActualizacion"
										}, {
											data : "fechaConexion"
										} ],
										order : [ 1, 'asc' ],
										tableTools : {
											sRowSelect : "os",
											sRowSelector : 'td:first-child',
											aButtons : [{
											        	sExtends : "editor_create",
														editor : editorSucursal,
														sButtonText : "Crear"
													},
													
													{
														sExtends : "editor_edit",
														editor : editorSucursal,
														sButtonText : "Editar"
													}
											//{ sExtends: "editor_remove", editor: editorPuestos, sButtonText: "Borrar" }
											]
										}
									});

							var lastValue;
							editorSucursal.on( 'open', function () {
					            // Store the values of the fields on open
					          // alert("S")
					            //$("#DTE_Field_codSucursal").attr('maxlength',4);
					          console.log("@VA:",$("#DTE_Field_codSucursal"))
					          $( "#DTE_Field_codSucursal" ).keyup(function() {
					        	  console.log("entra",$(this).val())
					        	  if ( $(this).val().length >4){
					        		 if ( $(this).val().charAt( 0 ) === '0' ){
					        			 console.log("SISIS")
						        		  var newV=$(this).val().slice(1);
						        		  console.log("NEWVA",newV)
						        		  $(this).val(newV)
						        		   lastValue= newV
					        		 }else{
					        			 console.log("ACA")
					        			 $(this).val(lastValue)
					        		 }
					        		  
					        	  }else{
					        		  console.log("VALOREs",pad($(this).val(),4))
					        	  $(this).val(pad($(this).val(),4) )
					        	  lastValue= $(this).val()
					        	 
					        	  }
});
					          //debugger
					        } )
					        
					        function pad(value,max){
					           // var value=$(field).val()
					            //console.log("VALUE",)
					           return value.length < max ? pad("0" + value, max) : value;
					        }					        
					        
					        
					        
					        
					        
					        
					        
					        
					        
					        
					        
					        
					        
							editorConfiguracion = new $.fn.dataTable.Editor(
									{
										ajax : "configuracionesOperaciones.action",
										table : "#example6",
										fields : [ {
											label : "Actualizarse en Minutos (Maximo 1440 minutos)",
											name : "tiempo"
										}, 


										],
										i18n : {
											edit : {
												button : "Editar",
												title : "Edicion",
												submit : "Guardar"
											},
											error : {
												system : "No se pudo completar la operacion"
											},

										}
									});
						
						
							editorConfiguracion.on("preSubmit", function(e, o) {

								return validarConfiguracion(e, o)
							})

							oTableConfiguracion = $('#example6')
							.DataTable(
									{
										dom : "Tfrtip",
										scrollY : 300,
										paging : false,
										language : {
											"info" : "Mostrando _START_ a _END_ de _TOTAL_ registros",
											"search" : "Busqueda",
											"infoEmpty" : "No existe informacion",
											"emptyTable" : "No existe informacion",
											"zeroRecords" : "No existe informacion",
											"infoFiltered" : "(Filtrando de un maximo de _MAX_ registros)"
										},
										ajax : "listaConfiguraciones.action",
										columns : [ {
											data : null,
											defaultContent : '',
											orderable : false
										}, {
											data : "tiempo"
										}],
										order : [ 1, 'asc' ],
										tableTools : {
											sRowSelect : "os",
											sRowSelector : 'td:first-child',
											aButtons : [
													{
														sExtends : "editor_edit",
														editor : editorConfiguracion,
														sButtonText : "Editar"
													}
											//{ sExtends: "editor_remove", editor: editorPuestos, sButtonText: "Borrar" }
											]
										}
									});

							oTableUsuarios = $('#example1')
							.DataTable(
									{
										dom : "Tfrtip",
										scrollY : 300,
										paging : false,
										language : {
											"info" : "Mostrando _START_ a _END_ de _TOTAL_ registros",
											"search" : "Busqueda",
											"infoEmpty" : "No existe informacion",
											"emptyTable" : "No existe informacion",
											"zeroRecords" : "No existe informacion",
											"infoFiltered" : "(Filtrando de un maximo de _MAX_ registros)"
										},
										ajax : "listaUsuarios.action",
										columns : [ {
											data : null,
											defaultContent : '',
											orderable : false
										}, {
											data : "nombreUsuario"
										},
										//{ data: "password" },
										{
											data : "nombrePerfil"
										},
										//{ data: "nombrePuesto" },
										{
											data : "habilitado"
										} ],
										order : [ 1, 'asc' ],
										tableTools : {
											sRowSelect : "os",
											sRowSelector : 'td:first-child',
											aButtons : [
													{
														sExtends : "editor_create",
														editor : editorUsuarios,
														sButtonText : "Crear"
													},
													{
														sExtends : "editor_edit",
														editor : editorUsuarios,
														sButtonText : "Editar"
													}
											//{ sExtends: "editor_remove", editor: editorUsuarios, sButtonText: "Borrar" }
											]
										},
										initComplete : function(
												settings, json) {
											// Populate the site select list with the data available in the
											// database on load

											// editorUsuarios.field( 'puestoId' ).update(getPuestos() );
										}

									});
						
							function getUsuarios() {
								var usuarios = new Array()
								var usuario = new Object()
								usuario.value =""
								usuario.label = ""
								usuarios.push(usuario);
								for (var i = 0; i < oTableUsuarios.data().length; i++) {
									var usuario = new Object()
									console
											.log(
													"HAbi ",
													oTableUsuarios.data()[i]["habilitado"])
									if (oTableUsuarios.data()[i]["habilitado"] == "SI") {
										usuario.value = oTableUsuarios.data()[i]["DT_RowId"]
										usuario.label = oTableUsuarios.data()[i]["nombreUsuario"]
										usuarios.push(usuario);
									}

								}
								console.log("USUAR", usuarios)
								return usuarios
							}
						
							function validarUsuario(e, o) {

								var typeReturn = true

								if (o.data.nombreUsuario === "") {
									editorUsuarios.error("nombreUsuario",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (o.data.nombreUsuario.length >15) {
										editorUsuarios
												.error("nombreUsuario",
														"El total de caracteres permitido es menor que 15")
										typeReturn = false;
									}
									if (o.data.nombreUsuario.length >15) {
										editorUsuarios
												.error("nombreUsuario",
														"El total de caracteres permitido es menor que 15")
										typeReturn = false;
									}
									if (/\s/.test(o.data.nombreUsuario)) {
										editorUsuarios
										.error("nombreUsuario",
												"No se permiten espacios")
												typeReturn = false;
									}
								}
								if (o.data.password === "") {
									editorUsuarios.error("password",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (/\s/.test(o.data.password)) {
										editorUsuarios
										.error("password",
												"No se permiten espacios")
												typeReturn = false;
									}
									if (o.data.password.length > 20) {
										editorUsuarios
												.error("password",
														"El total de caracteres permitido es menor que 20")
										typeReturn = false;
									}
								}
								if (o.data.perfilId === "") {
									editorUsuarios.error("perfilId",
											"Campo Requerido")
									typeReturn = false;
								}
								if (o.data.puestoId === "") {
									editorUsuarios.error("puestoId",
											"Campo Requerido")
									typeReturn = false;
								}

								return typeReturn;
							}
							function verifyIP (IPvalue) {
								errorString = "";
								theName = "IPaddress";

								var ipPattern = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
								var ipArray = IPvalue.match(ipPattern);
								
								console.log("ARRay",ipArray)
								if (IPvalue == "0.0.0.0")
								errorString = errorString + theName + ': '+IPvalue+' is a special IP address and cannot be used here.';
								else if (IPvalue == "255.255.255.255")
								errorString = errorString + theName + ': '+IPvalue+' is a special IP address and cannot be used here.';
								if (ipArray == null)
								errorString = errorString + theName + ': '+IPvalue+' is not a valid IP address.';
								else {
								for (i = 0; i < 5; i++) {
								thisSegment = ipArray[i];
								console.log("Segmente",thisSegment)
								if (thisSegment > 255) {
								errorString = errorString + theName + ': '+IPvalue+' is not a valid IP address.';
								i = 4;
								}
								if ((i == 0) && (thisSegment > 255)) {
								errorString = errorString + theName + ': '+IPvalue+' is a special IP address and cannot be used here.';
								i = 4;
								      }
								   }
								}
								extensionLength = 3;
								if (errorString == "")
								 return true
								else
								return false;
								}
							function validarSucursal(e, o) {
								var typeReturn = true
								var pattern = /\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b/;
								var regex = new RegExp("^[0-9]*$");
								//var alpha = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/
								var alpha =/^([a-zA-Z0-9]+\s)*[a-zA-Z0-9]+$/;
							
								if (o.data.nombreSucursal === "") {
									editorSucursal.error("nombreSucursal",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (o.data.nombreSucursal.trim().length > 30) {
										editorSucursal
												.error("nombreSucursal",
														"El total de caracteres permitido es menor que 30")
										typeReturn = false;
									}
								}
								
								if (!o.data.nombreSucursal.match(alpha)) {
									editorSucursal.error("nombreSucursal",
											"No se permiten espaciossas")
											typeReturn = false;
								}
								if (/\s/.test(o.data.ip)) {
									editorSucursal.error("ip",
											"No se permiten espacios")
											typeReturn = false;
								}
								if (o.data.ip === "") {
									editorSucursal.error("ip",
											"Campo Requerido")
									typeReturn = false;
								} else {o.data.ip.trim
									if (o.data.ip.trim().length > 20) {
										editorSucursal
												.error("ip",
														"El total de caracteres permitido es menor que 20")
										typeReturn = false;
									}else if (!verifyIP(o.data.ip.trim())){
										editorSucursal
										.error("ip",
												"Formato de Ip Incorrecto")
								typeReturn = false;
									}
								}
							
								if (o.data.codSucursal === "") {
									editorSucursal.error("codSucursal",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (!o.data.codSucursal.match(regex)) {
										editorSucursal
												.error("codSucursal",
														"Solo se permiten numeros")
										typeReturn = false;
									}else if (!(o.data.codSucursal.trim() >=0001 && o.data.codSucursal.trim() <= 9999)) {
										editorSucursal
										.error("codSucursal",
												"El codigo debe estar entre 0001 y 9999")
										typeReturn = false;
									}
								}
								return typeReturn;

								
							}
							
							
							function validarConfiguracion(e, o) {
								var typeReturn = true
								var pattern = /\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b/;
								var regex = new RegExp("^[0-9]*$");
								
								if (o.data.tiempo === "") {
									editorConfiguracion.error("tiempo",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (!o.data.tiempo.match(regex)) {
										editorConfiguracion
												.error("tiempo",
														"Solo se permiten numeros")
										typeReturn = false;
									}else if (!(o.data.tiempo.trim() >0)) {
										editorConfiguracion
										.error("tiempo",
												"Los minutos deben ser mayor a 0")
										typeReturn = false;
									}else if (!(o.data.tiempo.trim() >=0 && o.data.tiempo.trim() <=1440)) {
										editorConfiguracion
										.error("tiempo",
												"Ingrese minutos entre 1 y 1440")
										typeReturn = false;
									}
									
								}
								return typeReturn;

								
							}
							
							
							
							
							function validarSector(e, o) {

								var typeReturn = true
								var regex = new RegExp("[a-zA-Z]");
								//if (o.data.sector===""){
								//editorSectores.error("sector","Campo Requerido")
								//typeReturn=false;
								//}else{
								//if (o.data.sector.length >20){
								//	editorSectores.error("sector","El total de caracteres permitido es menor que 20")
								//typeReturn=false;
								//}
								//}

								if (o.data.nombreSector === "") {
									editorSectores.error("nombreSector",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (o.data.nombreSector.trim().length > 15) {
										editorSectores
												.error("nombreSector",
														"El total de caracteres permitido es menor que 15")
										typeReturn = false;
									}
								}

								if (o.data.codigoSector === "") {
									editorSectores.error("codigoSector",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (o.data.codigoSector.length > 1) {
										editorSectores
												.error("codigoSector",
														"El total de caracteres permitido es menor que 1")
										typeReturn = false;
									} else if (!o.data.codigoSector
											.match(regex)) {
										editorSectores
												.error("codigoSector",
														"Solo se permiten letras de la A la Z")
										typeReturn = false;
									}

								}
								if (o.data.habilitado === "") {
									editorSectores.error("habilitado",
											"Campo Requerido")
									typeReturn = false;
								}

								return typeReturn;
							}
						}
						var idleInterval = setInterval(timerIncrement,30000); // 1 minute

					    //Zero the idle timer on mouse movement.
					    $(this).mousemove(function (e) {
					        idleTime = 0;
					    });
					    $(this).keypress(function (e) {
					        idleTime = 0;
					    });
					    
					    function timerIncrement() {
					    	
					        idleTime = idleTime + 1;
					        console.log("PASA",idleTime)
					        if (idleTime > 1) { // 1 minutes
					        	$('#myForm').submit();
					        }
					        	}
					        
					});
	

	
</script>
<script>
	$(function() {
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
	});
</script>
</head>

<body class="dt-example" onload="window.history.forward(1);">
<s:hidden name="logueado" />
<s:hidden id="username" name="username" value="%{#session['user']}" />
	<s:if test="logueado == 1" >
	<div id="dialog-message" title="Alerta Usuarios">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> Hay otro usuario conectado en este momento.
				
		</p>
	</div>
	</s:if>
<div id="header-smart">
		<div id="masthead">
			<!-- Logo -->
			<img src="images/osdepym-logo.png">
			<div id="fecha-hora" style="font-weight: 700;font-family: 'Source Sans Pro'" >
				<p style="font-size: 22px;">Central Estadistica Sistema de Administración de Turnos</p>
				<div id="reloj" align="right" style="font-size: 20px;"></div>
			</div>

		

		
		</div>
	</div>
	<div class="container">
	
		<section>
			<h1>
			
				&nbsp;
			</h1>


		</section>
	</div>
	<div><s:form id="myForm" action="desconectar.action"></s:form></div>
	<div id="tabs">
		<ul>
		<p id="filtroHabilitado">
    <label>
        <input type="radio" id ="todoHabilitado" class="filter" value="todos" name="opt" checked />Todos</label>
    <label>
        <input type="radio" id="siHabilitado" class="filter" value="Si" name="opt" />Habilitados</label>
</p>
			<li id="usua"><a  href="#tabs-2" style="color: green;">Usuarios</a></li>
			<li id="sucu"><a  href="#tabs-5" style="color: green;">Sucursal</a></li>
			<li id="confi"><a  href="#tabs-6" style="color: green;">Configuracion</a></li>
			
		</ul>

		<div id="tabs-2">
			<table id="example1" class="display" cellspacing="0">
				<thead>
					<tr>
						<th>
						<th>Nombre Usuario</th>
						<th>Perfil</th>
						<th>Habilitado</th>
					</tr>
				</thead>
			</table>
		</div>

			<div id="tabs-5">
			<table id="example5" class="display" cellspacing="0"
				style="margin-left: 0px; width: 1197px;">
				<thead>
					<tr>
						<th>
						<th>Codigo Sucursal</th>
						<th>Nombre Sucursal</th>
						<th>Ip</th>
						<th>Habilitado</th>
						<th>Fecha Ultima Actualizacion</th>
						<th>Fecha Ultima Conexion</th>
						
					</tr>
				</thead>
			</table>
		</div>
			<div id="tabs-6">
			<table id="example6" class="display" cellspacing="0"
				style="margin-left: 0px; width: 1197px;">
				<thead>
					<tr>
						<th>
						<th>Minutos</th>

						
					</tr>
				</thead>
			</table>
		</div>
	</div>


	<section>
	<div style="position: absolute; left: 3%;">
	Usuario: <s:property value="%{#session['user']}" />
	</div>
	<div style="position: absolute; right: 3%;">
								<a class="links-footer" href="desconectar">Cerrar
									Sesi&oacute;n</a>
							</div></section>
</body>
</html>