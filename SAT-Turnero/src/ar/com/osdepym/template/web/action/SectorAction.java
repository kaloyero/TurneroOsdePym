package ar.com.osdepym.template.web.action;

// Turnero

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import ar.com.osdepym.common.utils.LoggerVariables;
import ar.com.osdepym.common.utils.Printer;
import ar.com.osdepym.template.entity.RolSectores;
import ar.com.osdepym.template.entity.Ticket;
import ar.com.osdepym.template.web.common.validation.ActualizarTurno;
import ar.com.osdepym.template.web.dao.SectoresDao;
import ar.com.osdepym.template.web.dao.TurneroDao;

import com.opensymphony.xwork2.ActionSupport;

public class SectorAction extends ActionSupport {

	private static Logger LOGGER = Logger.getLogger(LoggerVariables.TURNERO
			+ "-" + SectorAction.class);
	private int nro_sector;
	private int turnosDelante;
	private String nro_ticket;
	private String errorMessage;
	private String fechaTicket;
	private String mensajeError;
	private ArrayList<RolSectores> sectoresDisponibles;
	HttpServletRequest request = ServletActionContext.getRequest();
	private TurneroDao turneroDao = new TurneroDao();

	public String getLetra_sector() {
		return letra_sector;
	}

	public void setLetra_sector(String letra_sector) {
		this.letra_sector = letra_sector;
	}

	public int getNro_sector() {
		return nro_sector;
	}

	private String letra_sector = "";
	private String nom_sector = "";

	public String getNom_sector() {
		return nom_sector;
	}

	public void setNom_sector(String nom_sector) {
		this.nom_sector = nom_sector;
	}

	// static Logger logger = Logger.getLogger(LoginAction.class.getName());
	// public String authentication() {
	public int getSector() {
		return nro_sector;
	}

	public void setNro_sector(int nro_sector) {
		this.nro_sector = nro_sector;
	}

	/**
	 * Determina el proximo numero a imprimir para el sector correspondiente
	 * 
	 */
	public String sacarTicket() {
		Ticket ticket;

		RolSectores rolSector = new RolSectores();
		rolSector.setCod_sectores(new ArrayList<String>());
		rolSector.setNro_sectores(new ArrayList<String>());
		rolSector.setNom_sectores(new ArrayList<String>());

		rolSector.getCod_sectores().add(letra_sector);
		rolSector.getNro_sectores().add("" + nro_sector);
		rolSector.getNom_sectores().add("" + nom_sector);

		// Actualizador de turnos
		ActualizarTurno at = new ActualizarTurno();

		// ejecuta el proceso de proximo numero de sector
		ticket = at.execute(nro_sector, rolSector, letra_sector);

		System.out.println("Sector a actualizar " + nro_sector + " error "
				+ ticket);
		if (ticket.getError().isEmpty()) {
			nro_ticket = ticket.getNumeroMostrar();
			fechaTicket = ticket.getFechaEmision();
			turnosDelante = ticket.getTurnosDelante();

			try {
				LOGGER.debug("Imprimiendo ticket");

				Printer.printTicket(fechaTicket, "", nom_sector, nro_ticket,String.valueOf(turnosDelante), "waitTime");
			} catch (IOException e) {
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

				mensajeError = "Error con Archivo de Impresión";
				e.printStackTrace();
				return "error";
			} catch (PrinterException e) {
				LOGGER.error(LoggerVariables.ERROR+"-"+e.getMessage());

				mensajeError = "Verifique el estado del papel de la impresora"
						+ e.getMessage();
				e.printStackTrace();
				return "error";

			}
			return "success";
		} else {
			mensajeError = ticket.getError();
			LOGGER.error(LoggerVariables.ERROR+"-"+mensajeError);


			return "error";
		}

	}
	
	/**
	 * Metodo que inicia el turnero. Busca los sectores para mostrar en pantalla.
	 */
	public String execute() {
		try{
		SectoresDao sectorDao = new SectoresDao();
		sectoresDisponibles = sectorDao.execute();
		if (!sectoresDisponibles.get(0).getMensaje_error().equals("") && sectoresDisponibles.get(0).getMensaje_error() !=null){
			this.setErrorMessage(sectoresDisponibles.get(0).getMensaje_error());
			return ERROR;
		}
		String ip = turneroDao.obtenerIp();
		String direccionIP = request.getRemoteAddr();
		LOGGER.debug("Verificando IP");

		System.out.println(direccionIP);
		if (ip.equals(direccionIP)|| direccionIP.equalsIgnoreCase("127.0.0.1")||direccionIP.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			return SUCCESS;
		} else {
			this.setErrorMessage("La aplicacion Turnero no puede ejecutarse en esta máquina");
			LOGGER.info(this.getErrorMessage());

			return ERROR;
		}
		}catch (Exception e){
			LOGGER.error(e.getMessage());
			this.setErrorMessage("Ha ocurrido un error inesperado.");
			return ERROR;
		}

	}

	public String shutdown() {

		return SUCCESS;
	}

	public ArrayList<RolSectores> getSectoresDisponibles() {
		return sectoresDisponibles;
	}

	public void setSectoresDisponibles(
			ArrayList<RolSectores> sectoresDisponibles) {
		this.sectoresDisponibles = sectoresDisponibles;
	}

	public String getNro_ticket() {
		return nro_ticket;
	}

	public void setNro_ticket(String nro_ticket) {
		this.nro_ticket = nro_ticket;
	}

	public String getFechaTicket() {
		return fechaTicket;
	}

	public void setFechaTicket(String fechaTicket) {
		this.fechaTicket = fechaTicket;
	}

	public int getTurnosDelante() {
		return turnosDelante;
	}

	public void setTurnosDelante(int turnosDelante) {
		this.turnosDelante = turnosDelante;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}