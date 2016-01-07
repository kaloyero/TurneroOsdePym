<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" type="image/ico"
	href="http://www.datatables.net/favicon.ico">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=2.0">

<title>Administracion OsdePym</title>
<link rel="stylesheet" type="text/css"
	href="/SAT-Administracion/datatable/media/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="/SAT-Administracion/datatable/extensions/TableTools/css/dataTables.tableTools.css">
<link rel="stylesheet" type="text/css"
	href="/SAT-Administracion/datatable/extensions/Editor-1.3.3/css/dataTables.editor.css">
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css">
<link rel="stylesheet" type="text/css"
	href="/SAT-Administracion/datatable/examples/resources/syntax/shCore.css">

<link rel="stylesheet"
	href="css/jquery-ui-smooth.css">
<link rel="stylesheet" type="text/css"
	href="/SAT-Administracion/datatable/examples/resources/demo.css">
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
	src="/SAT-Administracion/datatable/media/js/jquery.js"></script>
<script type="text/javascript"
	src="/SAT-Administracion/datatable/examples/resources/jquery-ui.js"></script>
<script type="text/javascript" language="javascript"
	src="/SAT-Administracion/datatable/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript"
	src="/SAT-Administracion/datatable/extensions/TableTools/js/dataTables.tableTools.js"></script>
<script type="text/javascript" language="javascript"
	src="/SAT-Administracion/datatable/extensions/Editor-1.3.3/js/dataTables.editor.js"></script>
<script type="text/javascript" language="javascript"
	src="/SAT-Administracion/datatable/examples/resources/syntax/shCore.js"></script>
