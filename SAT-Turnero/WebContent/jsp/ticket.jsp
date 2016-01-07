<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


		<div id="header-wrapper" class="ticketSalida">
				<div id="header">
					<!-- Logo -->
					<h1><img src="images/osdepym-logo.png"></h1>
					
					<!-- Intro -->
					<p class="ticket-message">No olvide retirar su ticket de la terminal.</p>
					
					<div id="ticket">
						<img src="images/osdepym-logo-ticket-BN.png">
						<div class="dates"><s:property value="fechaTicket"/></div>
						<div class="numero">Sector</div>
						<div class="sector-var"><s:property value="nom_sector"/></div>
						<div class="numero">N&uacute;mero</div>
						<div class="numero-var"><s:property value="nro_ticket"/></div>
						<div class="dates">Tiene <strong><s:property value="turnosDelante"/></strong> turnos por delante.</div>
<%-- 						<div class="dates">Espera estimada: <strong>XX</strong> minutos</div> --%>
						<div class="dates">Muchas gracias por aguardar.</div>
					</div>
				</div>
		</div>