<script type="text/javascript" language="javascript"

	src="/SAT-Administracion/datatable/examples/resources/demo.js"></script>
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

							editorSectores = new $.fn.dataTable.Editor(
									{
										ajax : "sectoresOperaciones.action",
										table : "#example",
										fields : [// {
										//label: "Sector",
										//name: "sector"
										//},
										{
											label : "Codigo Sector:",
											name : "codigoSector"
										}, {
											label : "Nombre Sector:",
											name : "nombreSector"
										}, {
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

										} ],
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

							editorSectores.on("preSubmit", function(e, o) {

								return validarSector(e, o)
							})

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
												label : "Operador",
												value : "2"
											}, {
												label : "Estadistica",
												value : "3"
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

								// this.field("puestoId").update(getPuestos())

							})

							editorPuestos = new $.fn.dataTable.Editor(
									{
										ajax : "puestosOperaciones.action",
										table : "#example2",
										fields : [ {
											label : "Puesto",
											name : "puesto"
										}, {
											label : "Ip:",
											name : "ip"
										}, {
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
							editorPuestos.on("preSubmit", function(e, o) {

								return validarPuesto(e, o)
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
											}, {
												label : "Pass Totem:",
												name : "codigoTotem"
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


							editorUsuariosSectores = new $.fn.dataTable.Editor(
									{
										ajax : "usuarioSectoresOperaciones.action",
										table : "#example3",
										fields : [ {
											label : "Sector",
											name : "sectorId",
											type : "select"
										}, {
											label : "Usuario:",
											name : "usuarioId",
											type : "select"
										} ],
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
											},
											remove : {
												button : "Borrar",
												title : "Borrar",
												submit : "Borrar",
												confirm : {
													_ : "Desea borrar este registro?",
													1 : "Desea borrar este registro?"
												}
											},
										}
									});
							editorUsuariosSectores.on("initCreate", function() {

								this.field("sectorId").update(getSectores())
								this.field("usuarioId").update(getUsuarios())
							})
							editorUsuariosSectores.on("initEdit", function() {

								this.field("sectorId").update(getSectores())
								this.field("usuarioId").update(getUsuarios())
							})
							editorUsuariosSectores.on("preSubmit", function(e, o) {

								return validarUsuarioSector(e, o)
							})

							// Activate the bubble editor on click of a table cell
							//$('#example').on( 'click', 'tbody td:not(:first-child)', function (e) {
							//editor.bubble( this );
							//} );

							oTableSectores = $('#example')
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
												ajax : "listaSectores.action",
												columns : [ {
													data : null,
													defaultContent : '',
													orderable : false
												}, {
													data : "DT_RowId"
												}, {
													data : "codigoSector"
												}, {
													data : "nombreSector"
												}, {
													data : "habilitado"
												} ],
												order : [ 1, 'asc' ],
												tableTools : {
													sRowSelect : "os",
													sRowSelector : 'td:first-child',
													aButtons : [
															{
																sExtends : "editor_create",
																editor : editorSectores,
																sButtonText : "Crear"
															},
															{
																sExtends : "editor_edit",
																editor : editorSectores,
																sButtonText : "Editar"
															}
													//{ sExtends: "editor_remove", editor: editorSectores, sButtonText: "Borrar" }
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
							oTablePuestos = $('#example2')
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
												ajax : "listaPuestos.action",
												columns : [ {
													data : null,
													defaultContent : '',
													orderable : false
												}, {
													data : "puesto"
												}, {
													data : "ip"
												}, {
													data : "habilitado"
												} ],
												order : [ 1, 'asc' ],
												tableTools : {
													sRowSelect : "os",
													sRowSelector : 'td:first-child',
													aButtons : [
															{
																sExtends : "editor_create",
																editor : editorPuestos,
																sButtonText : "Crear"
															},
															{
																sExtends : "editor_edit",
																editor : editorPuestos,
																sButtonText : "Editar"
															}
													//{ sExtends: "editor_remove", editor: editorPuestos, sButtonText: "Borrar" }
													]
												}
											});
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
											data : "codigoTotem"
										}  ],
										order : [ 1, 'asc' ],
										tableTools : {
											sRowSelect : "os",
											sRowSelector : 'td:first-child',
											aButtons : [
													
													{
														sExtends : "editor_edit",
														editor : editorSucursal,
														sButtonText : "Editar"
													}
											//{ sExtends: "editor_remove", editor: editorPuestos, sButtonText: "Borrar" }
											]
										}
									});


							$('#example3')
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
												ajax : "listaUsuarioSectores.action",
												columns : [ {
													data : null,
													defaultContent : '',
													orderable : false
												}, {
													data : "sectorNombre"
												}, {
													data : "codigoSector"
												}, {
													data : "nombreUsuario"
												} ],
												order : [ 1, 'asc' ],
												tableTools : {
													sRowSelect : "os",
													sRowSelector : 'td:first-child',
													aButtons : [
															{
																sExtends : "editor_create",
																editor : editorUsuariosSectores,
																sButtonText : "Crear"
															},
															{
																sExtends : "editor_edit",
																editor : editorUsuariosSectores,
																sButtonText : "Editar"
															},
															{
																sExtends : "editor_remove",
																editor : editorUsuariosSectores,
																sButtonText : "Borrar"
															} ]
												},
												initComplete : function(
														settings, json) {

													editorUsuariosSectores
															.field("sectorId")
															.update(
																	getSectores())
													editorUsuariosSectores
															.field("usuarioId")
															.update(
																	getUsuarios())
												}
											});

							function getPuestos() {
								var puestos = new Array()

								for (var i = 0; i < oTablePuestos.data().length; i++) {
									var puesto = new Object()
									puesto.value = oTablePuestos.data()[i]["DT_RowId"]
									puesto.label = oTablePuestos.data()[i]["puesto"]
									puestos.push(puesto);
								}
								return puestos
							}

							function getSectores() {
								var sectores = new Array()
								var sector = new Object()
								sector.value =""
								sector.label = ""
								sectores.push(sector);
								for (var i = 0; i < oTableSectores.data().length; i++) {
									var sector = new Object()
									if (oTableSectores.data()[i]["habilitado"] == "SI") {
										sector.value = oTableSectores.data()[i]["DT_RowId"]
										sector.label = oTableSectores.data()[i]["nombreSector"]
										sectores.push(sector);
									}
								}
								console.log("Sectores", sectores)
								return sectores
							}
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
							function validarUsuarioSector(e, o) {
								
								var typeReturn = true

								if (o.data.sectorId === "") {
									editorUsuariosSectores.error("sectorId","Campo Requerido")
									typeReturn = false;
								}
								if(o.data.usuarioId === "") {
									editorUsuariosSectores.error("usuarioId","Campo Requerido")
									typeReturn = false;
								}
								return typeReturn;
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
							function validarPuesto(e,o){
								var typeReturn = true
								var pattern = /\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b/;
								var regex = new RegExp("^[0-9]*$");
								if (o.data.ip === "") {
									editorPuestos.error("ip",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (o.data.ip.trim().length > 20) {
										editorPuestos
												.error("ip",
														"El total de caracteres permitido es menor que 20")
										typeReturn = false;
									}else if (!pattern.test(o.data.ip.trim())){
										editorPuestos
										.error("ip",
												"Formato de Ip Incorrecto")
								typeReturn = false;
									}
								}
								return typeReturn;
							}
							function validarSucursal(e, o) {
								var typeReturn = true
								var pattern = /\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b/;
								var regex = new RegExp("^[0-9]*$");
							
								if (o.data.nombreSucursal === "") {
									editorSucursal.error("nombreSucursal",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (o.data.nombreSucursal.trim().length > 30) {
										editorSucursal
												.error("nombreSector",
														"El total de caracteres permitido es menor que 30")
										typeReturn = false;
									}
								}
								if (o.data.ip === "") {
									editorSucursal.error("ip",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (o.data.ip.trim().length > 20) {
										editorSucursal
												.error("ip",
														"El total de caracteres permitido es menor que 20")
										typeReturn = false;
									}else if (!pattern.test(o.data.ip.trim())){
										editorSucursal
										.error("ip",
												"Formato de Ip Incorrecto")
								typeReturn = false;
									}
								}
								if (o.data.codigoTotem === "") {
									editorSucursal.error("codigoTotem",
											"Campo Requerido")
									typeReturn = false;
								} else {
									if (!o.data.codigoTotem.match(regex)) {
										editorSucursal
												.error("codigoTotem",
														"Solo se permiten numeros")
										typeReturn = false;
									}else if (o.data.codigoTotem.trim().length > 4) {
										editorSucursal
										.error("codigoTotem",
												"El Total de numeros permitidos es menor que 4")
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
									}else if (o.data.codSucursal.trim().length > 5) {
										editorSucursal
										.error("codSucursal",
												"El Total de numeros permitidos es menor que 4")
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
						var idleInterval = setInterval(timerIncrement, 60000); // 1 minute

					    //Zero the idle timer on mouse movement.
					    $(this).mousemove(function (e) {
					        idleTime = 0;
					    });
					    $(this).keypress(function (e) {
					        idleTime = 0;
					    });
					    
					    function timerIncrement() {
					        idleTime = idleTime + 1;
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

<body class="dt-example">
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
				<p style="font-size: 22px;">Sistema de Administración de Turnos</p>
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
			<li><a href="#tabs-1" style="color: green;">Sector</a></li>
			<li><a href="#tabs-2" style="color: green;">Usuarios</a></li>
			<li><a href="#tabs-3" style="color: green;">Puestos</a></li>
			<li><a href="#tabs-4" style="color: green;">Usuario/Sector</a></li>
			<li><a href="#tabs-5" style="color: green;">Sucursal</a></li>
		</ul>
		<div id="tabs-1">
			<table id="example" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>
						<th>Sector</th>
						<th>Codigo Sector</th>
						<th>Nombre Sector</th>
						<th>Habilitado</th>

					</tr>
				</thead>
			</table>
		</div>
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
		<div id="tabs-3">
			<table id="example2" class="display" cellspacing="0"
				style="margin-left: 0px; width: 1197px;">
				<thead>
					<tr>
						<th>
						<th>Puesto</th>
						<th>Ip</th>
						<th>Habilitado</th>

					</tr>
				</thead>
			</table>
		</div>
		<div id="tabs-4">
			<table id="example3" class="display" cellspacing="0"
				style="margin-left: 0px; width: 1197px;">
				<thead>
					<tr>
						<th>
						<th>Sector Nombre</th>
						<th>Sector Codigo</th>
						<th>Usuario</th>

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
						<th>Pass Totem</th>
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